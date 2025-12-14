package com.example.android_mini_store.data.repository

import com.example.android_mini_store.data.dataBase.UsuarioDao
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class usuarioRepository(private val usuarioDao: UsuarioDao) {



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

    // 🗑️ ELIMINAR USUARIO FÍSICAMENTE
    suspend fun eliminarUsuario(rut: String): Result<Int> {
        return try {
            val filasAfectadas = usuarioDao.eliminarUsuario(rut)
            if (filasAfectadas > 0) {
                Result.success(filasAfectadas)
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al eliminar usuario: ${e.message}"))
        }
    }

    fun getAllUsuarios(): Flow<List<UsuarioEntity>> {
        return usuarioDao.getAllUsuarios()
    }

    // MÉTODO PARA INICIALIZAR ADMIN
    suspend fun inicializarUsuarioAdmin() {
        usuarioDao.crearUsuarioAdminInicial()
    }
    /**
     * 🆕 NORMALIZAR TEXTO (quitar acentos, minúsculas, solo letras)
     */
    private fun normalizarTexto(texto: String): String {
        return texto.lowercase()
            .replace("á", "a").replace("é", "e").replace("í", "i")
            .replace("ó", "o").replace("ú", "u").replace("ñ", "n")
            .filter { it.isLetter() }
    }

    /**
     * 🆕 TOMAR LETRAS (con relleno si es necesario)
     * @param texto Texto original
     * @param cantidad Cantidad de letras a tomar
     * @return Texto con la cantidad especificada, rellenado con 'x' si es más corto
     */
    private fun tomarLetras(texto: String, cantidad: Int): String {
        val letras = texto.take(cantidad)
        return if (letras.length < cantidad) {
            letras.padEnd(cantidad, 'x')
        } else {
            letras
        }
    }

}