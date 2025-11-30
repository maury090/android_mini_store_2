package com.example.android_mini_store.ui.theme.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_mini_store.data.repository.usuarioRepository

class AdminViewModelFactory(
    private val usuarioRepository: usuarioRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}