package com.example.android_mini_store.data.repository

import com.example.android_mini_store.data.dataBase.UsuarioDao
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class usuarioRepository(private val usuarioDao: UsuarioDao) {

    // Métodos existentes
    suspend fun login(identificador: String, password: String): Result<UsuarioEntity> {
        return try {
            val usuario = usuarioDao.login(identificador, password)
            if (usuario != null) {
                Result.success(usuario)
            } else {
                Result.failure(Exception("Credenciales inválidas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrarUsuario(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        direccion: String,
        password: String,
        rol: String = "cliente"
    ): Result<Unit> {
        return try {
            // Verificar si RUT ya existe
            if (usuarioDao.rutExiste(rut) > 0) {
                return Result.failure(Exception("El RUT ya está registrado"))
            }

            // Verificar si correo ya existe
            if (usuarioDao.correoExiste(correo) > 0) {
                return Result.failure(Exception("El correo ya está registrado"))
            }

            val usuario = UsuarioEntity(
                rut = rut,
                nombre = nombre,
                apellido = apellido,
                correo = correo,
                direccion = direccion,
                password = password,
                rol = rol
            )

            usuarioDao.registrarUsuario(usuario)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsuarioByRut(rut: String): UsuarioEntity? {
        return usuarioDao.getUsuarioByRut(rut)
    }

    suspend fun getUsuarioByCorreo(correo: String): UsuarioEntity? {
        return usuarioDao.getUsuarioByCorreo(correo)
    }

    suspend fun actualizarUsuario(usuario: UsuarioEntity): Result<Int> {
        return try {
            val filasActualizadas = usuarioDao.actualizarUsuario(usuario)
            Result.success(filasActualizadas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllUsuarios(): Flow<List<UsuarioEntity>> {
        return usuarioDao.getAllUsuarios()
    }

    // 🆕 MÉTODO PARA INICIALIZAR ADMIN
    suspend fun inicializarUsuarioAdmin() {
        usuarioDao.crearUsuarioAdminInicial()
    }
}