package com.example.zenserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zenserapp.databinding.ActivitySearchResultPageBinding
import com.example.zenserapp.ui.categories.MyAdapter
import com.example.zenserapp.ui.categories.Product
import com.google.firebase.database.*

class SearchResultPage : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultPageBinding
    private lateinit var productArrayList:ArrayList<Product>
    var keyTitle: String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Explore Result"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        //get keyword in previous Explore Fragment and show it on the text view
        val keyWord = intent.getStringExtra("KeyWord")
        Log.e("keyword","$keyWord")
        binding.tvTextInSearchBox.text = keyWord
        keyTitle = keyWord

        binding.rvResultLists.layoutManager= GridLayoutManager(this,2)
        binding.rvResultLists.setHasFixedSize(true)
        binding.rvResultLists.setItemViewCacheSize(20);

        productArrayList= arrayListOf<Product>()
        getRelatedData()
    }

    private fun getRelatedData(){
        val query:Query = FirebaseDatabase.getInstance().getReference("/searchListings")

        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(productSnapshot in snapshot.children){
                        val product=productSnapshot.getValue(Product::class.java)
                        val title = product!!.title
                        if(title!!.contains("$keyTitle",true)){
                            productArrayList.add(product!!)
                        }
                    }
                    binding.rvResultLists.adapter =
                        MyAdapter(this@SearchResultPage,productArrayList)
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