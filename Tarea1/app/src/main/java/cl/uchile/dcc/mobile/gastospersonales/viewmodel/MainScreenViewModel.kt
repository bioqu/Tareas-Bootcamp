package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class MainScreenViewModel : ViewModel() {

    var actualScreen by mutableStateOf("FORMULARIO")
        private set

    fun changetoFormulario() {
        actualScreen = "FORMULARIO"
    }

    fun changetoGastos() {
        actualScreen = "GASTOS"
    }





}
