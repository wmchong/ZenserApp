package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityForgotPasswordBinding
import com.example.zenserapp.ui.MyDBHelper

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Forgot Password?"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val helperDB= MyDBHelper(applicationContext)

       binding.bSearch.setOnClickListener {
           val getUsername=binding.etUsernameReset.text.toString()
           val getEmail=binding.etEmailReset.text.toString()

           val checkDetails=helperDB.checkUsernameEmail(getUsername,getEmail)
           if(checkDetails)
           {
               val intent = Intent(this,ResetPassword::class.java)
               intent.putExtra("username",getUsername)
               intent.putExtra("email",getEmail)
               startActivity(intent)
           }
           else{
               Toast.makeText(applicationContext,"Account Does Not Exist",Toast.LENGTH_SHORT).show()
           }

       }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}