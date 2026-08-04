package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.FormularioGastos
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.GastosMostrar
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ScreenEnum
import cl.uchile.dcc.mobile.gastospersonales.ui.theme.GastosPersonalesTheme
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.MainScreenViewModel
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Clipboard
import compose.icons.feathericons.Plus

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Instancia de viewModel y RegistryViewModel
        val viewModel = MainScreenViewModel()
        val registryViewModel = RegistryViewModel() // Este es el compartido

        setContent {
            GastosPersonalesTheme {
                Scaffold(
                    topBar = {
                        //CenterAlignedTopAppBar TopBar con un titulo centrado y un iconbutton para  volver
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.secondary,
                            ),
                            title = {
                                if (viewModel.actualScreen == ScreenEnum.REGISTRY) {
                                    Text(
                                        text = viewModel.actualScreen.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = FeatherIcons.ArrowLeft,
                                        contentDescription = "Volver"
                                    )
                                }
                            },
                        )
                    },
                    bottomBar = {
                            NavigationBar(
                                windowInsets = NavigationBarDefaults.windowInsets
                            ) {
                                NavigationBarItem(
                                    selected = viewModel.actualScreen == ScreenEnum.FORMULARIO,
                                    onClick = { viewModel.changetoFormulario() },
                                    icon = { Icon(FeatherIcons.Plus, contentDescription = ScreenEnum.FORMULARIO.title) },
                                    label = { Text(ScreenEnum.FORMULARIO.title) }
                                )
                                NavigationBarItem(
                                    selected = viewModel.actualScreen == ScreenEnum.REGISTRY,
                                    onClick = { viewModel.changetoGastos() },
                                    icon = { Icon(FeatherIcons.Clipboard, contentDescription = ScreenEnum.REGISTRY.title) },
                                    label = { Text(ScreenEnum.REGISTRY.title) }
                                )
                            }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    when (viewModel.actualScreen) {
                        ScreenEnum.FORMULARIO -> FormularioGastos(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = registryViewModel
                        )

                        ScreenEnum.REGISTRY ->  GastosMostrar(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = registryViewModel
                        )
                    }
        }
    }
}
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioGastosPreview() {
    GastosPersonalesTheme {
        FormularioGastos()
    }
}