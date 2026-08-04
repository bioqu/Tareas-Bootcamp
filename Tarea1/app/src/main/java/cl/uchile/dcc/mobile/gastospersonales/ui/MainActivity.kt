package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.component.FigureIconButton
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
        val viewModel = MainScreenViewModel()
        setContent {
            GastosPersonalesTheme {
                Scaffold(
                    topBar = {
                        //TopAppBar TopBar con un titulo centrado y un iconbutton para  volver
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
                            modifier = Modifier.padding(innerPadding)
                        )

                        ScreenEnum.REGISTRY ->  GastosMostrar(
                            modifier = Modifier.padding(innerPadding)
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