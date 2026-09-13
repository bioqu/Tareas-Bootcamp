package cl.uchile.dcc.mobile.gastospersonales.ui.screenstate

import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry

data class ExpenseUIState(
    val registry: ExpenseFormState,
    val gastos: ExpenseScreenState
)
