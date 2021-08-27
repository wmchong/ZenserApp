package com.example.zenserapp.ui.sell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.net.toUri
import com.example.zenserapp.MainActivity
import com.example.zenserapp.R
import com.example.zenserapp.User
import com.example.zenserapp.databinding.ActivityChatPageBinding.inflate
import com.example.zenserapp.ui.me.MeFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception
import java.util.*

class ListingDetails : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var chipGroup: ChipGroup
    private  var chip: Chip? =null
    private lateinit var title: EditText
    private lateinit var price: EditText
    private lateinit var description: EditText
    private lateinit var button: Button
    private var user_name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.listing_details)

        val actionBar = supportActionBar
        actionBar!!.title = "Add Listing Details"

        actionBar.setDisplayHomeAsUpEnabled(true)
        title = findViewById(R.id.listingTitleET)
        price = findViewById(R.id.listingPriceET)
        description = findViewById(R.id.listingDescET)
        radioGroup = findViewById(R.id.rg_radioGroup)
        chipGroup = findViewById(R.id.conditionCG)
        button = findViewById(R.id.b_insert)

        button.setOnClickListener {
           getUserData()
           uploadDataToFirebaseStorage()
          //  Log.d("Insert Activity",photo.toString())
            //    chip=findViewById(cId)
            // Toast.makeText(this,chip.text.toString(),Toast.LENGTH_SHORT).show()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }




    private fun uploadDataToFirebaseStorage(){
        val image = intent?.getStringExtra("PhotoUrl")
        Log.d("Upload",image.toString())
        val cat = intent?.getStringExtra("Category")
        val filename= UUID.randomUUID().toString()
        val ref=  FirebaseStorage.getInstance().getReference("/productImages/$cat/$filename")
        if (image != null) {
            ref.putFile(image.toUri())
                .addOnSuccessListener {
                    Log.d("Upload","Image uploaded")
                    ref.downloadUrl.addOnSuccessListener {
                        it.toString()
                        Log.d("upload","File location: $it")
                        handleSelection(it.toString())
                    }
                        .addOnFailureListener{
                            Toast.makeText(
                                this,
                                "Failed Registration: " + it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                }
        }
        else{

            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserData(){
       val db=FirebaseDatabase.getInstance().getReference("users")
       val uid=FirebaseAuth.getInstance().currentUser?.uid.toString()
        db.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user_name=snapshot.child("username").getValue(String::class.java).toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }

        })
    }


    private fun handleSelection(imageUrl:String) {
        val cat = intent?.getStringExtra("Category")
        val productTitle = title.text.toString()
        val productPrice = price.text.toString()
        val productDesc = description.text.toString()
        chipGroup.checkedChipIds.forEach {
            chip = findViewById(it)
        }
        val id = radioGroup.checkedRadioButtonId
        if (id <= 0) {
            Toast.makeText(this, "Select Method of delivery", Toast.LENGTH_SHORT).show()
        } else {
            radioButton = findViewById(id)


            // Toast.makeText(this, radioButton.text, Toast.LENGTH_SHORT).show()
            val uid = FirebaseAuth.getInstance().uid
            val ref =
                FirebaseDatabase.getInstance().getReference("/products/$cat/$productTitle")
            val user = Product(
                uid!!,
                user_name,
                imageUrl,
                productTitle,
                productPrice,
                productDesc,
                chip?.text.toString(),
                radioButton.text.toString()
            )
            ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("Insert Activity", "product saved to database")

                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            val refListing =
                FirebaseDatabase.getInstance().getReference("/listing/$uid/$productTitle")
            val userListing = Product(
                uid!!,
                user_name,
                imageUrl,
                productTitle,
                productPrice,
                productDesc,
                chip?.text.toString(),
                radioButton.text.toString()
            )
            refListing.setValue(user)
                .addOnSuccessListener {
                    Log.d("Insert Activity", "product saved to database listing")

                }

        }
    }
}
class Product(val uid:String,val username:String,val imageUrl:String,val title:String ,val price:String,val desc:String,val condition: String,val methodDelivery:String)
{
    constructor():this("","","","","","","","")
}
