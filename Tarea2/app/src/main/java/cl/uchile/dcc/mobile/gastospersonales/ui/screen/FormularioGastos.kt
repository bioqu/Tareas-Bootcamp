package cl.uchile.dcc.mobile.gastospersonales.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cl.uchile.dcc.mobile.gastospersonales.ui.component.ScreenSpacer
import cl.uchile.dcc.mobile.gastospersonales.ui.component.InputText
import cl.uchile.dcc.mobile.gastospersonales.ui.component.SubmitButton
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import compose.icons.feathericons.DollarSign
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// FormularioGastos()
// Crea la pantalla para ingreso de gastos con dos OutlinedTextField uno para concepto y otro para
// monto con un button que completa la función de agregar
@Composable
fun FormularioGastos(
    modifier: Modifier = Modifier,
    viewModel: RegistryViewModel = viewModel(),
    snackbarHostState: SnackbarHostState
) {
    // Feedback usuario al ingresar gastos con snackbarHostState
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val userName by viewModel.userName.collectAsStateWithLifecycle()

    var showNameDialog by remember { mutableStateOf(false) }
    var tempNameInput by remember { mutableStateOf("") }

    // Si el usuario aún no ingresa su nombre (es default "Invitado" o vacío), mostramos el diálogo inicial
    val shouldShowInitialDialog = userName == "Invitado" || userName.isBlank()

    if (shouldShowInitialDialog || showNameDialog) {
        AlertDialog(
            onDismissRequest = {
                if (!shouldShowInitialDialog) {
                    showNameDialog = false
                }
            },
            title = {
                Text(text = if (shouldShowInitialDialog) "¡Bienvenido!" else "Editar Nombre")
            },
            text = {
                Column {
                    Text(text = "Por favor, ingresa tu nombre:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempNameInput,
                        onValueChange = { tempNameInput = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (tempNameInput.isNotBlank()) {
                            viewModel.setUserName(tempNameInput.trim())
                            showNameDialog = false
                            tempNameInput = ""
                        }
                    },
                    enabled = tempNameInput.isNotBlank()
                ) {
                    Text("Guardar")
                }
            }
        )
    }

    // Columna principal de la vista con verticalScrolling activado
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

        // InputText de concepto de gasto
        InputText(
            label = "Concepto",
            value = state.form.concepto,
            onValueChange = { viewModel.onChangeConcepto(it) },
            isError = state.form.errorConcepto != null,
            icon = FeatherIcons.Check,
            errorMessage = state.form.errorConcepto
        )

        // InputText de monto de gasto
        InputText(
            label = "Monto",
            value = state.form.monto,
            onValueChange = { viewModel.onChangeMonto(it) },
            isError = state.form.errorMonto != null,
            icon = FeatherIcons.DollarSign,
            errorMessage = state.form.errorMonto,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ScreenSpacer()
        // Elevated Button
        SubmitButton(
            "GUARDAR",
            enable = state.form.errorMonto == null && state.form.errorConcepto == null,
            callBack = {
                viewModel.addGasto()
                // Se instancia snackbarHostState al presionar el button
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Gasto añadido",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )

    }
}
