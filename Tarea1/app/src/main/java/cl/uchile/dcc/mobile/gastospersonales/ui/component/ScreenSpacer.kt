package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenSpacer() {
    Spacer(
        modifier = Modifier
            .width(32.dp)
            .height(32.dp)
    )
}