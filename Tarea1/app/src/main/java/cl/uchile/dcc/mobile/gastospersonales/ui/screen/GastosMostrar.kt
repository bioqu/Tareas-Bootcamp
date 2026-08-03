package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.ui.component.GastosCard


@Composable
fun GastosMostrar(modifier: Modifier) {
    var gastos by remember { mutableStateOf( mutableListOf<GastosRegistry>()) }

    gastos.add(GastosRegistry("Perras", 1000000))
    gastos.add(GastosRegistry("Café", 5000))


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(36.dp),
        content = {
        items(gastos) { gasto ->
            GastosCard(gasto)
        }
    }
    )
}