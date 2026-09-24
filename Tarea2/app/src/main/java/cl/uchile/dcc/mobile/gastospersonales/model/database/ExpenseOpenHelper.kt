package cl.uchile.dcc.mobile.gastospersonales.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ExpenseOpenHelper(context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    override fun onCreate(db: SQLiteDatabase?) {
        val tableGastosRegistry = """
            CREATE TABLE $TABLE_GASTOS_REGISTRY(
               _id INTEGER PRIMARY KEY AUTOINCREMENT,
               concepto TEXT,
               monto INTEGER
              )
        """.trimIndent()
        db?.execSQL(tableGastosRegistry)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldversion: Int,
        newversion: Int
    ) {
        if(newversion > oldversion){
            Log.d("ExpenseOpenHelper", "Actualizando de v$oldversion a $newversion")
            //
        }
    }

    companion object{
        const val DATABASE_NAME = "expenses.db"
        const val DATABASE_VERSION = 1
        const val TABLE_GASTOS_REGISTRY = "GastosRegistry"
    }
}