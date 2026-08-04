package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.ui.component.GastosCard
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel



@Composable
fun GastosMostrar(
    modifier: Modifier,
    viewModel: RegistryViewModel = viewModel()
) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp, top = 54.dp, end = 24.dp),
        content = {
            items(
                items = viewModel.gastos,
                key = { it.hashCode() } // opcional pero recomendado para mejor performance
            ) { gasto ->
                GastosCard(gasto) // pasa el ítem individual, no toda la lista
            }
    }
    )
}