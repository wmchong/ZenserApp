package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityResetPasswordBinding
import com.example.zenserapp.ui.MyDBHelper

class ResetPassword : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Reset Password"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val helperDB = MyDBHelper(applicationContext)

        // val intent=getIntent()
        val username = intent?.getStringExtra("username").toString()
        val email = intent?.getStringExtra("email").toString()

        binding.bReset.setOnClickListener {
            val getPassword = binding.etPasswordReset.text.toString()
            val getPasswordConfirm = binding.etPasswordConfirmReset.text.toString()

            if (getPassword == "" || getPasswordConfirm == "") {
                Toast.makeText(applicationContext, "Fields Are Empty", Toast.LENGTH_SHORT).show()
                binding.tvStatusReset.setText("Enter New Password")
            }
            else{
                if (getPassword == getPasswordConfirm) {
                    val checkDetails = helperDB.updatePassword(username, email, getPassword)
                    if (checkDetails) {
                        val intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            applicationContext,
                            "Password Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()

                    }

                } else {
                    binding.tvStatusReset.text = "Password Does Not Match"
                    binding.etPasswordReset.setText("")
                    binding.etPasswordConfirmReset.setText("")
                    binding.etPasswordReset.requestFocus()
                }
            }
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}