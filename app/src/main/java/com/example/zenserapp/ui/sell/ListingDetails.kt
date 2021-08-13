package com.example.zenserapp.ui.sell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.R

class ListingDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listing_details)

        val actionBar = supportActionBar
        actionBar!!.title = "Add Listing Details"

        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}