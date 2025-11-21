package com.example.android_mini_store.config

object TextoConfig {
    var factorTamanio: Float = 1.0f
        private set

    fun actualizarTamanio(nuevoTamanio: Float) {
        factorTamanio = nuevoTamanio
    }

    // Función de extensión para aplicar el tamaño
    fun androidx.compose.ui.unit.TextUnit.aplicarEscala(): androidx.compose.ui.unit.TextUnit {
        return this * factorTamanio
    }
}

