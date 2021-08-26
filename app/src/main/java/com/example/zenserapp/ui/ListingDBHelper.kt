package com.example.zenserapp.ui

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class ListingDBHelper(context: Context): SQLiteOpenHelper(context, "LISTINGDETAILS",null,1){

    companion object{
        private const val TBL_LISTINGDETAILS = "tbl_listingdetails"
        private const val ID = "id"
        private const val CATEGORY = "category"
        private const val TITLE = "title"
        private const val PRICE = "price"
        private const val CONDITION = "condition"
        private const val DESCRIPTION = "description"
        private const val DEALMETHOD = "dealmethod"
        private const val USERID = "userid"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblListing = ("CREATE TABLE " + TBL_LISTINGDETAILS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT," + PRICE + " REAL,"
                + CONDITION + " TEXT," + DESCRIPTION + " TEXT,"
                + DEALMETHOD + " TEXT," + CATEGORY + " TEXT,"
                + USERID + " INTEGER,"
                + "FOREIGN KEY (" + USERID + ") REFERENCES USERS (USERID)"
                + ")")
        db?.execSQL(createTblListing)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_LISTINGDETAILS")
        onCreate(db)
    }

    fun insert(lst: ListingModel):Long{
        val sqLiteDatabase=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(TITLE, lst.title)
        contentValues.put(PRICE, lst.price)
        contentValues.put(CONDITION, lst.condition)
        contentValues.put(DESCRIPTION, lst.description)
        contentValues.put(DEALMETHOD, lst.dealmethod)
        contentValues.put(CATEGORY, lst.category)
        contentValues.put(USERID,lst.userid)

        val success = sqLiteDatabase.insert(TBL_LISTINGDETAILS, null, contentValues)
        sqLiteDatabase.close()
        return success
    }

    fun getListings(keyword:String):ArrayList<ListingModel>{
        val listingList : ArrayList<ListingModel> = ArrayList()
        val pattern = "%$keyword％"
        Log.e("pattern","$pattern")
        val selectQuery = "SELECT * FROM $TBL_LISTINGDETAILS WHERE $TITLE LIKE '%${keyword}%'"
        val sqLiteDatabase=this.readableDatabase

        val cursor: Cursor?
        try{
            cursor = sqLiteDatabase.rawQuery(selectQuery,null)
            Log.e("cursor column count","${cursor.columnCount}")
            Log.e("cursor column name","${cursor.columnNames}")
        } catch(e:Exception) {
            e.printStackTrace()
            sqLiteDatabase.execSQL(selectQuery)
            return ArrayList()
        }

        var title:String
        var price:Double
        var condition:String
        var description:String
        var dealmethod:String
        var category:String
        var userid :Int

        if(cursor.moveToFirst()){
            do{
                title = cursor.getString(cursor.getColumnIndex("title"))
                Log.e("cursor title","$title")
                price = cursor.getDouble(cursor.getColumnIndex("price"))
                condition = cursor.getString(cursor.getColumnIndex("condition"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                dealmethod = cursor.getString(cursor.getColumnIndex("dealmethod"))
                category = cursor.getString(cursor.getColumnIndex("category"))
                userid = cursor.getInt(cursor.getColumnIndex("userid"))

                val listing = ListingModel(
                    title = title, price = price,
                                        condition = condition, description = description,
                                        dealmethod = dealmethod, category = category, userid = userid)
                Log.e("cursor add to list","${listing.title}")
                listingList.add(listing)
            }while(cursor.moveToNext())
        }
        return listingList
    }

    //clean the database
    fun deleteAllRows(){
        val sqLiteDatabase=this.writableDatabase
        Log.e("start deleting...","deleting table")
        sqLiteDatabase.delete("tbl_listingdetails", null, null)
        sqLiteDatabase.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME = '$TBL_LISTINGDETAILS' ")
        Log.e("finish deleting...","complete delete")
        sqLiteDatabase.close()
    }
}