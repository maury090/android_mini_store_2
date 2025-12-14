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

    // 1. ESTADOS DE LISTA (Usados en RevisionUsuariosScreen)
    private val _usuariosState = MutableStateFlow<List<UsuarioEntity>>(emptyList())
    val usuariosState: StateFlow<List<UsuarioEntity>> = _usuariosState.asStateFlow()

    private val _loadingState = MutableStateFlow<Boolean>(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    // 2. ESTADO DE DETALLE (Usado en UsuarioInfoScreen)
    private val _usuarioDetalleState = MutableStateFlow<UsuarioEntity?>(null)
    val usuarioDetalleState: StateFlow<UsuarioEntity?> = _usuarioDetalleState.asStateFlow()

    // 3. ESTADO DE ACTUALIZACIÓN EXITOSA (Para mostrar Toast)
    private val _updateSuccessState = MutableStateFlow<Boolean>(false)
    val updateSuccessState: StateFlow<Boolean> = _updateSuccessState.asStateFlow()

    // 4. ESTADO PARA ELIMINACIÓN EXITOSA
    private val _eliminacionExitosa = MutableStateFlow<Boolean>(false)
    val eliminacionExitosa: StateFlow<Boolean> = _eliminacionExitosa.asStateFlow()

    // ---------------------------------------------------------------------
    // FUNCIONES DE LISTAS (MANTENIDAS)
    // ---------------------------------------------------------------------

    fun cargarUsuariosClientes() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    val usuariosClientes = todosUsuarios.filter { usuario ->
                        usuario.rol.equals("cliente", ignoreCase = true)
                    }
                    _usuariosState.value = usuariosClientes
                    _loadingState.value = false
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar clientes: ${e.message}"
            }
        }
    }

    fun cargarUsuarios() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    _usuariosState.value = todosUsuarios
                    _loadingState.value = false
                }
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar todos los usuarios: ${e.message}"
            }
        }
    }

    // ---------------------------------------------------------------------
    // FUNCIONES DE DETALLE (MANTENIDAS)
    // ---------------------------------------------------------------------

    fun fetchUsuarioDetalle(rut: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                val usuario = usuarioRepository.getUsuarioByRut(rut)
                _usuarioDetalleState.value = usuario
                _loadingState.value = false
                println("✅ [ADMIN-VM] Usuario $rut cargado para detalle.")
            } catch (e: Exception) {
                _loadingState.value = false
                _errorState.value = "Error al cargar detalles del usuario: ${e.message}"
            }
        }
    }

    fun actualizarDireccionUsuario(rut: String, nuevaDireccion: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            _updateSuccessState.value = false
            try {
                val usuarioActual = usuarioRepository.getUsuarioByRut(rut)

                if (usuarioActual != null) {
                    val usuarioActualizado = usuarioActual.copy(direccion = nuevaDireccion)
                    val resultado = usuarioRepository.actualizarUsuario(usuarioActualizado)

                    if (resultado.isSuccess) {
                        _usuarioDetalleState.value = usuarioActualizado
                        _loadingState.value = false
                        _updateSuccessState.value = true
                        println("✅ [ADMIN-VM] Dirección de $rut actualizada a: $nuevaDireccion")
                    } else {
                        _errorState.value = "Error al actualizar la dirección: ${resultado.exceptionOrNull()?.message}"
                        _loadingState.value = false
                    }
                } else {
                    _errorState.value = "Usuario con RUT $rut no encontrado para actualizar."
                    _loadingState.value = false
                }
            } catch (e: Exception) {
                _errorState.value = "Error de conexión al actualizar: ${e.message}"
                _loadingState.value = false
            }
        }
    }

    fun resetUpdateSuccess() {
        _updateSuccessState.value = false
    }

    // ---------------------------------------------------------------------
    // 🗑️ FUNCIONES DE ELIMINACIÓN (ELIMINACIÓN FÍSICA)
    // ---------------------------------------------------------------------

    /**
     * ELIMINAR USUARIO PERMANENTEMENTE
     * Borra completamente el usuario de la base de datos
     *
     * @param rut RUT del usuario a eliminar
     */
    fun eliminarUsuario(rut: String) {
        // Validación: No permitir eliminar el usuario admin principal
        if (rut == "88888888-8") {
            _errorState.value = "No se puede eliminar el usuario administrador principal"
            return
        }

        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            _eliminacionExitosa.value = false

            try {
                // 🗑️ Llamar al repositorio para ELIMINAR (no desactivar)
                val resultado = usuarioRepository.eliminarUsuario(rut)

                resultado.fold(
                    onSuccess = { filasAfectadas ->
                        if (filasAfectadas > 0) {
                            // Éxito: Usuario ELIMINADO permanentemente
                            _eliminacionExitosa.value = true
                            _loadingState.value = false

                            // Limpiar el detalle del usuario actual si es el mismo
                            if (_usuarioDetalleState.value?.rut == rut) {
                                _usuarioDetalleState.value = null
                            }

                            println("🗑️ [ADMIN-VM] Usuario $rut ELIMINADO permanentemente")
                        } else {
                            _errorState.value = "Usuario no encontrado"
                            _loadingState.value = false
                        }
                    },
                    onFailure = { error ->
                        _errorState.value = "Error al eliminar usuario: ${error.message}"
                        _loadingState.value = false
                    }
                )
            } catch (e: Exception) {
                _errorState.value = "Error inesperado: ${e.message}"
                _loadingState.value = false
            }
        }
    }


    fun resetEliminacionExitosa() {
        _eliminacionExitosa.value = false
    }

    // ---------------------------------------------------------------------
    // OTRAS FUNCIONES (MANTENIDAS)
    // ---------------------------------------------------------------------

    fun clearError() {
        _errorState.value = null
    }

    fun actualizarRolUsuario(rut: String, nuevoRol: String) {
        // ... (Tu lógica de actualización de rol)
    }

    // 🗑️ NOTA: El método desactivarUsuario() original se renombra o elimina
    // Mantenemos solo eliminarUsuario() para eliminación física

    fun buscarUsuariosClientes(query: String) {
        // ... (Tu lógica de búsqueda)
    }

    fun getUsuarioClienteByRut(rut: String): UsuarioEntity? {
        return _usuariosState.value.find { it.rut == rut }
    }
}