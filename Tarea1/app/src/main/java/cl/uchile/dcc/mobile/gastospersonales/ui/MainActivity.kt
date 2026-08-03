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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import compose.icons.FeatherIcons
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.component.IconoButton
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.FormularioGastos
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.GastosMostrar
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
                        IconoButton("Volver", callBack = { /*TODO */}, FeatherIcons.ArrowLeft)
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
                                IconoButton("Volver", callBack = { viewModel.changetoFormulario()}, FeatherIcons.Plus)
                                Text(
                                    text = "Agregar",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                            Column(
                                Modifier
                                    .clickable( onClick = { viewModel.changetoGastos()} ),
                            ) {
                                IconoButton("Volver", callBack = { viewModel.changetoGastos()}, FeatherIcons.Clipboard)
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
                    if (viewModel.actualScreen == "FORMULARIO") {
                        FormularioGastos(
                            modifier = Modifier.padding(innerPadding)
                        )
            } else if (viewModel.actualScreen == "GASTOS"){
                        GastosMostrar(
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