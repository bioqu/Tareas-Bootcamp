package cl.uchile.dcc.mobile.gastospersonales.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cl.uchile.dcc.mobile.gastospersonales.model.database.ExpenseRegistryDatabase.Companion.MIGRATION_1_2

@Database(
    entities = [GastosRegistry::class],
    version = 3,
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
                ).addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE GastosRegistry ADD COLUMN fecha INTEGER NOT NULL DEFAULT 0"
                )
                // opcional: marcar los viejos como "hoy"
                val now = System.currentTimeMillis()
                db.execSQL("UPDATE GastoRegistry SET fecha = $now WHERE fecha = 0")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE GastosRegistry ADD COLUMN fecha INTEGER NOT NULL DEFAULT 0"
                )

                val now = System.currentTimeMillis()

                db.execSQL(
                    "UPDATE GastosRegistry SET fecha = $now WHERE fecha = 0"
                )
            }
        }

    }

}