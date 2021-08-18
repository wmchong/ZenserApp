package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.btnChat.setOnClickListener {
            val sellerName = binding.tvSellerName.text
            val intent = Intent(this, ChatPage::class.java)
            intent.putExtra("SELLERNAME", sellerName)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}