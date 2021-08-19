package com.example.zenserapp.ui

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context,"LOGIN_REGISTER",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,USERNAME TEXT,EMAIL TEXT,PASSWORD TXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS USERS")
        onCreate(db)
    }

    fun insert(name:String,username:String,email:String,password:String):Boolean{
        val sqLiteDatabase=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("NAME", name)
        contentValues.put("USERNAME", username)
        contentValues.put("EMAIL", email)
        contentValues.put("PASSWORD", password)
        sqLiteDatabase.insert("USERS", null, contentValues)
        return true
    }

    fun verifyUsernamePassword(username:String,password:String):Boolean{
        val sqLiteDatabase=this.readableDatabase
        val usernamePassword = listOf<String>(username, password).toTypedArray()
        val cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?",
            usernamePassword
        )
        return cursor.count>0

    }
    fun checkUsernameEmail(username:String,email:String):Boolean{
        val sqLiteDatabase=this.readableDatabase
        val usernameEmail = listOf<String>(username, email).toTypedArray()
        val cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM USERS WHERE USERNAME = ? AND EMAIL = ?",
            usernameEmail
        )
        return cursor.count>0

    }
    fun updatePassword(username: String, email: String, password: String):Boolean{
        val sqLiteDatabase=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("PASSWORD",password)
        val usernameEmail= listOf<String>(username,email).toTypedArray()
        val result=sqLiteDatabase.update("USERS",contentValues,"USERNAME= ? AND EMAIL =?",usernameEmail)
        return result != -1
    }


}