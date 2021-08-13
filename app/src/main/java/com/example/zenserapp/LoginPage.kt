package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.zenserapp.ui.MyDBHelper

class LoginPage : AppCompatActivity() {
    private lateinit var status: TextView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        //database
        var helperDB= MyDBHelper(applicationContext)
        var myDB = helperDB.readableDatabase

         status=findViewById(R.id.tv_status_login)
         username=findViewById(R.id.et_username_login)
         password=findViewById(R.id.et_password_login)

         loginButton = findViewById(R.id.b_login)
         registerButton = findViewById(R.id.b_create_new_account)

         loginButton.setOnClickListener {
             val getUsername:String=username.text.toString()
             val getPassword:String=password.text.toString()
             val usernamePassword= listOf<String>(getUsername,getPassword).toTypedArray()
             val rs= myDB.rawQuery("SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?",usernamePassword)

             if (rs.moveToNext()) {
                 val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
             }
             else{
                  status.text = "username or password is incorrect"
             }
         }
         registerButton.setOnClickListener{
             val intent= Intent(this,RegisterPage::class.java)
             startActivity(intent)
         }
    }

}