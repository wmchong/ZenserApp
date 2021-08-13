package com.example.zenserapp.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context,"LOGIN_REGISTER",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,USERNAME TEXT,EMAIL TEXT,PASSWORD TXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}