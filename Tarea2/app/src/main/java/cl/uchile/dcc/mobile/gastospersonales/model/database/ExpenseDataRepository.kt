package cl.uchile.dcc.mobile.gastospersonales.model.database

import android.content.ContentValues
import android.content.Context
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

class ExpenseDataRepository(
    private val context: Context
) {
    private val dbHelper = ExpenseOpenHelper(context)

    // Create del CRUD
    fun addGastosRegistry(gastosRegistry: GastosRegistry) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("_id", gastosRegistry.id)
            put("concepto", gastosRegistry.concepto)
            put("monto", gastosRegistry.monto)
        }
        db.insert("GastosRegistry", null, values)
        db.close()
    }

    // READ del crud
    fun getAllGastosRepository(): List<GastosRegistry> {
        val gatosRegistryList = mutableListOf< GastosRegistry>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ExpenseOpenHelper.TABLE_GASTOS_REGISTRY,  // Tabla
            null,        // Columnas a retornar
            null,              // Sin cláusula WHERE (trae todos)
            null,              // Sin argumentos para el WHERE
            null,              // Sin GROUP BY
            null,              // Sin HAVING
            "_id DESC"               // Sin ORDER BY (o puedes poner "_id ASC")
        )

        // Después debes recorrer el cursor para llenar la lista
        cursor.use {
            it.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow("_id"))
                    val concepto = getString(getColumnIndexOrThrow("concepto"))
                    val monto = getInt(getColumnIndexOrThrow("monto"))

                    gatosRegistryList.add(GastosRegistry(id.toString(), concepto, monto))
                }
            }
        }
        db.close()
        return gatosRegistryList
    }

    // UPDATE del crud
    fun updateGastosRepository(gastosRegistry: GastosRegistry) {

    }
}