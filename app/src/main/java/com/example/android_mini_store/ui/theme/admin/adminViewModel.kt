package com.example.android_mini_store.ui.theme.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import com.example.android_mini_store.data.repository.usuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminViewModel(
    private val usuarioRepository: usuarioRepository
) : ViewModel() {

    // Estado de la lista de usuarios
    private val _usuariosState = MutableStateFlow<List<UsuarioEntity>>(emptyList())
    val usuariosState: StateFlow<List<UsuarioEntity>> = _usuariosState.asStateFlow()

    // Estado de carga
    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    // Estado de error
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    // Cargar todos los usuarios
    fun cargarUsuarios() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                // 🆕 CORREGIDO: Colectar el Flow correctamente
                usuarioRepository.getAllUsuarios().collect { usuarios ->
                    _usuariosState.value = usuarios
                    _loadingState.value = false
                    println("✅ [ADMIN] ${usuarios.size} usuarios cargados")
                    usuarios.forEach { usuario ->
                        println("👤 [ADMIN] Usuario: ${usuario.nombre} ${usuario.apellido} - ${usuario.correo} - Rol: ${usuario.rol}")
                    }
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar usuarios: ${e.message}"
                println("❌ [ADMIN] Error cargando usuarios: ${e.message}")
            }
        }
    }

    // Limpiar errores
    fun clearError() {
        _errorState.value = null
    }
}