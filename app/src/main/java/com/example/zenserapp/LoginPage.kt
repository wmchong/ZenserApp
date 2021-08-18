package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.zenserapp.databinding.ActivityLoginPageBinding
import com.example.zenserapp.databinding.FragmentMeBinding
import com.example.zenserapp.ui.MyDBHelper
import com.example.zenserapp.ui.me.MeFragment

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //database
        val helperDB= MyDBHelper(applicationContext)
       // val myDB = helperDB.readableDatabase

         binding.bLogin.setOnClickListener {
             val getUsername: String = binding.etUsernameLogin.text.toString()
             val getPassword: String = binding.etPasswordLogin.text.toString()
             if (getUsername == "" || getPassword == "") {
                 binding.tvStatusLogin.text = "Fields are empty"
             } else {
               val verify= helperDB.verifyUsernamePassword(getUsername,getPassword)
                 if (verify) {
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("USERNAME",getUsername)
                     startActivity(intent)
                 } else {
                     binding.tvStatusLogin.text = "username or password is incorrect"
                 }
             }
         }
         binding.bCreateNewAccount.setOnClickListener{
             val intent= Intent(this,RegisterPage::class.java)
             startActivity(intent)
         }
    }

}