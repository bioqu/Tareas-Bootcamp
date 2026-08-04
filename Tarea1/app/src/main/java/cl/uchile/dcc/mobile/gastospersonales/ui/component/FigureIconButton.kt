package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// FigureIconButton :: String callBack icon enabled -> Icon() { }
// Genera un Button con forma de Icon, que se puede hacer click
//ejemplo: FigureIconButton("Volver", callBack = { }, FeatherIcons.ArrowLeft) Crea un button en forma de flecha a la izquierda
@Composable
fun FigureIconButton(
    label: String,
    callBack: () -> Unit,
    icon: ImageVector,
    enabled: Boolean = true,
    ) {
    IconButton(
        onClick = { callBack() },
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(top = 8.dp, end = 8.dp))
    }
}
