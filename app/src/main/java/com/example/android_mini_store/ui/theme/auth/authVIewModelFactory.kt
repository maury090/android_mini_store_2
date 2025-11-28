package com.example.android_mini_store.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_mini_store.data.repository.usuarioRepository

class AuthViewModelFactory(
    private val usuarioRepository: usuarioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}