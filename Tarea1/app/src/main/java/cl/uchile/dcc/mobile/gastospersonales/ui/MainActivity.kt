package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.FormularioGastos
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.GastosMostrar
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ScreenEnum
import cl.uchile.dcc.mobile.gastospersonales.ui.theme.GastosPersonalesTheme
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.MainScreenViewModel
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Clipboard
import compose.icons.feathericons.Plus

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Instancia de viewModel
        val screenViewModel = MainScreenViewModel()
        setContent {
            GastosPersonalesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(screenViewModel: MainScreenViewModel = viewModel()) {
    // La navegación se gestiona mediante el MainScreenViewModel usando el ScreenEnum
    val actualScreen = screenViewModel.actualScreen
    val density = LocalDensity.current

    // Se crea variable para registrar si el teclado esta presente en pantalla
    val isKeyboardOpen = WindowInsets.ime.getBottom(density) > 0

    // Se crea el estado de snackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (actualScreen == ScreenEnum.HISTORIAL) {
                //CentralTopAppBar TopBar con un titulo centrado y un iconbutton para  volver
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.secondary,
                    ),
                    title = {

                        Text(
                            text = actualScreen.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { screenViewModel.changeScreen(ScreenEnum.FORMULARIO)  }) {
                            Icon(
                                imageVector = FeatherIcons.ArrowLeft,
                                contentDescription = "Volver"
                            )
                        }
                    },
                )
            }
        },
        bottomBar = {
            // Condicional
            // Solo se muestra cuando el teclado NO está abierto
            if (!isKeyboardOpen) {
                NavigationBar(
                    windowInsets = WindowInsets.navigationBars
                ) {
                    // NavigationBarItem crea un tipo IconButton y texto que permite navegar entre pantalla
                    NavigationBarItem(
                        selected = actualScreen == ScreenEnum.FORMULARIO,
                        onClick = { screenViewModel.changeScreen(ScreenEnum.FORMULARIO) },
                        icon = {
                            Icon(
                                FeatherIcons.Plus,
                                contentDescription = ScreenEnum.FORMULARIO.title
                            )
                        },
                        label = { Text(ScreenEnum.FORMULARIO.title) }
                    )
                    NavigationBarItem(
                        selected = actualScreen == ScreenEnum.HISTORIAL,
                        onClick = { screenViewModel.changeScreen(ScreenEnum.HISTORIAL) },
                        icon = {
                            Icon(
                                FeatherIcons.Clipboard,
                                contentDescription = ScreenEnum.HISTORIAL.title
                            )
                        },
                        label = { Text(ScreenEnum.HISTORIAL.title) }
                    )
                }
            }
                    },
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) { innerPadding ->
        when (actualScreen) {
            ScreenEnum.FORMULARIO -> FormularioGastos(
                modifier = Modifier
                    .padding(innerPadding),
                // Se pasa estado de snackbarHostState
                snackbarHostState = snackbarHostState
            )

            ScreenEnum.HISTORIAL ->  GastosMostrar(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioGastosPreview() {
    GastosPersonalesTheme {
        TODO()
    }
}