package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

// RegistryViewModel :: viewModel()
// Genera la lógica de concepto y monto
class RegistryViewModel : ViewModel() {
    // Definición de gastos como mutableStateListof de  GastosRegistry
    val gastos = mutableStateListOf<GastosRegistry>()

    // Definición de concepto de gasto y monto de gasto
    var concepto by mutableStateOf("")
    var monto by mutableStateOf("")

    // Tratamiento de datos y errores relacionado a concepto de gasto
    var errorConcepto by mutableStateOf<String?>(null)
        private set

    // onChangeConcepto contiene la lógica de los chequeos del dato que se va a ingresar como concepto en addGastos
    fun onChangeConcepto(nuevoValor: String) {
        concepto = nuevoValor

        errorConcepto = when {
            nuevoValor.isBlank() || nuevoValor.isEmpty() -> "El concepto no puede estar vacío"
            nuevoValor.length < 3 -> "El concepto debe tener más de 3 caracteres"
            else -> null
        }
    }

    // Implementación error de concepto en Gastos
    val isValidConcepto: Boolean
        get() = concepto.isNotEmpty() && errorConcepto == null

    // Tratamiento de datos y errores relacionado a concepto de gasto
    // Implementación error de Monto en Gastos
    var errorMonto by mutableStateOf<String?>(null)
        private set


    // Función auxiliar para verificar notificar al usuario que en el InputText de monto solo puede
    // ingresar números
    private fun esSoloNumeros(texto: String): Boolean {
        return texto.toIntOrNull() != null
    }

    // onChangeMonto contiene la lógica de los chequeos del dato que se va a ingresar como monto en addGastos
    fun onChangeMonto(nuevoValor: String) {
        // Solo aceptar números (filtra lo que no sea dígito)
        if (nuevoValor.isEmpty() || esSoloNumeros(nuevoValor)) {
            monto = nuevoValor
        }
        // Errores de monto
        errorMonto = when {
            monto.isEmpty() -> "Ingresa un monto para tu gasto"
            monto.toIntOrNull() == null -> "Ingresa un monto válido"
            monto.toInt() < 100 -> "El monto debe ser mayor a 100$"
            else -> null
        }
    }

    // Estado con error
    val isValidMonto: Boolean
        get() = monto.isNotEmpty() && errorMonto == null

    // Añadir Gasto
    fun addGasto(concepto: String, monto: Int) {
        val gasto = GastosRegistry(formatearEntrada(concepto), monto)
        gastos.add(gasto)
    }

    fun resetConcepto() {
        concepto = ""
    }

    fun resetMonto() {
        monto = ""
    }

    // Formatear entrada de concepto para eliminar espacios a la izquierda, capitalizar y dejar el resto en lowercase
    fun formatearEntrada(input: String): String {
        return input.trim().lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}