package cl.uchile.dcc.mobile.gastospersonales.ui

import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.MoneyBill
import compose.icons.FeatherIcons
import compose.icons.feathericons.CreditCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R
import cl.uchile.dcc.mobile.gastospersonales.ui.theme.GastosPersonalesTheme
import compose.icons.feathericons.DollarSign
import compose.icons.feathericons.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GastosPersonalesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormularioGastos(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FormularioGastos(name: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth())
    {
        Row() {
            Text(
                text = "Nuevo Gasto",
                fontSize = 30.sp,
            )
        }
        Spacer(
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier

            ) {
                Icon(
                    imageVector = FeatherIcons.File,
                    contentDescription = null,
                    modifier = Modifier.padding(top = 8.dp, end = 8.dp)
                )
            }
            OutlinedTextField(
                value = "",
                onValueChange = { /* */ },
                label = { Text("Concepto")}
            )
        }
        Spacer(
            modifier = Modifier
                .width(16.dp)
                .height(16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Icon(
                imageVector = FeatherIcons.DollarSign,
                contentDescription = null,
                modifier = Modifier.padding(top = 8.dp, end = 8.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = { /* */ },
                label = { Text("Monto")}
            )
        }
        Spacer(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically

        )
        {
            ElevatedButton(
                onClick = { /* */ },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Blue,
                    containerColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)

            ) {
                Text(text = "GUARDAR",
                    fontSize = 24.sp)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun FormularioGastosPreview() {
    GastosPersonalesTheme {
        FormularioGastos("Android")
    }
}