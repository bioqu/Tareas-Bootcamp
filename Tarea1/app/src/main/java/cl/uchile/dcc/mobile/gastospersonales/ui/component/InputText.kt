package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// InputText :: String String onValueChange -> Unit icon errorMessage KeyboardOPtions -> OutlinedTextfield() { }
// Genera un cuadro de texto donde se puede escribir
// ejemplo: InputText( "Monto", viewModel.monto, onValueChange , isError = viewModel.errorMonto != null, icon = FeatherIcons.DollarSign,
// errorMessage, keyboardOptions) genera cuadro de texto para escribir con un icono al lado izquierdo
@Composable
fun InputText(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    icon: ImageVector? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        leadingIcon = icon?.let {
            {
                Icon(imageVector = it, contentDescription = null)
            }
        },
        supportingText = errorMessage?.let {
            {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}
