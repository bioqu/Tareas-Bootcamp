package cl.uchile.dcc.mobile.gastospersonales.model.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

object PreferenceKeys {
    val  THEME_MODE = stringPreferencesKey("TemaActual")
    val LANGUAGE = stringPreferencesKey("Idioma")
    val USERNAME = stringPreferencesKey("NombreUsuario")
}
class GastosAppRepository(
    private val context: Context,
    private val config: DataStore<Preferences>
) {
    // Theme Datastore
    val theme: Flow<String> = config.data
            .map { preferences ->
                preferences[PreferenceKeys.THEME_MODE]?: "Auto"
            }

    suspend fun setTheme(theme: String) {
        config.edit { preferences ->
            preferences[PreferenceKeys.THEME_MODE] = theme
        }
    }

    val name: Flow<String> = config.data
        .map { preferences ->
            preferences[PreferenceKeys.USERNAME]?: "Invitado"
        }

    suspend fun setName(name: String) {
        config.edit { preferences ->
            preferences[PreferenceKeys.USERNAME] = name
        }
    }
//    val theme: Flow<String> = dataStore.data .map { prefs ->
//        prefs[PreferencesKeys.THEME_MODE] ?: "Auto"
//    }
//
//    suspend fun setTheme(theme: String) {
//        dataStore.edit { prefs ->
//            prefs[PreferencesKeys.THEME_MODE] = theme
//        }
//    }
}