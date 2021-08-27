package com.example.zenserapp.ui.categories

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.R
import com.example.zenserapp.databinding.ActivityProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {
    private lateinit var binding:ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val title=intent.getStringExtra("Title")
        val price=intent.getStringExtra("Price")
        val sellerName=intent.getStringExtra("Username")
        val description=intent.getStringExtra("Description")
        val condition=intent.getStringExtra("Condition")
        val method=intent.getStringExtra("Method")
        val image=intent.getStringExtra("Image")



        binding.tvTitle.text=title
        binding.tvPrice.text=price
        binding.tvSellerName.text=sellerName
        binding.tvDescription.text=description
        binding.tvCondition.text=condition
        binding.tvMethodDelivery.text=method
        Picasso.get().load(image).into(binding.ivImage)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title =title
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}