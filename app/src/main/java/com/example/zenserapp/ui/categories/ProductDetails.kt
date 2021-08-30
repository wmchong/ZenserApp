package com.example.zenserapp.ui.categories

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zenserapp.R
import com.example.zenserapp.User
import com.example.zenserapp.databinding.ActivityProductDetailsBinding
import com.example.zenserapp.ui.chat.ChatLogActivity
import com.example.zenserapp.ui.chat.ChatPage
import com.example.zenserapp.ui.chat.NewMessageActivity.Companion.USER_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {
    private lateinit var binding:ActivityProductDetailsBinding
    private val uid = FirebaseAuth.getInstance().uid
    private var isInWishlist:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("uid","$uid")
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val title=intent.getStringExtra("Title")
        val price=intent.getStringExtra("Price")
        val sellerName=intent.getStringExtra("Username")
        val description=intent.getStringExtra("Description")
        val condition=intent.getStringExtra("Condition")
        val method=intent.getStringExtra("Method")
        val image=intent.getStringExtra("Image")
        val uid=intent.getStringExtra("UID")



        binding.tvTitle.text=title
        binding.tvPrice.text=price
        binding.tvSellerName.text=sellerName
        binding.tvDescription.text=description
        binding.tvCondition.text=condition
        binding.tvMethodDelivery.text=method
        Picasso.get().load(image).into(binding.ivImage)

        val user = User(uid!!,"",sellerName!!,"","","" )

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title =title
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        //check whether the listing is in the wishlist
        val query: Query = FirebaseDatabase.getInstance().getReference("/wishlist/$uid/")
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){
                        val product=productSnapshot.getValue(Product::class.java)
                        val productTitle =  product!!.title
                        if(productTitle.equals(title)){
                            //the listing is in your wishlist
                            binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_selected_24)
                            isInWishlist = true
                            Log.d("two titles equal","$productTitle , $title")
                        }else{
                            Log.d("two titles not equal","$productTitle , $title")
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message",error.message)
            }
        })

        //chat button
//        binding.btnChat.setOnClickListener {
//            val intent = Intent(this, ChatPage::class.java)
//            intent.putExtra("SELLERNAME", sellerName)
//            startActivity(intent)
//        }

        binding.btnChat.setOnClickListener {
            val intent = Intent(this, ChatLogActivity::class.java)
            intent.putExtra(USER_KEY, user)
            startActivity(intent)
        }

        //add listing to wishlist
        binding.btnFavourite.setOnClickListener {
            if(isInWishlist){
                //user wants to remove it from the wishlist
                val ref =
                    FirebaseDatabase.getInstance().getReference("/wishlist/$uid/$title").removeValue()
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                isInWishlist = false
            }else{
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_selected_24)
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
                val ref =
                    FirebaseDatabase.getInstance()
                        .getReference("/wishlist/$uid/$title")
                val fav = Wishlist(
                    title,
                    image,
                    price,
                    sellerName
                )
                ref.setValue(fav)
                    .addOnSuccessListener {
                        Log.d("Added to wish list", "product saved to database")
                    }
                isInWishlist = true
            }
        }

        //make an offer button click to show price dialog
        binding.btnMakeOffer.setOnClickListener {
            //inflate the dialog with custom view
            var dialogView =layoutInflater.inflate(R.layout.make_offer_dialog,null)
            //AlertDialogBuilder
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("$sellerName is selling this for $ $price")
            //show dialog
            val offerDialogAlert = dialogBuilder.show()

            //when user clicks on cancel button
            dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                offerDialogAlert.dismiss()
            }

            //when user confirms the offer
            dialogView.findViewById<Button>(R.id.btnConfirmOffer).setOnClickListener {
                //dismiss dialog
                offerDialogAlert.dismiss()
                //get input price from EditTexts of custom layout
                val enterPrice = dialogView.findViewById<EditText>(R.id.etOfferPrice).text.toString()
                Log.e("Enter Price", enterPrice)

                val intent = Intent(this, ChatLogActivity::class.java)
                intent.putExtra(USER_KEY, user)
                intent.putExtra("OFFERPRICE", enterPrice)
                intent.putExtra("TITLE", title)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}