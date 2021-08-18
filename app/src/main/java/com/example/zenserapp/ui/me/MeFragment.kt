package com.example.zenserapp.ui.me

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentMeBinding
import android.util.Log
import com.example.zenserapp.LoginPage
import com.google.android.material.tabs.TabLayoutMediator

class MeFragment : Fragment(R.layout.fragment_me) {

    private var _binding: FragmentMeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get username from login activity
        val username= activity?.intent?.getStringExtra("USERNAME")
        //set username
        binding.usernameTV.text=username

        binding.searchIB.setOnClickListener {
            //send text to backend
            //sql here
            val text = binding.searchET.text.toString()
            Log.i("look here",text)
        }
        binding.logoutIB.setOnClickListener{
            val intent = Intent(context,LoginPage::class.java)
            startActivity(intent)
        }

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Listings"
                }
                1->{
                    tab.text="Wishlists"
                }
                2->{
                    tab.text="Purchases"
                }
            }
        }.attach()

    }

    //prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}