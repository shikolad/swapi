package com.mstoyan.swapi.swapi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context?
) : SQLiteOpenHelper(context, "persons", null, 1) {

    object tableData{
        const val TABLE_NAME = "persons"
        const val COLUMN_ID = "id"
        const val PERSON_JSON = "json_data"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${tableData.TABLE_NAME} (" +
                "${tableData.COLUMN_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "${tableData.PERSON_JSON} TEXT NOT NULL UNIQUE ON CONFLICT IGNORE)"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("do nothing yet") //To change body of created functions use File | Settings | File Templates.
    }
}