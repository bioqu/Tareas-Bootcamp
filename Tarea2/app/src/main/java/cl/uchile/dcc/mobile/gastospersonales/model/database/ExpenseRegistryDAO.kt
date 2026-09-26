package cl.uchile.dcc.mobile.gastospersonales.model.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

@Dao
interface ExpenseRegistryDAO {
    // CREATE
    @Insert
    suspend fun addGastosRegistry(gastosRegistry: GastosRegistry): Long

    // READ
    @Query("SELECT * FROM GastosRegistry")
    suspend fun getAllGastosRegistry(): List<GastosRegistry>

    @Query("SELECT * FROM GastosRegistry WHERE id = :id")
    suspend fun getOneGastosRegistry(): List<GastosRegistry>

    // UPDATE
    @Update
    suspend fun updateGastosRegistry(gastosRegistry: GastosRegistry)

    // UPDATE
    @Delete
    suspend fun deleteGastosRegistry(gastosRegistry: GastosRegistry)


}