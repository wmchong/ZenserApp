package com.example.zenserapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityRegisterPageBinding
import com.example.zenserapp.ui.MyDBHelper

class RegisterPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //database
        val helperDB= MyDBHelper(applicationContext)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Create New Account"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.bRegister.setOnClickListener{
            val getName=binding.etFullnameRegister.text.toString()
            val getUsername=binding.etUsernameRegister.text.toString()
            val getEmail=binding.etEmailRegister.text.toString()
            val getPassword=binding.etPasswordRegister.text.toString()
            val getPasswordConfirm=binding.etPasswordConfirmRegister.text.toString()

            if(getName==""||getUsername==""||getEmail==""||getPassword==""||getPasswordConfirm==""){
                binding.tvStatusRegister.setText("Fields are empty")
            }
            else {
                if (getPassword == getPasswordConfirm) {
                    helperDB.insert(getName,getUsername,getEmail,getPassword)
                    Toast.makeText(
                        applicationContext,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                } else {
                    binding.tvStatusRegister.text = "Password does not match"
                    binding.etPasswordRegister.setText("")
                    binding.etPasswordConfirmRegister.setText("")
                    binding.etPasswordRegister.requestFocus()
                }
            }

            }
        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}