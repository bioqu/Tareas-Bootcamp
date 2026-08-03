package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.component.IconoButton
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.FormularioGastos
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.GastosMostrar
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ScreenEnum
import cl.uchile.dcc.mobile.gastospersonales.ui.theme.GastosPersonalesTheme
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.MainScreenViewModel
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Clipboard
import compose.icons.feathericons.Plus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = MainScreenViewModel()
        setContent {
            GastosPersonalesTheme {
                Scaffold(
                    topBar = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)) {
                        IconoButton("Volver", callBack = { /*TODO */ }, FeatherIcons.ArrowLeft)
                        if (viewModel.actualScreen == ScreenEnum.REGISTRY) {
                            Text(
                                text = viewModel.actualScreen.title,
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(8.dp, 8.dp)
                            )
                        }
                            }
                    },
                    bottomBar = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(
                                Modifier
                                    .clickable( onClick = { viewModel.changetoFormulario()} ),
                            ) {
                                IconoButton(
                                    ScreenEnum.FORMULARIO.title,
                                    callBack = { viewModel.changetoFormulario()},
                                    FeatherIcons.Plus,
                                    enabled = viewModel.actualScreen != ScreenEnum.FORMULARIO)
                                Text(
                                    text = "Agregar",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                            Column(
                                Modifier
                                    .clickable( onClick = { viewModel.changetoGastos()} ),
                            ) {
                                IconoButton(
                                    "Volver",
                                    callBack = { viewModel.changetoGastos()},
                                    FeatherIcons.Clipboard,
                                    enabled = viewModel.actualScreen != ScreenEnum.REGISTRY)
                                Text(
                                    text = "Historial",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
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



//@Preview(showBackground = true)
//@Composable
//fun FormularioGastosPreview() {
//    GastosPersonalesTheme {
//        FormularioGastos()
//    }
//}