package com.example.zenserapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.zenserapp.databinding.ActivityListing1Binding
import com.example.zenserapp.ui.ListingDBHelper
import com.example.zenserapp.ui.ListingModel
import com.example.zenserapp.ui.MyDBHelper
import java.util.stream.IntStream.range

class Listing1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityListing1Binding
    private lateinit var listingdbHelper: ListingDBHelper
    private lateinit var userdbHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListing1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //datebase
        listingdbHelper = ListingDBHelper(this)
        userdbHelper = MyDBHelper(this)

        //clear all pre rows
        listingdbHelper.deleteAllRows()
        // insert example listings
        var listing1 = ListingModel(title = "iphone 15X", price = 1390.99, condition = "Brand New",
            description = "Queue for iphone 15x 512GB", dealmethod = "Meet-Up", category = "Computers & Tech", userid = 1)
        var listing2 = ListingModel(title = "Nike Dunk low retro sneakers", price = 90.00, condition = "Like New",
            description = "42 Sizes. worn 2-3 times only. Pm for more details", dealmethod = "Delivery", category = "Men's Fashion", userid = 2)
        var listing3 = ListingModel(title = "Iphone 11 128GB Black", price = 300.00, condition = "Well used",
            description = "second-handed iphone 11 128GB Black", dealmethod = "Delivery", category = "Computers & Tech", userid = 2)
        addListing(listing1)
        addListing(listing2)
        addListing(listing3)

        val keyWord = intent?.getStringExtra("SearchingKeyWord")
        binding.tvTextInSearchBox.text = keyWord
        val listingsList = getListings(keyWord)

        //up to 6 listings displayed
        for(i in range(0,listingsList.size)){
            if(i == 0){
                binding.tvListingTitle1.text = listingsList[i].title
                binding.tvPrice1.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt1.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName1.text = userName
            }
            if(i == 1){
                binding.tvListingTitle2.text = listingsList[i].title
                binding.tvPrice2.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt2.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName2.text = userName
            }
            if(i == 2){
                binding.tvListingTitle3.text = listingsList[i].title
                binding.tvPrice3.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt3.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName3.text = userName
            }
            if(i == 3){
                binding.tvListingTitle4.text = listingsList[i].title
                binding.tvPrice4.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt4.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName4.text = userName
            }
            if(i == 4){
                binding.tvListingTitle5.text = listingsList[i].title
                binding.tvPrice5.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt5.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName5.text = userName
            }
            if(i == 5){
                binding.tvListingTitle6.text = listingsList[i].title
                binding.tvPrice6.text = listingsList[i].price.toString()
                binding.tvDeliveryOpt6.text = listingsList[i].dealmethod
                val userName = userdbHelper.findUserName(listingsList[i].userid)
                binding.tvUseName6.text = userName
            }
        }

        binding.cvListing1.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[0].title)
            intent.putExtra("PRICE",listingsList[0].price.toString())
            intent.putExtra("CONDITION",listingsList[0].condition)
            intent.putExtra("DESCRIPTION",listingsList[0].description)
            intent.putExtra("DEALMETHOD",listingsList[0].dealmethod)
            intent.putExtra("CATEGORY",listingsList[0].category)
            val userName = userdbHelper.findUserName(listingsList[0].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
        binding.cvListing2.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[1].title)
            intent.putExtra("PRICE",listingsList[1].price.toString())
            intent.putExtra("CONDITION",listingsList[1].condition)
            intent.putExtra("DESCRIPTION",listingsList[1].description)
            intent.putExtra("DEALMETHOD",listingsList[1].dealmethod)
            intent.putExtra("CATEGORY",listingsList[1].category)
            val userName = userdbHelper.findUserName(listingsList[1].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
        binding.cvListing3.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[2].title)
            intent.putExtra("PRICE",listingsList[2].price.toString())
            intent.putExtra("CONDITION",listingsList[2].condition)
            intent.putExtra("DESCRIPTION",listingsList[2].description)
            intent.putExtra("DEALMETHOD",listingsList[2].dealmethod)
            intent.putExtra("CATEGORY",listingsList[2].category)
            val userName = userdbHelper.findUserName(listingsList[2].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
        binding.cvListing4.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[3].title)
            intent.putExtra("PRICE",listingsList[3].price.toString())
            intent.putExtra("CONDITION",listingsList[3].condition)
            intent.putExtra("DESCRIPTION",listingsList[3].description)
            intent.putExtra("DEALMETHOD",listingsList[3].dealmethod)
            intent.putExtra("CATEGORY",listingsList[3].category)
            val userName = userdbHelper.findUserName(listingsList[3].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
        binding.cvListing5.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[4].title)
            intent.putExtra("PRICE",listingsList[4].price.toString())
            intent.putExtra("CONDITION",listingsList[4].condition)
            intent.putExtra("DESCRIPTION",listingsList[4].description)
            intent.putExtra("DEALMETHOD",listingsList[4].dealmethod)
            intent.putExtra("CATEGORY",listingsList[4].category)
            val userName = userdbHelper.findUserName(listingsList[4].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
        binding.cvListing6.setOnClickListener {
            val intent = Intent(this, single_listing1::class.java)
            intent.putExtra("TITLE",listingsList[5].title)
            intent.putExtra("PRICE",listingsList[5].price.toString())
            intent.putExtra("CONDITION",listingsList[5].condition)
            intent.putExtra("DESCRIPTION",listingsList[5].description)
            intent.putExtra("DEALMETHOD",listingsList[5].dealmethod)
            intent.putExtra("CATEGORY",listingsList[5].category)
            val userName = userdbHelper.findUserName(listingsList[5].userid)
            intent.putExtra("BUYERNAME",userName)
            startActivity(intent)
        }
    }

    //collect listings that title contains keyword
    private fun getListings(keyWord: String?): ArrayList<ListingModel>{
       val listingList = listingdbHelper.getListings(keyWord.toString())
        Log.e("Size of result listing", "${listingList.size}")
        return listingList
    }

    //for Joel
    //insert listing and check whether success
    private fun addListing(lst:ListingModel){
        val status = listingdbHelper.insert(lst)
        //check insert success or not success
        if(status > -1)
        {
            Log.e("listing Added", "${lst.title}")
        }else{
            Log.e("listing Failed to insert", "${lst.title}")
        }
    }

}