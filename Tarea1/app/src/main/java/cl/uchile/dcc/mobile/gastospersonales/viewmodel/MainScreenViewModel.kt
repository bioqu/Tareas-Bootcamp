package cl.uchile.dcc.mobile.gastospersonales.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ScreenEnum


class MainScreenViewModel : ViewModel() {

    var actualScreen by mutableStateOf(ScreenEnum.FORMULARIO)
        private set

    fun changetoFormulario() {
        actualScreen = ScreenEnum.FORMULARIO
    }

    fun changetoGastos() {
        actualScreen = ScreenEnum.REGISTRY
    }





}
