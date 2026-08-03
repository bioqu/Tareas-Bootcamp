package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.component.Espaciador
import cl.uchile.dcc.mobile.gastospersonales.ui.component.Icono
import cl.uchile.dcc.mobile.gastospersonales.ui.component.InputText
import cl.uchile.dcc.mobile.gastospersonales.ui.component.SubmitButton
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.DollarSign
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel

@Composable
fun FormularioGastos(
    modifier: Modifier = Modifier.Companion,
    viewModel: RegistryViewModel = viewModel()
) {
    var concepto = viewModel.concepto
    var monto = viewModel.monto

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth())
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Nuevo Gasto",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Espaciador()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icono(
                "Concepto",
                FeatherIcons.Check)

            InputText(
                label = "Concepto",
                value = concepto,
                onValueChange = {
                    viewModel.concepto = it }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Icono(
                "Monto",
                FeatherIcons.DollarSign )

            InputText(
                label = "Monto",
                value = monto,
                onValueChange = {
                    viewModel.monto = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Espaciador()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            SubmitButton(
                "GUARDAR",
                enable = concepto.isNotEmpty(),
                callBack = {
                    viewModel.addGasto(concepto, monto.toIntOrNull() ?: 0)
                    viewModel.resetConcepto();
                    viewModel.resetMonto()})
        }

    }
}