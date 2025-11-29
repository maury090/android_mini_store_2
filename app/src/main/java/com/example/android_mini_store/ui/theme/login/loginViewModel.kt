package com.example.android_mini_store.ui.theme.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    // 🆕 MANTENIDO: Estados para los campos de texto
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Función para actualizar email
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Función para actualizar password
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}