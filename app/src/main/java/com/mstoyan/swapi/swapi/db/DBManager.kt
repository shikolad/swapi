package com.mstoyan.swapi.swapi.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mstoyan.swapi.swapi.network.GsonConfig
import com.mstoyan.swapi.swapi.network.services.Person

class DBManager {

    private var dbHelper: DBHelper? = null
    private var database: SQLiteDatabase? = null

    fun open(context: Context): DBManager{
        dbHelper = DBHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close(){
        database!!.close()
    }

    fun insertPersons(people: List<Person>){
        for (person in people){
            val contentValue = ContentValues()
            contentValue.put(DBHelper.tableData.PERSON_JSON, GsonConfig.defaultGson.toJson(person))
            database!!.insert(DBHelper.tableData.TABLE_NAME, null, contentValue)
        }
    }

    fun getAllPersons(): List<Person>{
        val result = ArrayList<Person>()
        val columns = arrayOf(DBHelper.tableData.PERSON_JSON)
        val cursor = database!!.query(DBHelper.tableData.TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()){
            val jsonPosition = cursor.getColumnIndex(DBHelper.tableData.PERSON_JSON)
            do{
                val person = GsonConfig.defaultGson.fromJson(cursor.getString(jsonPosition), Person::class.java)
                result.add(person)
            }while (cursor.moveToNext())
            cursor.close()
        }
        return result
    }
}