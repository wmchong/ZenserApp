package com.example.zenserapp

import android.R.id
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityRegisterPageBinding
import com.example.zenserapp.ui.MyDBHelper
import com.google.firebase.auth.FirebaseAuth
import android.R.id.message

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase


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

        binding.bRegister.setOnClickListener {

            val getEmail = binding.etEmailRegister.text.toString()
            val getPassword = binding.etPasswordRegister.text.toString()
            val getPasswordConfirm = binding.etPasswordConfirmRegister.text.toString()

            if (getEmail.isEmpty() || getPassword.isEmpty() || getPasswordConfirm.isEmpty()) {
                binding.tvStatusRegister.text = "Fields Are Empty"
            }
            else
            {
                if(getPassword==getPasswordConfirm){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(getEmail, getPassword)
                        .addOnCompleteListener {
                            if (!it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Failed Registration: " + it.exception!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@addOnCompleteListener
                            }
                            saveUserToFirebaseDatabase()
                            val intent=Intent(this,LoginPage::class.java)
                            startActivity(intent)

                            Toast.makeText(this, "Successfully created", Toast.LENGTH_SHORT).show()


                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        }
                }
                else{
                    binding.tvStatusRegister.text = "Password Does Not Match"
                }
            }
                //    Log.d("REGISTERPAGE", "Email is: $getEmail")
                //  Log.d("REGISTERPAGE", "Password is: $getPassword")



            }

        }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun saveUserToFirebaseDatabase(){
        val getName = binding.etFullnameRegister.text.toString()
        val getUsername = binding.etUsernameRegister.text.toString()
        val getEmail=binding.etEmailRegister.text.toString()
        val uid =FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid!!,getName,getUsername,getEmail)
        ref.setValue(user)
            .addOnSuccessListener {
            Log.d("RegisterActivity","User saved to database")
        }
    }
}

class User(val uid:String ,val fullname:String,val username:String,val email: String)