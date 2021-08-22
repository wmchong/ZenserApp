package com.example.zenserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.zenserapp.databinding.ActivitySettingPageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SettingPage : AppCompatActivity() {
    private lateinit var binding: ActivitySettingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySettingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_setting_page)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Setting"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        //get username from login activity
        //val username= intent?.getStringExtra("USERNAME")
        //set username
        //binding.username2TV.text=username

        binding.helpBtn.setOnClickListener {
            val intent =Intent(this,HelpPage::class.java)
            startActivity(intent)
        }

    }

    fun showAlertDialog(view: View) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure you want to deactivate this account?")
            .setNegativeButton("Cancel") { dialog, which ->
                showSnackbar("Deactivation of account canceled")
            }
            .setPositiveButton("Deactivate Account"){ dialog, which ->
                val intent =Intent(this,LoginPage::class.java)
                startActivity(intent)
                showSnackbar("Account have been successfully deactivated")
            }
            .show()

    }

    private fun showSnackbar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}