package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

class RegistryViewModel : ViewModel() {
    val gastos: MutableList<GastosRegistry> = mutableListOf()

    fun addGasto(concepto: String, monto: Int) {
        val gasto = GastosRegistry(concepto, monto)
        gastos.add(gasto)
    }
}