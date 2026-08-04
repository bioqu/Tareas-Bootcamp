package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

class RegistryViewModel : ViewModel() {
    val gastos = mutableStateListOf<GastosRegistry>()
    var concepto by mutableStateOf("")
    var monto by mutableStateOf("")

    var errorConcepto by mutableStateOf<String?>(null)
        private set

    fun onChangeConcepto(nuevoValor: String) {
        concepto = nuevoValor

        errorConcepto = when {
            nuevoValor.isBlank() || nuevoValor.isEmpty() -> "El concepto no puede estar vacío"
            nuevoValor.length < 3 -> "El concepto debe tener más de 3 caracteres"
            else -> null
        }
    }

    // Estado con error
    val isValidConcepto: Boolean
        get() = concepto.isNotEmpty() && errorConcepto == null

    /* Prueba */
    /* Prueba */
    /* Prueba */

    var errorMonto by mutableStateOf<String?>(null)
        private set

    fun onChangeMonto(nuevoValor: String) {
        // Opción A: Solo aceptar números (filtra lo que no sea dígito)
        monto = nuevoValor.filter { it.isDigit() }

        errorMonto = when {
            monto.isEmpty() -> "El monto no puede estar vacío"
            monto.toIntOrNull() == null -> "Ingresa un monto válido"
            else -> null
        }
    }

    // Estado con error
    val isValidMonto: Boolean
        get() = monto.isNotEmpty() && errorMonto == null

    /* Prueba */
    /* Prueba */
    /* Prueba */

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