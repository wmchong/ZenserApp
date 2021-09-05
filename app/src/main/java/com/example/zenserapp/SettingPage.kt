package com.example.zenserapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import com.example.zenserapp.databinding.ActivitySettingPageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import kotlinx.android.parcel.Parcelize

class SettingPage : AppCompatActivity() {
    private lateinit var binding: ActivitySettingPageBinding
    private lateinit var db:DatabaseReference
    private lateinit var uid:String
    val getUserid =FirebaseAuth.getInstance().uid
    var setStatus="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Setting"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.helpBtn.setOnClickListener {
            val intent =Intent(this,HelpPage::class.java)
            startActivity(intent)
        }

        db= FirebaseDatabase.getInstance().getReference("users")
        uid= FirebaseAuth.getInstance().currentUser?.uid.toString()
        if(uid.isNotEmpty()){
            getUserData()
            getUserTheme()
        }
        else{
            Log.d("Connection","Failed")
        }

        binding.saveBtn.setOnClickListener{
            saveUserToFirebaseDatabase()
        }

        binding.appearanceTB.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                //Dark
                showSnackbar("Dark mode enabled")

                setStatus="1"

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()

                saveUserThemeToFirebaseDatabase()
            } else {
                setStatus="0"

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()

                //Light
                showSnackbar("Light mode enabled")

                saveUserThemeToFirebaseDatabase()
            }
        }
    }

    fun deleteUserData(){
        val dR = FirebaseDatabase.getInstance().getReference("users").child(getUserid!!)
        dR.removeValue()
    }

    fun getUserData(){
        db.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user_name=snapshot.child("username").getValue(String::class.java).toString()
                val full_name=snapshot.child("fullname").getValue(String::class.java).toString()
                val email=snapshot.child("email").getValue(String::class.java).toString()
                val mobile=snapshot.child("mobile").getValue(String::class.java).toString()
                val address=snapshot.child("addr").getValue(String::class.java).toString()
                val theme=snapshot.child("theme").getValue(String::class.java).toString()
                Log.d("meFrag",user_name)
                binding.username2TV.text=user_name
                binding.fullnameET.setText(full_name)
                binding.emailET.setText(email)
                binding.mobileET.setText(mobile)
                binding.addressET.setText(address)
                //setTheme=theme
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }
        })
    }

    private fun saveUserToFirebaseDatabase(){
        val getName = binding.fullnameET.text.toString()
        val getUsername = binding.username2TV.text.toString()
        val getEmail=binding.emailET.text.toString()
        val getMobile=binding.mobileET.text.toString()
        val getAddr=binding.addressET.text.toString()

        val uid =FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid!!,getName,getUsername,getEmail,getMobile,getAddr)
        ref.setValue(user)
            .addOnSuccessListener {
                showSnackbar("New profile details saved ")
            }
    }

    fun showAlertDialog(view: View) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure you want to deactivate this account?")
            .setNegativeButton("Cancel") { dialog, which ->
                showSnackbar("Deactivation of account cancelled")
            }
            .setPositiveButton("Deactivate Account"){ dialog, which ->
                val intent =Intent(this,LoginPage::class.java)
                startActivity(intent)

                showSnackbar("Account have been successfully deactivated")

                //Delete user details in Authentication in Firebase
                val user = FirebaseAuth.getInstance().getCurrentUser()
                user!!.delete()

                //Delete user details in users tree in Firebase
                deleteUserData()

            }
            .show()
    }

    private fun showSnackbar(msg: String) {
        val snackBar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(Color.parseColor("#EDEAEA"))
        snackBar.setTextColor(Color.parseColor("#000000"))
        snackBar.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun getUserTheme(){
        db= FirebaseDatabase.getInstance().getReference("theme")
        db.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status=snapshot.child("status").getValue(String::class.java).toString()
                setStatus=status

                if (setStatus == "1"){
                    binding.appearanceTB.setChecked(true)

                } else {
                    binding.appearanceTB.setChecked(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error",error.toString())
            }
        })
    }

    private fun saveUserThemeToFirebaseDatabase(){
        val getStatus=setStatus
        val uid =FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/theme/$uid")
        val userTheme=UserTheme(uid!!,getStatus)
        ref.setValue(userTheme)
            .addOnSuccessListener {
                //showSnackbar("New appearance saved ")
            }
    }
}

@Parcelize
class UserTheme(val uid:String ,val status:String):
    Parcelable {
    constructor() : this("","")
}