package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import android.icu.text.DecimalFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseEventState
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseFormState
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyList

// RegistryViewModel :: viewModel()
// Genera la lógica de concepto y monto
class RegistryViewModel : ViewModel() {

    private val _state = MutableStateFlow(ExpenseScreenState())
    val state: StateFlow<ExpenseScreenState> = _state.asStateFlow()

    fun onChangeConcepto(nuevoValor: String) {
        val error = when {
            nuevoValor.isBlank() -> "El concepto no puede estar vacío"
            nuevoValor.length < 3 -> "El concepto debe tener más de 3 caracteres"
            else -> null
        }

        _state.update { actual ->
            actual.copy(
                form = actual.form.copy(
                    concepto = nuevoValor,
                    errorConcepto = error
                )
            )
        }
    }

    fun onChangeMonto(nuevoValor: String) {
        val error = when {
            nuevoValor.isBlank() -> "El monto no puede estar vacío"
            nuevoValor.toIntOrNull() == null -> "El monto debe ser un número"
            nuevoValor.toInt() <= 0 -> "El monto debe ser mayor a 0"
            else -> null
        }

        _state.update { actual ->
            actual.copy(
                form = actual.form.copy(
                    monto = nuevoValor,
                    errorMonto = error
                )
            )
        }
    }

    fun addGasto() {
        val form = _state.value.form
        val concepto = form.concepto.trim()
        val monto = form.monto.toIntOrNull()

        val errorConcepto = when {
            concepto.isBlank() -> "El concepto no puede estar vacío"
            concepto.length < 3 -> "El concepto debe tener más de 3 caracteres"
            else -> null
        }
        val errorMonto = when {
            form.monto.isBlank() -> "El monto no puede estar vacío"
            monto == null -> "El monto debe ser un número entero"
            monto < 0 -> "El monto debe ser mayor a 0"
            else -> null
        }

        if (errorConcepto != null || errorMonto != null) {
            _state.update {
                it.copy(
                    form = it.form.copy(
                        errorConcepto = errorConcepto,
                        errorMonto = errorMonto
                    )
                )
            }
            return
        }

        val actuales = when (val event = _state.value.event) {
            is ExpenseEventState.Success -> event.gastos
            else -> emptyList()
        }

        _state.update {
            it.copy(
                form = ExpenseFormState(),
                event = ExpenseEventState.Success(
                    gastos = actuales + GastosRegistry(
                        concepto = concepto,
                        monto = monto!!
                    )
                )
            )
        }
    }

    // Formatear numero en monto de manera que aparezca en formato ###.###.###
    fun splitDigits(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number).replace(",", ".") // Forzamos el punto chileno
    }
}