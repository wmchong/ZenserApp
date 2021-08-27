package com.example.zenserapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import com.example.zenserapp.databinding.ActivityListing1Binding
import com.example.zenserapp.ui.ListingDBHelper
import com.example.zenserapp.ui.ListingModel

class Listing1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityListing1Binding
    private lateinit var dbHelper: ListingDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListing1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //datebase
        dbHelper = ListingDBHelper(this)


        var listing1 = ListingModel(title = "iphone 15X", price = 1390.99, condition = "Brand New",
            description = "Queue for iphone 15x 512GB", dealmethod = "Meet-Up", category = "Computers & Tech", userid = 0)
        var listing2 = ListingModel(title = "Nike Dunk low retro sneakers", price = 90.00, condition = "Like New",
            description = "42 Sizes. worn 2-3 times only. Pm for more details", dealmethod = "Delivery", category = "Men's Fashion", userid = 1)

        addListing(listing1)
        addListing(listing2)

        val keyWord = intent?.getStringExtra("SearchingKeyWord")
        binding.tvTextInSearchBox.text = keyWord
        val listingsList = getListings(keyWord)

        binding.cvListing1.setOnClickListener {
            startActivity(Intent(this, single_listing1::class.java))
        }
    }

    //collect listings that title contains keyword
    private fun getListings(keyWord: String?): ArrayList<ListingModel>{
        val listingList = dbHelper.getListings(keyWord.toString())
        Log.e("Size of result listing", "${listingList.size}")
        return listingList
    }

    //for Joel
    //insert listing and check whether success
    private fun addListing(lst:ListingModel){
        val status = dbHelper.insert(lst)
        //check insert success or not success
        if(status > -1)
        {
            Log.e("listing Added", "${lst.title}")
        }else{
            Log.e("listing Failed to insert", "${lst.title}")
        }
    }

}