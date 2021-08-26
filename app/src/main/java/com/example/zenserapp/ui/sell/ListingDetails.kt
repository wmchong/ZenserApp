package com.example.zenserapp.ui.sell

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.R
import android.widget.*
import com.example.zenserapp.MainActivity
import com.example.zenserapp.ui.ListingDBHelper
import com.example.zenserapp.ui.ListingModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class ListingDetails : AppCompatActivity() {
    private lateinit var buttonOpen: Button
    lateinit var listingTitle: EditText
    lateinit var listingPrice: EditText
    lateinit var listingConditionCG: ChipGroup
    lateinit var listingConditionChip: Chip
    lateinit var listingDesc: EditText
    lateinit var listingDealMethodRG: RadioGroup
    lateinit var listingDealMethod: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listing_details)

        // initializing listingDB
        val listingDB = ListingDBHelper(this)

        // initializing values based on UI
        buttonOpen = findViewById(R.id.submitListingDetailsBtn)
        listingTitle = findViewById(R.id.listingTitleET)
        listingPrice = findViewById(R.id.listingPriceET)
        listingConditionCG = findViewById(R.id.conditionCG)
        listingDesc = findViewById(R.id.listingDescET)
        listingDealMethodRG = findViewById(R.id.listingDealRG)

        // values to store final input
        var title:String
        var price:Double
        var condition:String
        var description:String
        var dealmethod:String
        var category:String
        var userid :Int


        val actionBar = supportActionBar
        actionBar!!.title = "Add Listing Details"

        actionBar.setDisplayHomeAsUpEnabled(true)

        buttonOpen.setOnClickListener {

            listingConditionChip = listingConditionCG.findViewById(listingConditionCG.checkedChipId)

            val selectedOption: Int = listingDealMethodRG!!.checkedRadioButtonId
            listingDealMethod = findViewById(selectedOption)

            title = listingTitle.text.toString()
            price = listingPrice.text.toString().toDouble()
            condition = listingConditionChip.text.toString()
            description = listingDesc.text.toString()
            dealmethod = listingDealMethod.text.toString()
            category = intent.getStringExtra("category").toString()

            var listing = ListingModel(title = title, price = price, condition = condition, description = description, dealmethod = dealmethod, category = category, userid = 1)
            listingDB.insert(listing)
            Toast.makeText(this, "Listing Created!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            this?.startActivity(intent)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}