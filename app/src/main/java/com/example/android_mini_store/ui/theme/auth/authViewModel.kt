package com.example.android_mini_store.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_mini_store.data.repository.usuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val usuarioRepository: usuarioRepository) : ViewModel() {

    // 👇 ESTADO DEL LOGIN
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // 👇 ESTADO DEL REGISTRO
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()

    // 👇 USUARIO ACTUAL
    private val _currentUser = MutableStateFlow<UsuarioState>(UsuarioState.NotLogged)
    val currentUser: StateFlow<UsuarioState> = _currentUser.asStateFlow()

    // 👇 FUNCIÓN DE LOGIN
    fun login(identificador: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val result = usuarioRepository.login(identificador, password)
                if (result.isSuccess) {
                    val usuario = result.getOrNull()
                    _loginState.value = LoginState.Success(usuario!!)
                    _currentUser.value = UsuarioState.Logged(usuario)
                } else {
                    _loginState.value = LoginState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    // 👇 FUNCIÓN DE REGISTRO - ✅ MODIFICADA CON LOGS
    fun registrarUsuario(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        direccion: String,
        password: String,
        rol: String = "cliente"
    ) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch {
            try {
                // ✅ LOG: INICIANDO REGISTRO
                println("🟡 [AUTH-VIEWMODEL] INICIANDO REGISTRO DE USUARIO")
                println("📋 [AUTH-VIEWMODEL] Datos recibidos:")
                println("   👤 RUT: $rut")
                println("   📛 Nombre: $nombre $apellido")
                println("   📧 Email: $correo")
                println("   🏠 Dirección: $direccion")
                println("   🔐 Rol: $rol")

                val result = usuarioRepository.registrarUsuario(
                    rut = rut,
                    nombre = nombre,
                    apellido = apellido,
                    correo = correo,
                    direccion = direccion,
                    password = password,
                    rol = rol
                )

                if (result.isSuccess) {
                    // ✅ LOG: REGISTRO EXITOSO
                    println("✅ [AUTH-VIEWMODEL] USUARIO REGISTRADO EXITOSAMENTE")
                    println("🎉 [AUTH-VIEWMODEL] Navegando a estado Success")
                    _registerState.value = RegisterState.Success
                } else {
                    // ✅ LOG: ERROR EN REGISTRO
                    val errorMsg = result.exceptionOrNull()?.message ?: "Error en el registro"
                    println("❌ [AUTH-VIEWMODEL] ERROR EN REGISTRO: $errorMsg")
                    println("💥 [AUTH-VIEWMODEL] Navegando a estado Error")
                    _registerState.value = RegisterState.Error(errorMsg)
                }
            } catch (e: Exception) {
                // ✅ LOG: EXCEPCIÓN
                println("🚨 [AUTH-VIEWMODEL] EXCEPCIÓN CAPTURADA: ${e.message}")
                _registerState.value = RegisterState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    // 👇 LIMPIAR ESTADOS
    fun clearLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun clearRegisterState() {
        _registerState.value = RegisterState.Idle
    }

    // 👇 CERRAR SESIÓN
    fun logout() {
        _currentUser.value = UsuarioState.NotLogged
        _loginState.value = LoginState.Idle
    }

    // 👇 ESTADOS DEL LOGIN
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val usuario: com.example.android_mini_store.data.dataBase.UsuarioEntity) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    // 👇 ESTADOS DEL REGISTRO
    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val message: String) : RegisterState()
    }

    // 👇 ESTADO DEL USUARIO
    sealed class UsuarioState {
        object NotLogged : UsuarioState()
        data class Logged(val usuario: com.example.android_mini_store.data.dataBase.UsuarioEntity) : UsuarioState()
    }
}