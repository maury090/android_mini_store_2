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

    // 2. NUEVO ESTADO DE DETALLE (Usado en UsuarioInfoScreen)
    // Contiene un solo usuario o null si no se ha cargado
    private val _usuarioDetalleState = MutableStateFlow<UsuarioEntity?>(null)
    val usuarioDetalleState: StateFlow<UsuarioEntity?> = _usuarioDetalleState.asStateFlow()

    // 3. ESTADO DE ACTUALIZACIÓN EXITOSA (Para mostrar Toast)
    private val _updateSuccessState = MutableStateFlow<Boolean>(false)
    val updateSuccessState: StateFlow<Boolean> = _updateSuccessState.asStateFlow()

    // ---------------------------------------------------------------------
    // FUNCIONES EXISTENTES (Carga de listas)
    // ---------------------------------------------------------------------

    // Cargar SOLO usuarios clientes
    fun cargarUsuariosClientes() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    // Filtrar: Solo usuarios con rol "cliente"
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

    // Cargar TODOS los usuarios
    fun cargarUsuarios() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                usuarioRepository.getAllUsuarios().collect { todosUsuarios ->
                    // No se aplica filtro, carga todos
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
    // FUNCIONES NUEVAS (Detalle de UsuarioInfoScreen)
    // ---------------------------------------------------------------------

    /**
     * 🆕 FUNCIÓN 1: Carga el detalle de un usuario específico por su RUT.
     */
    fun fetchUsuarioDetalle(rut: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            try {
                // Llama al repositorio para obtener el usuario
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

    /**
     * 🆕 FUNCIÓN 2: Actualiza solo el campo de dirección del usuario.
     * Ahora también establece el estado de éxito para mostrar el Toast
     */
    fun actualizarDireccionUsuario(rut: String, nuevaDireccion: String) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null
            _updateSuccessState.value = false // Resetear estado de éxito
            try {
                val usuarioActual = usuarioRepository.getUsuarioByRut(rut)

                if (usuarioActual != null) {
                    // Crea una copia del usuario SÓLO con la dirección modificada
                    val usuarioActualizado = usuarioActual.copy(direccion = nuevaDireccion)

                    val resultado = usuarioRepository.actualizarUsuario(usuarioActualizado)

                    if (resultado.isSuccess) {
                        _usuarioDetalleState.value = usuarioActualizado // Actualizar el estado para refrescar la UI
                        _loadingState.value = false
                        _updateSuccessState.value = true // Establecer éxito para mostrar Toast
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

    /**
     * 🆕 FUNCIÓN 3: Reinicia el estado de éxito después de mostrar el Toast
     * Esto se llama desde la UI después de mostrar el Toast
     */
    fun resetUpdateSuccess() {
        _updateSuccessState.value = false
    }

    // ---------------------------------------------------------------------
    // OTRAS FUNCIONES MANTENIDAS (Para referencia)
    // ---------------------------------------------------------------------

    fun clearError() {
        _errorState.value = null
    }

    fun actualizarRolUsuario(rut: String, nuevoRol: String) {
        // ... (Tu lógica de actualización de rol)
    }

    fun desactivarUsuario(rut: String) {
        // ... (Tu lógica de desactivación)
    }

    fun buscarUsuariosClientes(query: String) {
        // ... (Tu lógica de búsqueda)
    }

    fun getUsuarioClienteByRut(rut: String): UsuarioEntity? {
        return _usuariosState.value.find { it.rut == rut }
    }
}