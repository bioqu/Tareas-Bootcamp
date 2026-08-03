package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun IconoButton(
    label: String,
    callBack: () -> Unit,
    icon: ImageVector,
    enabled: Boolean = true) {
    IconButton(
        onClick = { callBack() },
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp, end = 8.dp))
    }
}
