package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.Screen

class MainScreenViewModel : ViewModel() {

    var actualScreen by mutableStateOf<Screen>(Screen.Formulario)
        private set

    fun changeToFormulario() {
        actualScreen = Screen.Formulario // Usar el objeto de la sealed class
    }

    fun changeToGastos() {
        actualScreen = Screen.Historial
    }
}
