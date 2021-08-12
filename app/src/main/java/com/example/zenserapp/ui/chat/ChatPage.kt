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


class ChatPage : AppCompatActivity() {
    //private lateinit var c: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)

//        val btn = findViewById<ImageButton>(R.id.imgBack)
//        btn.setOnClickListener {
//            val bIntent = Intent(c, ChatPage::class.java)
//            c.startActivity(bIntent)
//        }

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = "Chat Page"
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

