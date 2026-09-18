package cl.uchile.dcc.mobile.gastospersonales.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.primaryContainerLight
import com.example.compose.primaryLight

// SubmitButton :: String enable callBack -> ElevatedButton() { }
// Genera un Button del tipo elevado con forma elevada
// ejemplo: SubmitButton( "GUARDAR", enable =  enable, callBack = {}) genera un button con forma elevada
@Composable
fun SubmitButton(
    text: String,
    enable: Boolean,
    callBack: () -> Unit) {
    ElevatedButton(
        onClick = { callBack() },
        modifier = Modifier
            .padding(8.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            // Enabled
            containerColor = primaryContainerLight,
            contentColor = primaryLight,

            // Disabled
            disabledContainerColor = primaryContainerLight.copy(alpha = 0.12f),
            disabledContentColor = primaryLight.copy(alpha = 0.38f)
        ),
        content =  {
            Text(
                text = text,
                modifier = Modifier
                    .padding(16.dp)

            )
        },
        enabled = enable
        )
}