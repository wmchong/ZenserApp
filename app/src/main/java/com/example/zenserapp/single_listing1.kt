package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zenserapp.databinding.ActivityListing1Binding
import com.example.zenserapp.databinding.ActivitySingleListing1Binding
import com.example.zenserapp.ui.chat.ChatPage

class single_listing1 : AppCompatActivity() {
    private lateinit var binding: ActivitySingleListing1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleListing1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.setDisplayShowTitleEnabled(false)
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val title = intent?.getStringExtra("TITLE")
        binding.tvListingTitle.text = title
        val price = "S$ "+intent?.getStringExtra("PRICE")
        binding.tvListingPrice.text = price
        val condition = intent?.getStringExtra("CONDITION")
        binding.tvListingCondition.text = condition
        val description = intent?.getStringExtra("DESCRIPTION")
        binding.tvListingDescription.text = description
        val buyerName = intent?.getStringExtra("BUYERNAME")
        binding.tvSellerName.text = buyerName


        val sellerName = binding.tvSellerName.text
        binding.btnChat.setOnClickListener {
            val intent = Intent(this, ChatPage::class.java)
            intent.putExtra("SELLERNAME", sellerName)
            startActivity(intent)
        }

        //add listing to wishlist
        binding.cbHeart.setOnCheckedChangeListener { checkBox, isChecked ->
            if(isChecked){
                Toast.makeText(this,"Item added to Wishlist",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this,"Item removed from Wishlist",Toast.LENGTH_SHORT).show()
            }
        }

        //make an offer button click to show price dialog
        binding.btnMakeOffer.setOnClickListener {
            //inflate the dialog with custom view
            var dialogView =layoutInflater.inflate(R.layout.make_offer_dialog,null)
            //AlertDialogBuilder
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("iAmBuyer is selling this for $1399")
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
                val intent = Intent(this, ChatPage::class.java)
                intent.putExtra("SELLERNAME", sellerName)
                intent.putExtra("OFFERPRICE", enterPrice)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}