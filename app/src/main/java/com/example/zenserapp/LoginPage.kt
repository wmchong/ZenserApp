package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.databinding.ActivityLoginPageBinding
import com.example.zenserapp.ui.MyDBHelper


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
               val verify= helperDB.verifyUsernamePassword(getUsername,getPassword)
                 if (verify) {
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("USERNAME",getUsername)
                     intent.putExtra("USERID",getUsername)
                     startActivity(intent)
                 } else {
                     binding.tvStatusLogin.text = "username or password is incorrect"
                     binding.etPasswordLogin.setText("")
                     binding.etUsernameLogin.requestFocus()
                 }
             }
         }
         binding.bCreateNewAccount.setOnClickListener{
             val intent= Intent(this,RegisterPage::class.java)
             startActivity(intent)
         }

    }

}