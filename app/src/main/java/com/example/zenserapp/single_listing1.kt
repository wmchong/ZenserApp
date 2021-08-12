package com.example.zenserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class single_listing1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_listing1)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.setDisplayShowTitleEnabled(false)
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}