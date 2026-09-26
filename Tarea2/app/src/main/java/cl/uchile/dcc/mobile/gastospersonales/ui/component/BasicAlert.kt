package cl.uchile.dcc.mobile.gastospersonales.ui.component

import android.os.Message
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicAlert(
    label: String,
    message: String,
    callBack: () -> Unit,
    icon: ImageVector,
) {
    // Se determina que la alerta es falsa hasta presionar IconButton
    val openDialog = remember { mutableStateOf(false) }

    IconButton(onClick = { openDialog.value = true }) {
        Icon(
            imageVector = icon,
            contentDescription = label
        )
    }
    // Al presionar IconButton
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = label) },
            text = {
                Text(
                   message
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        callBack() // Función enviada por parámetro (borrar el gasto en GastosCard)
                        openDialog.value = false // Cerrar la alerta
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false // Cierra la alerta sin realizar cambios
                    }
                ) {
                    Text("Cancelar")
                }
            },
        )
    }
}