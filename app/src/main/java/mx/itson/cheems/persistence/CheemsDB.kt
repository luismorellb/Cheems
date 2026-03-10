package mx.itson.cheems.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CheemsDB(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int) :
    SQLiteOpenHelper
        (context, name, factory, version) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("CREATE TABLE Winner (id INT PRIMARY KEY AUTOINCREMENT, name TEXT, nickname TEXT)")
        } catch (ex: Exception){
            Log.e("Error al crear la base de datos", ex.message.toString())
        }
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {
    }
}