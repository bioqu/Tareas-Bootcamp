package cl.uchile.dcc.mobile.gastospersonales.ui.screen

enum class ScreenEnum (
    val title: String,
    val route: String,
) {
    FORMULARIO(
        title = "Formulario Gastos",
        route = "Formulario"
    ),

    REGISTRY(
        title = "Historial de Gastos",
        route = "Registry"
    )


}