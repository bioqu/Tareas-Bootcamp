package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

class RegistryViewModel : ViewModel() {
    val gastos: MutableList<GastosRegistry> = mutableListOf()
    var concepto by mutableStateOf("")
    var monto by mutableStateOf("")

    fun addGasto(concepto: String, monto: Int) {
        val gasto = GastosRegistry(concepto, monto)
        gastos.add(gasto)
    }

    fun resetConcepto() {
        concepto = ""
    }

    fun resetMonto() {
        monto = ""
    }
}