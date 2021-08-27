package com.example.zenserapp.ui.categories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager

import com.example.zenserapp.databinding.ActivityComputerAndTechHomeBinding
import com.google.firebase.database.*

class ComputerAndTechHome() : AppCompatActivity() {
    private lateinit var binding: ActivityComputerAndTechHomeBinding
    private lateinit var dbref:DatabaseReference
    private lateinit var productArrayList:ArrayList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityComputerAndTechHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Computer And Tech"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.rvProductList.layoutManager=GridLayoutManager(this,2)
        binding.rvProductList.setHasFixedSize(true)
        binding.rvProductList.setItemViewCacheSize(20);

       productArrayList= arrayListOf<Product>()
       getUserData()

    }

    private fun getUserData(){
        dbref=FirebaseDatabase.getInstance().getReference("/products/Computers & Tech")
        dbref.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){
                     val product=productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    binding.rvProductList.adapter =MyAdapter(this@ComputerAndTechHome,productArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message",error.message)
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

