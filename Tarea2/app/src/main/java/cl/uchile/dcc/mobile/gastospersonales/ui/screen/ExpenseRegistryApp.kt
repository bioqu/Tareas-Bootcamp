package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.datastore.dataStore
import cl.uchile.dcc.mobile.gastospersonales.model.database.ExpenseDataRepository
import cl.uchile.dcc.mobile.gastospersonales.model.repository.GastosAppRepository
import cl.uchile.dcc.mobile.gastospersonales.ui.component.BottomNavBar
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseRegistryApp(screenViewModel: RegistryViewModel = viewModel()) {

    // Implementación de navController
    val navController = rememberNavController()

    // Se crea el estado de snackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // BackStack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // CurrentScreen
    val currentScreen = ScreenRoutes.entries.find { it.route == currentRoute } ?: ScreenRoutes.FORMULARIO

    // Se crea variable para registrar si el teclado esta presente en pantalla
    val isKeyboardOpen = WindowInsets.ime.getBottom(density) > 0

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (currentScreen == ScreenRoutes.HISTORIAL) {
                //CentralTopAppBar TopBar con un titulo centrado y un iconbutton para  volver
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.secondary,
                    ),
                    title = {

                        Text(
                            text = currentScreen.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
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
                BottomNavBar(
                    currentRoute = navController.currentBackStackEntry?.destination?.route,
                    onNavigateTo = {
                        navController.navigate(it)
                    }
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoutes.FORMULARIO.route,
        ) {
            composable(ScreenRoutes.FORMULARIO.route) {
                FormularioGastos(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = screenViewModel,
                    snackbarHostState = snackbarHostState
                )
            }
            composable(ScreenRoutes.HISTORIAL.route) {
                GastosMostrar(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = screenViewModel
                )
            }
        }
    }
}