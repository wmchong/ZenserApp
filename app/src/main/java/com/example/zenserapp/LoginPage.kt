package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityLoginPageBinding
import com.example.zenserapp.ui.MyDBHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //database
        val helperDB= MyDBHelper(applicationContext)
       // val myDB = helperDB.readableDatabase

        binding.tvForgotpassword.setOnClickListener {
            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

         binding.bLogin.setOnClickListener {
             val getUsername: String = binding.etUsernameLogin.text.toString()
             val getPassword: String = binding.etPasswordLogin.text.toString()

             if (getUsername == "" || getPassword == "") {
                 binding.tvStatusLogin.text = "Fields Are Empty"
             } else {
               FirebaseAuth.getInstance().signInWithEmailAndPassword(getUsername,getPassword)
                   .addOnCompleteListener {
                       if (!it.isSuccessful) {
                           Toast.makeText(
                               this,
                               "Failed Login: " + it.exception!!.message,
                               Toast.LENGTH_SHORT
                           ).show()

                           return@addOnCompleteListener
                       }
                       val intent=Intent(this,MainActivity::class.java)
                       startActivity(intent)
                      // Toast.makeText(this, "Successfully Logged in", Toast.LENGTH_SHORT).show()


                   }
             }
         }
         binding.bCreateNewAccount.setOnClickListener{
             val intent= Intent(this,RegisterPage::class.java)
             startActivity(intent)
         }

    }




}