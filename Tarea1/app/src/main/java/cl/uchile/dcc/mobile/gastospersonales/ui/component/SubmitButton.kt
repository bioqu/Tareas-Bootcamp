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

@Composable
fun SubmitButton(text: String, enable: Boolean, callBack: () -> Unit) {
    ElevatedButton(
        onClick = { callBack() },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            containerColor = Color.LightGray,),
        content =  {
            Text(
                text = text,
                modifier = Modifier
                    .padding(24.dp)

            )
        },
        enabled = text.isNotEmpty()
        )
}