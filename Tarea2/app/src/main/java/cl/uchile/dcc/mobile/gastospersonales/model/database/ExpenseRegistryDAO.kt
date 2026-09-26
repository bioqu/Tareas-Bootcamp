package cl.uchile.dcc.mobile.gastospersonales.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseRegistryDAO {
    // CREATE
    @Insert
    suspend fun addGastosRegistry(gastosRegistry: GastosRegistry): Long

    // READ
    @Query("SELECT * FROM GastosRegistry")
    suspend fun getAllGastosRegistry(): List<GastosRegistry>

    @Query("SELECT * FROM GastosRegistry WHERE id = :id")
    suspend fun getOneGastosRegistry(id: String): List<GastosRegistry>

    // UPDATE
    @Update
    suspend fun updateGastosRegistry(gastosRegistry: GastosRegistry)

    // UPDATE
    @Delete
    suspend fun deleteGastosRegistry(gastosRegistry: GastosRegistry)


}