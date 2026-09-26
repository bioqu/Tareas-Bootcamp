package cl.uchile.dcc.mobile.gastospersonales.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseRegistryDAO {
    @Insert
    suspend fun addGastosRegistry(gastosRegistry: GastosRegistry)

    @Query("SELECT * FROM GastosRegistry")
    suspend fun getAllGastosRegistry(): List<GastosRegistry>

    @Query("SELECT * FROM GastosRegistry WHERE id = :id")
    suspend fun getOneGastosRegistry(id: String): List<GastosRegistry>

    @Update
    suspend fun updateGastosRegistry(gastosRegistry: GastosRegistry)

    @Delete
    suspend fun deleteGastosRegistry(gastosRegistry: GastosRegistry)

    @Query(
        """
        SELECT COALESCE(SUM(monto), 0) FROM GastosRegistry
        WHERE fecha >= :inicioDia
        """
    )
    fun totalDesde(inicioDia: Long): Flow<Int>

    @Query(
        """
        SELECT * FROM GastosRegistry
        WHERE fecha >= :inicioDia
        ORDER BY fecha DESC
        """
    )
    fun gastosDesde(inicioDia: Long): Flow<List<GastosRegistry>>
}