package com.example.zenserapp.ui.me.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentFirstBinding
import com.example.zenserapp.databinding.FragmentSecondBinding
import com.example.zenserapp.ui.categories.MyAdapterListing
import com.example.zenserapp.ui.categories.MyAdapterWishlist
import com.example.zenserapp.ui.categories.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference
    private lateinit var productArrayList: ArrayList<Product>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)


        binding.rvListing.layoutManager = GridLayoutManager(context, 2)
        binding.rvListing.setHasFixedSize(true)
        binding.rvListing.setItemViewCacheSize(20);

        productArrayList = arrayListOf<Product>()
        getUserData()
        return binding.root
    }
    private fun getUserData() {
        val uid = FirebaseAuth.getInstance().uid

        dbref = FirebaseDatabase.getInstance().getReference("/listing/$uid")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    binding.rvListing.adapter = context?.let { MyAdapterListing(it,productArrayList) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message", error.message)
            }
        })

    }

}