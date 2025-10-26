// Validations.kt
package com.example.android_mini_store.ui.theme.singIn

// DATA CLASS PARA MANEJAR RESULTADOS DE VALIDACIÓN
data class ValidationResult(val isValid: Boolean, val message: String)

// FUNCIONES DE VALIDACIÓN
fun isValidNombre(nombre: String): ValidationResult {
    return when {
        nombre.isEmpty() -> ValidationResult(false, "El nombre es obligatorio")
        nombre.length < 2 -> ValidationResult(false, "El nombre debe tener al menos 2 caracteres")
        nombre.any { it.isDigit() } -> ValidationResult(false, "El nombre no puede contener números")
        else -> ValidationResult(true, "Nombre válido")
    }
}

fun isValidApellido(apellido: String): ValidationResult {
    return when {
        apellido.isEmpty() -> ValidationResult(false, "El apellido es obligatorio")
        apellido.length < 2 -> ValidationResult(false, "El apellido debe tener al menos 2 caracteres")
        apellido.any { it.isDigit() } -> ValidationResult(false, "El apellido no puede contener números")
        else -> ValidationResult(true, "Apellido válido")
    }
}

fun isValidEmail(email: String): ValidationResult {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return when {
        email.isEmpty() -> ValidationResult(false, "El email es obligatorio")
        !emailRegex.matches(email) -> ValidationResult(false, "Formato de email inválido")
        else -> ValidationResult(true, "Email válido")
    }
}

fun isValidDireccion(direccion: String): ValidationResult {
    return when {
        direccion.isEmpty() -> ValidationResult(false, "La dirección es obligatoria")
        direccion.length < 5 -> ValidationResult(false, "La dirección debe tener al menos 5 caracteres")
        else -> ValidationResult(true, "Dirección válida")
    }
}

// ✅ VALIDACIÓN DE RUT MEJORADA: Con validación de longitud (8-9 dígitos)
fun isValidRUT(rutIngresado: String): ValidationResult {
    if (rutIngresado.isEmpty()) return ValidationResult(false, "El RUT es obligatorio")

    // Convertir a mayúsculas y limpiar espacios
    val rutLimpio = rutIngresado.uppercase().replace(" ", "")

    // ✅ NUEVA VALIDACIÓN: Longitud total entre 8 y 9 caracteres
    if (rutLimpio.length < 8 || rutLimpio.length > 9) {
        return ValidationResult(false, "El RUT debe tener entre 8 y 9 dígitos (incluyendo el verificador)")
    }

    // Separar número y dígito verificador (último carácter)
    val numeroStr = rutLimpio.substring(0, rutLimpio.length - 1)
    val digitoVerificador = rutLimpio.last()

    // ✅ VERIFICACIÓN PARA PERSONAS: entre 7 y 8 dígitos en la parte numérica
    if (numeroStr.length < 7 || numeroStr.length > 8) {
        return ValidationResult(false, "RUT inválido. La parte numérica debe tener 7 u 8 dígitos")
    }

    // Verificar que el número sean solo dígitos
    if (!numeroStr.all { it.isDigit() }) {
        return ValidationResult(false, "La parte numérica del RUT debe contener solo números")
    }

    // SOLO PERMITIR K COMO LETRA VÁLIDA
    if (!digitoVerificador.isDigit() && digitoVerificador != 'K') {
        return ValidationResult(false, "Dígito verificador inválido. Solo se permite K como letra")
    }

    // Calcular dígito verificador esperado
    val digitoCalculado = calcularDigitoVerificador(numeroStr)

    // Comparar con el dígito ingresado
    return if (digitoCalculado == digitoVerificador) {
        ValidationResult(true, "RUT válido")
    } else {
        ValidationResult(false, "Dígito verificador incorrecto. Debería ser: $digitoCalculado")
    }
}

// ALGORITMO PARA CALCULAR DÍGITO VERIFICADOR
private fun calcularDigitoVerificador(rutSinDigito: String): Char {
    var suma = 0
    var multiplicador = 2

    for (i in rutSinDigito.length - 1 downTo 0) {
        suma += rutSinDigito[i].toString().toInt() * multiplicador
        multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
    }

    val resto = suma % 11
    val digito = 11 - resto

    return when (digito) {
        11 -> '0'
        10 -> 'K'
        else -> digito.toString().first()
    }
}