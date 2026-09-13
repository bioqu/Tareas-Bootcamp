package cl.uchile.dcc.mobile.gastospersonales.ui.screenstate

data class ExpenseFormState(
    // Datos Formulario de Gastos
    val concepto: String = "",
    val monto: String = "",
    val errorConcepto: String? = null,
    val errorMonto: String? = null
)
