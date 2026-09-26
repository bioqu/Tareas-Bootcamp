package cl.uchile.dcc.mobile.gastospersonales.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GastosRegistry")
data class GastosRegistry(
    // Llave primara ID
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "concepto")
    var concepto: String,
    @ColumnInfo(name = "monto")
    var monto: Int
)