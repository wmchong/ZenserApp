package com.example.zenserapp.ui.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zenserapp.databinding.ActivityServicesHomeBinding
import com.google.firebase.database.*

class ServicesHome : AppCompatActivity() {
    private lateinit var binding: ActivityServicesHomeBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var productArrayList: ArrayList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServicesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Services"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        binding.rvProductList.setHasFixedSize(true)
        binding.rvProductList.setItemViewCacheSize(20);

        productArrayList = arrayListOf<Product>()
        getUserData()

    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("/products/Services")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    binding.rvProductList.adapter = MyAdapter(this@ServicesHome,productArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message", error.message)
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

