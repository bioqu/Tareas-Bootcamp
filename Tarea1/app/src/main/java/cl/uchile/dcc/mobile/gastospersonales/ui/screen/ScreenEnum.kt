package cl.uchile.dcc.mobile.gastospersonales.ui.screen

sealed class Screen(val route: String, val title: String) {
    object Formulario : Screen("formulario", "Formulario Gastos")
    object Historial : Screen("historial", "Historial de Gastos")

    // Si mañana quieres ver el detalle de un gasto específico:
    // class DetalleGasto(val id: Int) : Screen("detalle/$id", "Detalle")
}
//enum class ScreenEnum (
//    val title: String,
//    val route: String,
//) {
//    FORMULARIO(
//        title = "Formulario Gastos",
//        route = "Formulario"
//    ),
//
//    REGISTRY(
//        title = "Historial de Gastos",
//        route = "Registry"
//    )
//
//
//}