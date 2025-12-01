package com.example.android_mini_store.ui.theme.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import com.example.android_mini_store.data.repository.usuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    // Cargar SOLO usuarios clientes
    fun cargarUsuariosClientes() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    // ✅ FILTRAR: Solo usuarios con rol "cliente"
                    val usuariosClientes = todosUsuarios.filter { usuario ->
                        usuario.rol.equals("cliente", ignoreCase = true)
                    }

                    _usuariosState.value = usuariosClientes
                    _loadingState.value = false
                    println("✅ [ADMIN] ${usuariosClientes.size} usuarios clientes cargados")
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar clientes: ${e.message}"
                println("❌ [ADMIN] Error cargando clientes: ${e.message}")
            }
        }
    }

    // 🆕 NUEVA FUNCIÓN: Cargar TODOS los usuarios (Para el filtro "Todos")
    fun cargarUsuarios() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    // No se aplica filtro, carga todos
                    _usuariosState.value = todosUsuarios
                    _loadingState.value = false
                    println("✅ [ADMIN] ${todosUsuarios.size} usuarios (todos) cargados")
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar todos los usuarios: ${e.message}"
                println("❌ [ADMIN] Error cargando todos los usuarios: ${e.message}")
            }
        }
    }
    // ... (rest of the functions: clearError, actualizarRolUsuario, desactivarUsuario, buscarUsuariosClientes, getUsuarioClienteByRut)
    // ... (Mantener las funciones que ya tenías aquí, excepto si estaban fuera del cierre de la clase)

    // Limpiar errores
    fun clearError() {
        _errorState.value = null
    }

    // ACTUALIZAR ROL DE USUARIO (SOLO PARA CLIENTES)
    fun actualizarRolUsuario(rut: String, nuevoRol: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val usuarioActual = _usuariosState.value.find { it.rut == rut }

                if (usuarioActual != null) {
                    val usuarioActualizado = usuarioActual.copy(rol = nuevoRol)
                    val resultado = usuarioRepository.actualizarUsuario(usuarioActualizado)

                    if (resultado.isSuccess) {
                        println("✅ [ADMIN] Rol actualizado: ${usuarioActual.nombre} -> $nuevoRol")
                        cargarUsuariosClientes() // Recargar la lista
                    } else {
                        _errorState.value = "Error al actualizar rol: ${resultado.exceptionOrNull()?.message}"
                    }
                } else {
                    _errorState.value = "Usuario con RUT $rut no encontrado"
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al actualizar rol: ${e.message}"
            }
        }
    }

    // DESACTIVAR USUARIO (SOLO CLIENTES)
    fun desactivarUsuario(rut: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true
                val usuarioActual = _usuariosState.value.find { it.rut == rut }

                if (usuarioActual != null) {
                    val usuarioDesactivado = usuarioActual.copy(rol = "inactivo")
                    val resultado = usuarioRepository.actualizarUsuario(usuarioDesactivado)

                    if (resultado.isSuccess) {
                        println("✅ [ADMIN] Usuario desactivado: ${usuarioActual.nombre}")
                        cargarUsuariosClientes() // Recargar la lista
                    } else {
                        _errorState.value = "Error al desactivar usuario: ${resultado.exceptionOrNull()?.message}"
                    }
                } else {
                    _errorState.value = "Usuario con RUT $rut no encontrado"
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al desactivar usuario: ${e.message}"
            }
        }
    }

    // BUSCAR USUARIOS CLIENTES
    fun buscarUsuariosClientes(query: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = true

                if (query.isBlank()) {
                    cargarUsuariosClientes()
                } else {
                    val resultados = _usuariosState.value.filter { usuario ->
                        usuario.nombre.contains(query, ignoreCase = true) ||
                                usuario.apellido.contains(query, ignoreCase = true) ||
                                usuario.correo.contains(query, ignoreCase = true) ||
                                (usuario.rut?.contains(query, ignoreCase = true) == true)
                    }
                    _usuariosState.value = resultados
                    _loadingState.value = false
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error en búsqueda: ${e.message}"
            }
        }
    }

    // OBTENER USUARIO CLIENTE POR RUT
    fun getUsuarioClienteByRut(rut: String): UsuarioEntity? {
        return _usuariosState.value.find { it.rut == rut }
    }
} // ✅ ¡SOLO UN CORCHETE DE CIERRE AQUÍ!