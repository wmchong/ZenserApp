package com.example.zenserapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.zenserapp.ui.MyDBHelper

class RegisterPage : AppCompatActivity() {

    private lateinit var status: TextView
    private lateinit var name: EditText
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var passwordConfirm: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        //database
        val helperDB= MyDBHelper(applicationContext)
        val myDB=helperDB.readableDatabase

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Create New Account"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        status=findViewById(R.id.tv_status_register)

        name=findViewById(R.id.et_fullname_register)
        username=findViewById(R.id.et_username_register)
        email=findViewById(R.id.et_email_register)
        password=findViewById(R.id.et_password_register)
        passwordConfirm=findViewById(R.id.et_password_confirm_register)

        registerButton=findViewById(R.id.b_register)

        registerButton.setOnClickListener{
            val getName=name.text.toString()
            val getUsername=username.text.toString()
            val getEmail=email.text.toString()
            val getPassword=password.text.toString()
            val getPasswordConfirm=passwordConfirm.text.toString()
            if(getName==""||getUsername==""||getEmail==""||getPassword==""||getPasswordConfirm==""){
                status.setText("Fields are empty")
            }
            else {
                if (password.text.toString() == passwordConfirm.text.toString()) {
                    val contentValues = ContentValues()
                    contentValues.put("NAME", name.text.toString())
                    contentValues.put("USERNAME", username.text.toString())
                    contentValues.put("EMAIL", email.text.toString())
                    contentValues.put("PASSWORD", password.text.toString())
                    myDB.insert("USERS", null, contentValues)

                    Toast.makeText(
                        applicationContext,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, LoginPage::class.java)
                    startActivity(intent)
                } else {
                    status.text = "Password does not match"
                    password.setText("")
                    passwordConfirm.setText("")
                    password.requestFocus()
                }
            }

            }
        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}