package cl.uchile.dcc.mobile.gastospersonales.ui

import cl.uchile.dcc.mobile.gastospersonales.model.database.ExpenseDataRepository
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.uchile.dcc.mobile.gastospersonales.model.GastosRegistry
import cl.uchile.dcc.mobile.gastospersonales.model.repository.GastosAppRepository
import cl.uchile.dcc.mobile.gastospersonales.ui.screen.ExpenseRegistryApp
import cl.uchile.dcc.mobile.gastospersonales.viewmodel.RegistryViewModel
import com.example.compose.AppTheme
import com.example.compose.backgroundDark
import com.example.compose.backgroundLight

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: RegistryViewModel
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name= "ConfigApp")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val configPreferences: DataStore<Preferences> = this.datastore
        val configRepo = GastosAppRepository(
            applicationContext,
            configPreferences,
        )
        val configDatabase = ExpenseDataRepository(applicationContext)

//        configDatabase.addGastosRegistry(
//            GastosRegistry(
//                id = "1",
//                concepto = "18cho",
//                monto = 50000
//            )
//        )

        viewModel = RegistryViewModel(
            configRepo,
            configDatabase
        )

        setContent {
            // Estado del tema actual
            val theme by  viewModel.appTheme.collectAsState()
            // Calculamos si debe aplicar el tema oscuro
            val useDarkTheme = when (theme) {
                "Oscuro" -> true
                "Claro" -> false
                else -> isSystemInDarkTheme() // Caso "Auto"
            }
            // Decision del theme la pasamos a AppTheme
            AppTheme(darkTheme = useDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // Color de fondo automático provisto por el tema seleccionado
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseRegistryApp(viewModel)
                }
            }

        }
    }
}