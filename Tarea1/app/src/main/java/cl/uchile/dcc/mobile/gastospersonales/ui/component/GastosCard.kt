package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.RectRulers
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import compose.icons.FeatherIcons
import compose.icons.feathericons.DollarSign

// GastosCard :: gastos -> Card()
// Genera Card() con la lista de gastos con concepto y monto en filas
// GastosCard(gasto) Genera un Card() con concepto de gasto a lado izquierdo y al lado derecho monto
@Composable
fun GastosCard(gastos: GastosRegistry, viewModel: RegistryViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()) {
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
            }
    }
}