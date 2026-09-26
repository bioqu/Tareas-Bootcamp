package cl.uchile.dcc.mobile.gastospersonales.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

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
