package com.example.zenserapp.ui.me.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentThirdBinding
import com.example.zenserapp.ui.categories.MyAdapterListing
import com.example.zenserapp.ui.categories.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference
    private lateinit var productArrayList: ArrayList<Product>
    val uid = FirebaseAuth.getInstance().uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)

/*
        binding.rvListing.layoutManager = GridLayoutManager(context, 2)
        binding.rvListing.setHasFixedSize(true)
        binding.rvListing.setItemViewCacheSize(20);

        productArrayList = arrayListOf<Product>()
        getUserData()

 */


        return binding.root
    }
    /*
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("/purchase/$uid")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    binding.
                    rvListing.adapter = context?.let { MyAdapterListing(it,productArrayList) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message", error.message)
            }
        })

    }

     */

}