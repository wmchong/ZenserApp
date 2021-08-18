package com.example.zenserapp.ui.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

import com.example.zenserapp.databinding.ActivityComputerAndTechHomeBinding
import com.example.zenserapp.single_listing1

class ComputerAndTechHome : AppCompatActivity() {
    private lateinit var binding: ActivityComputerAndTechHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityComputerAndTechHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Computer And Tech"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        binding.cvListing1.setOnClickListener{
            val intent =Intent(this,single_listing1::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}