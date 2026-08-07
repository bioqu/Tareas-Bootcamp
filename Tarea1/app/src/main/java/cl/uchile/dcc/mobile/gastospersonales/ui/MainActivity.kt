package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.FormularioGastos
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.GastosMostrar
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.Screen
import cl.uchile.dcc.mobile.gastospersonales.ui.theme.GastosPersonalesTheme
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.MainScreenViewModel
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Clipboard
import compose.icons.feathericons.Plus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Instancia de viewModel
        val viewModel = MainScreenViewModel()
        setContent {
            GastosPersonalesTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(screenViewModel: MainScreenViewModel = viewModel(), registryViewModel: RegistryViewModel = viewModel() ) {
//    val actualScreen by remember { mutableStateOf(screenViewModel.actualScreen) }
    val actualScreen = screenViewModel.actualScreen
    val navController = rememberNavController() // El "jefe" de la navegación
    // 1. Obtenemos la ruta actual del NavController para que la UI reaccione
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            //TopAppBar TopBar con un titulo centrado y un iconbutton para  volver
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    val title = if (currentRoute == Screen.Historial.route)
                        Screen.Historial.title else Screen.Formulario.title
                    Text(text = title)
                },
                 //                {
//                    if (actualScreen == ScreenEnum.REGISTRY) {
//                        Text(
//                            text = actualScreen.title,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                },
                // Icono de flecha izquierda para volver a la pantalla anterior, solo cuando estamos en el registro de gastos
                navigationIcon = {
                    if (currentRoute == Screen.Historial.route) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = FeatherIcons.ArrowLeft,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
            )
        },
        // Bottom con Iconos mostrando cada Pantalla: Formulario de gastos e Historial de Gastos
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                NavigationBarItem(
                    selected = currentRoute == Screen.Formulario.route,
                    onClick = {
                        // Navegamos y limpiamos el historial para no acumular pantallas
                        navController.navigate(Screen.Formulario.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        screenViewModel.changeToFormulario()
                    },
                    icon = { Icon(FeatherIcons.Plus, contentDescription = "Formulario") },
                    label = { Text("Agregar") }
                )
                NavigationBarItem(
                    selected = currentRoute == Screen.Historial.route,
                    onClick = {
                        navController.navigate(Screen.Historial.route) {
                            launchSingleTop = true }
                        screenViewModel.changeToGastos()
                    },
                    icon = { Icon(FeatherIcons.Clipboard, contentDescription = "Historial") },
                    label = { Text("Historial") }
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Formulario.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Formulario.route) {
                FormularioGastos(viewModel = registryViewModel)
            }
            composable(Screen.Historial.route) {
                GastosMostrar(viewModel = registryViewModel)
            }
        }
//        when (actualScreen) {
//            ScreenEnum.FORMULARIO -> FormularioGastos(
//                modifier = Modifier.padding(innerPadding)
//            )
//
//            ScreenEnum.REGISTRY ->  GastosMostrar(
//                modifier = Modifier.padding(innerPadding)
//            )
//        }
    }
}


@Preview(showBackground = true)
@Composable
fun FormularioGastosPreview() {
    GastosPersonalesTheme {
        FormularioGastos()
    }
}