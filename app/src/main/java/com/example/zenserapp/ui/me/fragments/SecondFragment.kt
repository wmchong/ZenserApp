package com.example.zenserapp.ui.me.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zenserapp.databinding.FragmentSecondBinding
import com.example.zenserapp.ui.categories.MyAdapter
import com.example.zenserapp.ui.categories.MyAdapterWishlist
import com.example.zenserapp.ui.categories.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbref: DatabaseReference
    private lateinit var productArrayList: ArrayList<Product>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.rvWishList.layoutManager = GridLayoutManager(context, 2)
        binding.rvWishList.setHasFixedSize(true)
        binding.rvWishList.setItemViewCacheSize(20);

        productArrayList = arrayListOf<Product>()
        getUserData()
        return binding.root
    }
    private fun getUserData() {
        val uid = FirebaseAuth.getInstance().uid
        dbref = FirebaseDatabase.getInstance().getReference("/wishlist/$uid")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                productArrayList.clear()
                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        productArrayList.add(product!!)
                    }
                    binding.rvWishList.adapter = context?.let { MyAdapterWishlist(it,productArrayList) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error message", error.message)
            }
        })

    }

}