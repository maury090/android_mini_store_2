package com.example.android_mini_store.data.repository

import com.example.android_mini_store.data.dataBase.UsuarioDao
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class usuarioRepository(private val usuarioDao: UsuarioDao) {

    // 👇 LOGIN - acepta RUT o correo como identificador
    suspend fun login(identificador: String, password: String): Result<UsuarioEntity> {
        return try {
            val usuario = usuarioDao.login(identificador, password)
            if (usuario != null) {
                Result.success(usuario)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error en el login: ${e.message}"))
        }
    }

    // 👇 REGISTRO DE USUARIO - ✅ MODIFICADA CON LOGS
    suspend fun registrarUsuario(usuario: UsuarioEntity): Result<Boolean> {
        return try {
            // ✅ LOG: INICIANDO PROCESO DE REGISTRO
            println("🟡 [REPOSITORY] INICIANDO REGISTRO EN BASE DE DATOS")
            println("📋 [REPOSITORY] Datos del usuario:")
            println("   🔑 RUT: ${usuario.rut}")
            println("   👤 Nombre: ${usuario.nombre} ${usuario.apellido}")
            println("   📧 Email: ${usuario.correo}")
            println("   🏠 Dirección: ${usuario.direccion}")
            println("   🎯 Rol: ${usuario.rol}")

            // Verificar si RUT ya existe
            println("🔍 [REPOSITORY] Verificando si RUT existe: ${usuario.rut}")
            if (usuarioDao.rutExiste(usuario.rut) > 0) {
                println("❌ [REPOSITORY] RUT YA EXISTE EN LA BASE DE DATOS: ${usuario.rut}")
                return Result.failure(Exception("El RUT ${usuario.rut} ya está registrado"))
            }
            println("✅ [REPOSITORY] RUT disponible: ${usuario.rut}")

            // Verificar si correo ya existe
            println("🔍 [REPOSITORY] Verificando si correo existe: ${usuario.correo}")
            if (usuarioDao.correoExiste(usuario.correo) > 0) {
                println("❌ [REPOSITORY] CORREO YA EXISTE EN LA BASE DE DATOS: ${usuario.correo}")
                return Result.failure(Exception("El correo ${usuario.correo} ya está registrado"))
            }
            println("✅ [REPOSITORY] Correo disponible: ${usuario.correo}")

            // Registrar usuario
            println("💾 [REPOSITORY] Guardando usuario en la base de datos Room...")
            usuarioDao.registrarUsuario(usuario)
            println("✅ [REPOSITORY] USUARIO GUARDADO EXITOSAMENTE EN ROOM")
            println("🎉 [REPOSITORY] Registro completado para: ${usuario.nombre} ${usuario.apellido}")

            Result.success(true)

        } catch (e: Exception) {
            println("💥 [REPOSITORY] ERROR AL GUARDAR EN ROOM: ${e.message}")
            println("🚨 [REPOSITORY] Excepción completa: $e")
            Result.failure(Exception("Error al registrar usuario: ${e.message}"))
        }
    }

    // 👇 REGISTRO SIMPLIFICADO (para usar desde la UI) - ✅ MODIFICADA CON LOGS
    suspend fun registrarUsuario(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        direccion: String,
        password: String,
        rol: String = "cliente"
    ): Result<Boolean> {
        println("🔄 [REPOSITORY] Llamando registro simplificado")
        val usuario = UsuarioEntity(
            rut = rut,
            nombre = nombre,
            apellido = apellido,
            correo = correo,
            direccion = direccion,
            password = password,
            rol = rol
        )
        return registrarUsuario(usuario)
    }

    // 👇 OBTENER USUARIO POR RUT
    suspend fun getUsuarioByRut(rut: String): UsuarioEntity? {
        return try {
            usuarioDao.getUsuarioByRut(rut)
        } catch (e: Exception) {
            null
        }
    }

    // 👇 OBTENER USUARIO POR CORREO
    suspend fun getUsuarioByCorreo(correo: String): UsuarioEntity? {
        return try {
            usuarioDao.getUsuarioByCorreo(correo)
        } catch (e: Exception) {
            null
        }
    }

    // 👇 ACTUALIZAR USUARIO
    suspend fun actualizarUsuario(usuario: UsuarioEntity): Result<Boolean> {
        return try {
            val filasAfectadas = usuarioDao.actualizarUsuario(usuario)
            if (filasAfectadas > 0) {
                Result.success(true)
            } else {
                Result.failure(Exception("No se pudo actualizar el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al actualizar: ${e.message}"))
        }
    }

    // 👇 OBTENER TODOS LOS USUARIOS (Flow para observación en tiempo real)
    fun getAllUsuarios(): Flow<List<UsuarioEntity>> {
        return usuarioDao.getAllUsuarios()
    }

    // 👇 VERIFICAR SI RUT EXISTE
    suspend fun verificarRutExistente(rut: String): Boolean {
        return try {
            usuarioDao.rutExiste(rut) > 0
        } catch (e: Exception) {
            false
        }
    }

    // 👇 VERIFICAR SI CORREO EXISTE
    suspend fun verificarCorreoExistente(correo: String): Boolean {
        return try {
            usuarioDao.correoExiste(correo) > 0
        } catch (e: Exception) {
            false
        }
    }

    // 👇 CAMBIAR CONTRASEÑA
    suspend fun cambiarPassword(rut: String, nuevaPassword: String): Result<Boolean> {
        return try {
            val usuario = usuarioDao.getUsuarioByRut(rut)
            if (usuario != null) {
                val usuarioActualizado = usuario.copy(password = nuevaPassword)
                actualizarUsuario(usuarioActualizado)
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al cambiar contraseña: ${e.message}"))
        }
    }
}