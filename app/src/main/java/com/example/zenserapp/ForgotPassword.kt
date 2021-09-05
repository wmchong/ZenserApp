package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityForgotPasswordBinding
import com.example.zenserapp.ui.MyDBHelper
import com.google.firebase.auth.FirebaseAuth

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
           val getEmail=binding.etEmailReset.text.toString()
           if(getEmail.isEmpty()){
               Toast.makeText(this,"Enter Email Address",Toast.LENGTH_SHORT).show()
           }
           else if(!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()){
               Toast.makeText(this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show()

           }
           else{
               FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail)
                   .addOnCompleteListener{
                       if(it.isSuccessful){
                           Toast.makeText(this,"Check your email to reset ur password!",Toast.LENGTH_SHORT).show()

                       }
                       else{
                           Toast.makeText(this,"Something Wrong Happened",Toast.LENGTH_SHORT).show()

                       }
                   }
           }
       }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}