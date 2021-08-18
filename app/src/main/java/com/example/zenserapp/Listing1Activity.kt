package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.databinding.ActivityListing1Binding

class Listing1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityListing1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListing1Binding.inflate(layoutInflater)

        //setContentView(R.layout.activity_listing1)
        setContentView(binding.root)

        val getKeyWord = intent?.getStringExtra("SearchingKeyWord")
        binding.tvTextInSearchBox.text = getKeyWord

        binding.cvListing1.setOnClickListener {
            startActivity(Intent(this, single_listing1::class.java))
        }
    }
}