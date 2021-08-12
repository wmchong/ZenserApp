package com.example.zenserapp.ui.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.zenserapp.MainActivity
import com.example.zenserapp.R

class ComputerAndTechHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_and_tech_home)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Computer And Tech"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}