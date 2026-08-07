package cl.uchile.dcc.mobile.gastospersonales.ui.screen

// ScreenEnum :: String String  -> routing


enum class ScreenEnum (
    val title: String,
    val route: String,
) {
    FORMULARIO(
        title = "Formulario Gastos",
        route = "Formulario"
    ),

    HISTORIAL(
        title = "Historial de Gastos",
        route = "Registry"
    )


}