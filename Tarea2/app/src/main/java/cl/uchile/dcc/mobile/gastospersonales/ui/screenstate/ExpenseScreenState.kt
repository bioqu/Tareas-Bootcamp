package cl.uchile.dcc.mobile.gastospersonales.ui.screenstate

data class ExpenseScreenState(
    val form: ExpenseFormState = ExpenseFormState(),
    val event: ExpenseEventState = ExpenseEventState.Empty
)
