package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.uchile.dcc.mobile.gastospersonales.model.database.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.model.repository.ExpenseDataRepository
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
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId

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
        viewModelScope.launch {
            configRepo.setTheme(theme)
        }
    }

    fun toggleTheme() {
        val current = appTheme.value
        val newTheme = if (current == "Oscuro") "Claro" else "Oscuro"
        viewModelScope.launch {
            configRepo.setTheme(newTheme)
        }
    }

    val userName: StateFlow<String> = configRepo.name
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
    @RequiresApi(Build.VERSION_CODES.O)
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
            monto <= 0 -> "El monto debe ser mayor a 0"
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

        viewModelScope.launch {
            if (!puedeAgregar(monto!!)) {
                _state.update {
                    it.copy(
                        form = it.form.copy(
                            errorMonto = "Superaste el gasto máximo del día"
                        )
                    )
                }
                return@launch
            }

            val nuevoGasto = GastosRegistry(
                id = System.currentTimeMillis().toString(),
                concepto = concepto,
                monto = monto
            )

            dataRepo.addGastosRegistry(nuevoGasto)
            cargarGastos()

            savedStateHandle[KEY_CONCEPTO] = ""
            savedStateHandle[KEY_MONTO] = ""
            _state.update { it.copy(form = ExpenseFormState()) }
        }
    }

    // Eliminar un gasto
    fun deleteGasto(gasto: GastosRegistry) {
        viewModelScope.launch {
            // Eliminar el registro en Room a través del repositorio
            dataRepo.deleteGastosRegistry(gasto)

            // Volver a cargar la lista en el _state para refrescar la pantalla
            cargarGastos()
        }
    }

    // Cargar Gastos en lista desde BD
    fun cargarGastos() {
        viewModelScope.launch {
            // getAllGastosRepository() también es suspend
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
    }

    // Formatear numero en monto de manera que aparezca en formato ###.###.###
    fun splitDigits(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number).replace(",", ".") // Forzamos el punto chileno
    }

    // Total Diario
    @RequiresApi(Build.VERSION_CODES.O)
    fun inicioDeHoyMillis(): Long {
        return LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val totalHoy: StateFlow<Int> = dataRepo
        .totalDesde(inicioDeHoyMillis())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val maxDiario: StateFlow<Int> = configRepo.maxDiario
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )

    fun setMaxDiario(valor: Int) {
        viewModelScope.launch {
            configRepo.setMaxDiario(valor)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun puedeAgregar(monto: Int): Boolean {
        val tope = maxDiario.value
        if (tope <= 0) return true
        return totalHoy.value + monto <= tope
    }
}