package com.example.android_mini_store.config

import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit

object TextoConfig {
    var factorTamanio: Float = 1.0f
        private set

    fun actualizarTamanio(nuevoTamanio: Float) {
        factorTamanio = nuevoTamanio
    }

    // ✅ FUNCIÓN NORMAL - CORRECTA
    fun aplicarEscala(tamanioBase: Float): TextUnit {
        return (tamanioBase * factorTamanio).sp
    }

    // ✅ TAMAÑOS ESPECÍFICOS
    val boton: TextUnit get() = aplicarEscala(16f)
    val cardTitulo: TextUnit get() = aplicarEscala(18f)
    val cardCuerpo: TextUnit get() = aplicarEscala(14f)
    val menu: TextUnit get() = aplicarEscala(14f)
    val textoNormal: TextUnit get() = aplicarEscala(16f)
    val tituloPantalla: TextUnit get() = aplicarEscala(24f)
    val subtitulo: TextUnit get() = aplicarEscala(20f)
    val pequeno: TextUnit get() = aplicarEscala(12f)
}