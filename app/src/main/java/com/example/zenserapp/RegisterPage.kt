package com.example.zenserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterPage : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Create New Account"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        name=findViewById(R.id.et_fullname_register)
        username=findViewById(R.id.et_username_register)
        email=findViewById(R.id.et_email_register)
        password=findViewById(R.id.et_password_register)
        registerButton=findViewById(R.id.b_register)

        registerButton.setOnClickListener{


            }
        }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}