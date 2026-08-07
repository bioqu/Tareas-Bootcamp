package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cl.uchile.dcc.mobile.gastospersonales.ui.component.ScreenSpacer
import cl.uchile.dcc.mobile.gastospersonales.ui.component.InputText
import cl.uchile.dcc.mobile.gastospersonales.ui.component.SubmitButton
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.DollarSign
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel

// FormularioGastos()
// Crea la pantalla para ingreso de gastos
@Composable
fun FormularioGastos(
    modifier: Modifier = Modifier.Companion,
    viewModel: RegistryViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(1f)
            .imePadding()
            .verticalScroll(rememberScrollState())
    )
    {
            Text(
                text = "Nuevo Gasto",
                style = MaterialTheme.typography.titleLarge,
            )

        ScreenSpacer()

        InputText(
            label = "Concepto",
            value = viewModel.concepto,
            onValueChange = { viewModel.onChangeConcepto(it) },
            isError = viewModel.errorConcepto != null,
            icon = FeatherIcons.Check,
            errorMessage = viewModel.errorConcepto
        )

        InputText(
            label = "Monto",
            value = viewModel.monto,
            onValueChange = { viewModel.onChangeMonto(it) },
            isError = viewModel.errorMonto != null,
            icon = FeatherIcons.DollarSign,
            errorMessage = viewModel.errorMonto,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ScreenSpacer()

            SubmitButton(
                "GUARDAR",
                enable = viewModel.isValidConcepto && viewModel.isValidMonto,
                callBack = {
                    viewModel.addGasto(viewModel.concepto, viewModel.monto.toIntOrNull() ?: 0)
                    viewModel.resetConcepto();
                    viewModel.resetMonto()
                }
            )

    }
}
