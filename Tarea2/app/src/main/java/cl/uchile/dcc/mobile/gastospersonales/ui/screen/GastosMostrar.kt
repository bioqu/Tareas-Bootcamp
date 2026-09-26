package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.ui.component.GastosCard
import cl.uchile.dcc.mobile.gastospersonales.ui.screenstate.ExpenseEventState
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel

// GastosMostrar()
// Crea la pantalla para revisar los gastos. Estos se crean un Card() y cada gasto va en una fila con el
// siguiente formato: [Concepto monto(numero en formato 1.000.000.$ button borrar]
@Composable
fun GastosMostrar(
    modifier: Modifier = Modifier,
    viewModel: RegistryViewModel = viewModel()
) {
    // Se ejecuta al entrar a la pantalla para cargar los datos de la BD
    LaunchedEffect(Unit) {
        viewModel.cargarGastos()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    // Lista de Gastos
    val listaDeGastos = when (val event = state.event) {
        is ExpenseEventState.Success -> event.gastos
        else -> emptyList()
    }

    // LazyColumn que albergara los gastos ingresados en la pantalla FormularioGastos.kt
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        content = {
            items(
                items = listaDeGastos,
                key = { it.id } // opcional pero recomendado para mejor performance
            ) { gasto ->
                // GastosCard genera un Card() con los gastos ordenados asi:
                // concepto de gasto a lado izquierdo, al lado derecho monto e IconButton
                GastosCard(gastos = gasto, viewModel = viewModel) // pasa el ítem individual, no toda la lista
            }
        }
    )
}