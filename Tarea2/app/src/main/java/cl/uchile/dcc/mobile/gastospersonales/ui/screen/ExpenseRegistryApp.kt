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
import cl.uchile.dcc.mobile.gastospersonales.ui.component.BottomNavBar
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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

    // Estados de Usuario y tema
    val currentTheme by screenViewModel.appTheme.collectAsStateWithLifecycle()
    val userName by screenViewModel.userName.collectAsStateWithLifecycle()

    var showEditNameDialog by remember { mutableStateOf(false) }
    var tempNameInput by remember { mutableStateOf("") }

    // Diálogo para editar el nombre de usuario
    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text(text = "Editar Nombre") },
            text = {
                Column {
                    Text(text = "Ingresa tu nuevo nombre:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempNameInput,
                        onValueChange = { tempNameInput = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (tempNameInput.isNotBlank()) {
                            screenViewModel.setUserName(tempNameInput.trim())
                            showEditNameDialog = false
                        }
                    },
                    enabled = tempNameInput.isNotBlank()
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // CurrentScreen
    val currentScreen = ScreenRoutes.entries.find { it.route == currentRoute } ?: ScreenRoutes.FORMULARIO

    // Se crea variable para registrar si el teclado esta presente en pantalla
    val isKeyboardOpen = WindowInsets.ime.getBottom(density) > 0

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Text(
                        text = if (currentScreen == ScreenRoutes.FORMULARIO) {
                            if (userName.isBlank() || userName == "Invitado") "Gastos Personales" else "Hola, $userName"
                        } else {
                            currentScreen.title
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (currentScreen == ScreenRoutes.HISTORIAL) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = FeatherIcons.ArrowLeft,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                actions = {
                    // Botón para editar el nombre de usuario
                    IconButton(onClick = {
                        tempNameInput = if (userName == "Invitado") "" else userName
                        showEditNameDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar nombre"
                        )
                    }
                    // Botón para cambiar tema
                    IconButton(onClick = { screenViewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (currentTheme == "Oscuro") Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Cambiar tema"
                        )
                    }
                }
            )
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
                    viewModel = screenViewModel,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}