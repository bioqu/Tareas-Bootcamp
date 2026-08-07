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
    modifier: Modifier = Modifier.Companion,
    viewModel: RegistryViewModel = viewModel()
) {
    // LazyColumn que albergara los gastos ingresados en la pantalla FormularioGastos.kt
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(8.dp),
        content = {
            items(
                items = viewModel.gastos,
                key = { it.hashCode() } // opcional pero recomendado para mejor performance
            ) { gasto ->
                // GastosCard genera un Card() con los gastos ordenados asi:
                // concepto de gasto a lado izquierdo y al lado derecho monto
                GastosCard(gasto) // pasa el ítem individual, no toda la lista
            }
    }
    )
}