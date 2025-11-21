package com.example.android_mini_store.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property para DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val TEXTO_TAMANIO_KEY = floatPreferencesKey("texto_tamanio")
    }

    // Obtener el tamaño de texto guardado
    val textoTamanio: Flow<Float> = dataStore.data
        .map { preferences ->
            preferences[TEXTO_TAMANIO_KEY] ?: 1.0f // Valor por defecto: estándar
        }

    // Guardar el tamaño de texto
    suspend fun guardarTextoTamanio(tamanio: Float) {
        dataStore.edit { preferences ->
            preferences[TEXTO_TAMANIO_KEY] = tamanio
        }
    }
}