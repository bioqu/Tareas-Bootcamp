package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.model.database.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import kotlinx.coroutines.launch

// GastosCard :: gastos -> Card()
// Genera Card() con la lista de gastos con concepto y monto en filas
// GastosCard(gasto) Genera un Card() con concepto de gasto a lado izquierdo y al lado derecho monto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosCard(
    gastos: GastosRegistry,
    viewModel: RegistryViewModel,
    onDeleted: () -> Unit
) {
    // Feedback usuario al ingresar gastos con snackbarHostState
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = gastos.concepto,
                modifier = Modifier
                    .padding(16.dp),
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = viewModel.splitDigits(gastos.monto) + "$",
                modifier = Modifier
                    .padding(16.dp),
            )
            // IconButon de borrar gasto en función de alerta
            BasicAlert(
                label = "Eliminar gasto",
                icon = Icons.Default.Delete,
                callBack = {
                    viewModel.deleteGasto(gastos)
                    // Se instancia snackbarHostState al presionar el button
                    onDeleted()
                },
                message = "¿Quieres borrar este gasto"

            )
        }
    }
}