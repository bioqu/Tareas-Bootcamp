package cl.uchile.dcc.mobile.gastospersonales.model.repository

import android.content.Context
import cl.uchile.dcc.mobile.gastospersonales.model.database.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.model.database.ExpenseRegistryDatabase
import kotlinx.coroutines.flow.Flow

class ExpenseDataRepository(
    private val context: Context
) {
    // database
    private val database = ExpenseRegistryDatabase.getInstance(context)
    private val dao = database.expenseRegistryDAO

    fun totalDesde(inicioDia: Long): Flow<Int> {
        return dao.totalDesde(inicioDia)
    }

    // Create del CRUD
    suspend fun addGastosRegistry(gastosRegistry: GastosRegistry) {
        return dao.addGastosRegistry(gastosRegistry)
//        val db = dbHelper.writableDatabase
//        val values = ContentValues().apply {
//            put("concepto", gastosRegistry.concepto)
//            put("monto", gastosRegistry.monto)
//        }
//        db.insert("GastosRegistry", null, values)
//        db.close()
    }

    // READ del crud
    suspend fun getAllGastosRepository(): List<GastosRegistry> {
        return dao.getAllGastosRegistry()
    }

    // UPDATE del crud
    suspend fun updateGastosRepository(gastosRegistry: GastosRegistry) {
        return dao.updateGastosRegistry(gastosRegistry)

    }

    // DELETE del crud
    suspend fun deleteGastosRegistry(gastosRegistry: GastosRegistry) {
        return dao.deleteGastosRegistry(gastosRegistry)
    }

}