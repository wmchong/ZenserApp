package com.example.zenserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.zenserapp.databinding.ActivityHelpPageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HelpPage : AppCompatActivity() {
    private lateinit var binding: ActivityHelpPageBinding

    val details = "Telephone: +65 88886666\n" +
            "Monday - Sunday, 9am - 5pm(GMT)\n" +
            "\n" +
            "Email:\n" +
            "Have a question/problem?\n" +
            "Please write here, and we will reply back in 2-3 business days"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityHelpPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Help Centre"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding.detailTV.text = details

       // binding.submitBtn.setOnClickListener {
            //val intent =Intent(this,HelpPage::class.java)
            //startActivity(intent)
        //}
    }

    fun showAlertDialog(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Feedback Received")
            .setMessage("Thank you!\n" +
                    "We have received your email and will be attended to in 2-3 business days.")
            .setNeutralButton("Dismiss") { dialog, which ->
                showSnackbar("Feedback sent")
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