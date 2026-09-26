package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import android.app.AlertDialog
import android.app.Dialog
import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.model.database.ExpenseDataRepository
import cl.uchile.dcc.mobile.gastospersonales.model.repository.GastosAppRepository
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseEventState
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseFormState
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// RegistryViewModel :: viewModel()
// Genera la lógica de concepto y monto
class RegistryViewModel(
    private val configRepo: GastosAppRepository,
    private val dataRepo: ExpenseDataRepository,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    // Llaves para almacenar los valores en el SavedStateHandle
    companion object {
        private const val KEY_CONCEPTO = "concepto_gasto"
        private const val KEY_MONTO = "monto_gasto"
    }

    // Al iniciar, leemos si ya existía un valor guardado, si no, usamos el valor vacío ""
    private val inicialConcepto = savedStateHandle.get<String>(KEY_CONCEPTO) ?: ""
    private val inicialMonto = savedStateHandle.get<String>(KEY_MONTO) ?: ""

    // Inicializamos el _state con los valores rescatados del SavedStateHandle
    private val _saveStateHandle = MutableStateFlow(
        ExpenseScreenState(
            form = ExpenseFormState(
                concepto = inicialConcepto,
                monto = inicialMonto
            )
        )
    )

    // Theme
    private val _appTheme = MutableStateFlow(configRepo.theme)
    val appTheme: StateFlow<String> = configRepo.theme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "Auto"
        )

    fun changeTheme(theme: String) {
        var theme = appTheme.value
        when(theme) {
            "Auto" -> theme = "Claro"
            "Claro" -> theme = "Oscuro"
            "Oscuro" -> theme = "Claro"
        }
        viewModelScope.launch {
            configRepo.setTheme(theme)
        }
    }

    val userName: StateFlow<String> = configRepo.theme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "Invitado"
        )

    fun setUserName(name: String) {
        viewModelScope.launch {
            configRepo.setName(name)
        }
    }

    private val _state = MutableStateFlow(ExpenseScreenState())
    val state: StateFlow<ExpenseScreenState> = _state.asStateFlow()

    fun onChangeConcepto(nuevoValor: String) {
        // Guardamos SavedStateHandle para que sobreviva a la muerte del proceso
        savedStateHandle[KEY_CONCEPTO] = nuevoValor

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
        // Guardamos en el SavedStateHandle
        savedStateHandle[KEY_MONTO] = nuevoValor

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

    //Añadir Gastos
    fun addGasto() {
        val form = _state.value.form
        val concepto = form.concepto.trim()
        val monto = form.monto.toIntOrNull()

        // Validación de monto y concepto en formulario
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

        // Se crea instancia de GastosRegistry
        val nuevoGasto = GastosRegistry(
            id = "0", // SQLite genera el número ID
            concepto = concepto,
            monto = monto!!
        )

        // Guardamos en la base de datos
        dataRepo.addGastosRegistry(nuevoGasto)

        // Actualizamos la lista cargada
        cargarGastos()

        // Se limpia l SavedStateHandle
        savedStateHandle[KEY_CONCEPTO] = ""
        savedStateHandle[KEY_MONTO] = ""

        // Se limpia formulario en el UI State
        _state.update {
            it.copy(
                form = ExpenseFormState()
            )
        }

//        _state.update {
//            it.copy(
//                form = ExpenseFormState(),
//                event = ExpenseEventState.Success(
//                    gastos = actuales + GastosRegistry(
//                        id = java.util.UUID.randomUUID().toString(), // Genera un ID único,
//                        concepto = concepto,
//                        monto = monto!!
//                    )
//                )
//            )
//        }
    }

    // Eliminar un gasto
    fun deleteGasto(gasto: GastosRegistry) {
        // Eliminar el registro en SQLite con deleteGastosRegistry
        dataRepo.deleteGastosRegistry(gasto)

        // Volver a cargar la lista en el _state para refrescar la pantalla automáticamente
        cargarGastos()
    }

    fun getGastosRegistry(): List<GastosRegistry> {
        return dataRepo.getAllGastosRepository()
    }

    // Cargar Gastos en lista desde BD
    fun cargarGastos() {
        val lista = dataRepo.getAllGastosRepository()
        _state.update {
            it.copy(
                event = if (lista.isEmpty()) {
                    ExpenseEventState.Empty
                } else {
                    ExpenseEventState.Success(lista)
                }
            )
        }
    }



    // Formatear numero en monto de manera que aparezca en formato ###.###.###
    fun splitDigits(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number).replace(",", ".") // Forzamos el punto chileno
    }
}