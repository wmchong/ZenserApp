package com.example.zenserapp.ui.chat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityChatPageBinding
import com.example.zenserapp.databinding.ActivitySingleListing1Binding


class ChatPage : AppCompatActivity() {
    //private lateinit var c: Context
    private lateinit var binding:ActivityChatPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //Payal pre
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat_page)

        //Dana edit
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val btn = findViewById<ImageButton>(R.id.imgBack)
//        btn.setOnClickListener {
//            val bIntent = Intent(c, ChatPage::class.java)
//            c.startActivity(bIntent)
//        }

        //action bar
        val actionbar = supportActionBar

        //get buyer name
        val sellerName = intent?.getStringExtra("SELLERNAME")
        actionbar!!.title = sellerName

        //actionbar!!.title = "Chat Page"
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        actionbar.setIcon(R.drawable.ic_baseline_search_24)

        actionbar.setDisplayUseLogoEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_icon -> Toast.makeText(this, "Icon selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

