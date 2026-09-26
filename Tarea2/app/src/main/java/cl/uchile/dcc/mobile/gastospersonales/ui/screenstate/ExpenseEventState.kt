package cl.uchile.dcc.mobile.gastospersonales.ui.screenstate

import cl.uchile.dcc.mobile.gastospersonales.model.database.GastosRegistry

sealed class ExpenseEventState {
    object Loading : ExpenseEventState()
    object Saving : ExpenseEventState()
    object Empty : ExpenseEventState()
    data class Success(val gastos: List<GastosRegistry>) : ExpenseEventState()
    data class Error(val message: String) : ExpenseEventState()
}