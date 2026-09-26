package cl.uchile.dcc.mobile.gastospersonales.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(
    entities = [GastosRegistry::class],
    version = 2,
    exportSchema = false,
)

abstract class ExpenseRegistryDatabase: RoomDatabase() {
    abstract val expenseRegistryDAO: ExpenseRegistryDAO

    companion object {
        @Volatile
        private var INSTANCE: ExpenseRegistryDatabase? = null

        fun getInstance(context: Context): ExpenseRegistryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =  databaseBuilder(
                    context.applicationContext,
                    ExpenseRegistryDatabase::class.java,
                    "gastos_registry_database"
                )//.addMigrations(MIGRATIONS_1_TO_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
//        val MIGRATIONS_1_TO_2 = object : Migration(1, 2) {
//            override suspend fun migrate(connection: SQLiteConnection) {
//                connection.execSQL("ALTER TABLE GastosRegistry ADD COLUMN tipo:id TEXT")
//            }
//        }
    }

}