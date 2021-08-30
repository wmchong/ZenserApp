package com.example.zenserapp.ui.me

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zenserapp.R
import com.example.zenserapp.databinding.FragmentMeBinding
import android.util.Log
import android.widget.Toast
import com.example.zenserapp.LoginPage
import com.example.zenserapp.SettingPage
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MeFragment : Fragment(R.layout.fragment_me) {

    private var _binding: FragmentMeBinding? = null
    private val binding get() = _binding!!
    private lateinit var db:DatabaseReference
    private lateinit var uid:String


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

        db=FirebaseDatabase.getInstance().getReference("users")
        uid=FirebaseAuth.getInstance().currentUser?.uid.toString()
        if(uid.isNotEmpty()){
            getUserData()
        }
        else{
            Log.d("Chat","Failed")
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

        binding.settingIB.setOnClickListener {
            val intent = Intent(context, SettingPage::class.java)
            context?.startActivity(intent)
        }

        binding.logoutIB.setOnClickListener {
            showAlertDialog()
        }
    }

    fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out of this account?")
        builder.setPositiveButton("Logout",
            DialogInterface.OnClickListener { dialog, id ->
                val intent = Intent(context,LoginPage::class.java)
                startActivity(intent)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //prevent memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


     fun getUserData(){
        db.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user_name=snapshot.child("username").getValue(String::class.java).toString()
                Log.d("meFrag",user_name)
                binding.usernameTV.text=user_name
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }

        })
    }

}