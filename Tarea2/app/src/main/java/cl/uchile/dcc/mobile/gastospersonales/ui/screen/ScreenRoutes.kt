package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import android.icu.text.CaseMap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

enum class ScreenRoutes(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    FORMULARIO(
        title = "Formulario Gastos",
        route = "Formulario",
        icon = Icons.Filled.Add
    ),

    HISTORIAL(
        title = "Historial de Gastos",
        route = "Registry",
        icon = Icons.Filled.Archive
    )
}