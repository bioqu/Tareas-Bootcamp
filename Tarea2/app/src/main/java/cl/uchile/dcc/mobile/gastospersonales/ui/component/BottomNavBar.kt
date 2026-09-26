package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ScreenRoutes
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight
import com.example.compose.secondaryLight
import com.example.compose.tertiaryContainerLight
import com.example.compose.tertiaryDark
import com.example.compose.tertiaryLight


@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets.navigationBars,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 8.dp
    ) {
        val sections = listOf(
            ScreenRoutes.FORMULARIO,
            ScreenRoutes.HISTORIAL,
        )

        sections.forEach { section ->
            NavigationBarItem(
                selected = currentRoute == section.route,
                onClick = { onNavigateTo(section.route) },
                icon = { Icon(section.icon, contentDescription = section.title, tint = MaterialTheme.colorScheme.secondary) },
                label = { Text(text = section.title, color = MaterialTheme.colorScheme.secondary) },
            )
        }
    }
}