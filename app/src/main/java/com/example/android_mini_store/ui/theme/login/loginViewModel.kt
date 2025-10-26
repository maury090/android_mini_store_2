package com.example.android_mini_store.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // Estados para los campos de texto
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Estado para el Snackbar
    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()

    // Función para actualizar email
    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    // Función para actualizar password
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    // Función para manejar el login
    fun onLoginClick() {
        // Verificar si los campos están vacíos
        if (_email.value.isBlank() || _password.value.isBlank()) {
            // Mostrar Snackbar solo cuando los campos estén vacíos
            _showSnackbar.value = true
        }
        // Si los campos tienen contenido, no hacer nada
    }

    // Función para ocultar el Snackbar
    fun hideSnackbar() {
        _showSnackbar.value = false
    }
}