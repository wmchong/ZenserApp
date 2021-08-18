package com.example.zenserapp.ui.chat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zenserapp.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.zenserapp.databinding.ActivityChatPageBinding


class ChatPage : AppCompatActivity() {

    private lateinit var binding:ActivityChatPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val bundle : Bundle?= intent.extras
        //val name = bundle!!.getString("name")
        val picture = bundle!!.getInt("picture")

        val sellerName = intent?.getStringExtra("SELLERNAME")

        //action bar
        val actionbar = supportActionBar
        actionbar!!.title = sellerName
        //back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        actionbar.setIcon(picture)

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

