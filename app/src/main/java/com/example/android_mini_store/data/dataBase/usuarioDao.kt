package com.example.android_mini_store.data.dataBase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    // Login con RUT o correo
    @Query("SELECT * FROM usuarios WHERE (rut = :identificador OR correo = :identificador) AND password = :password AND activo = 1")
    suspend fun login(identificador: String, password: String): UsuarioEntity?

    // Registrar nuevo usuario
    @Insert
    suspend fun registrarUsuario(usuario: UsuarioEntity)

    // CREAR USUARIO ADMIN
    @Insert
    suspend fun crearUsuarioAdmin(usuario: UsuarioEntity)

    // Verificar si RUT ya existe
    @Query("SELECT COUNT(*) FROM usuarios WHERE rut = :rut")
    suspend fun rutExiste(rut: String): Int

    // Verificar si correo ya existe
    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    suspend fun correoExiste(correo: String): Int

    // 🆕 VERIFICAR SI YA EXISTE OPERARIO CON MISMO NOMBRE Y APELLIDO
    @Query("SELECT COUNT(*) FROM usuarios WHERE nombre = :nombre AND apellido = :apellido AND rol = 'operario'")
    suspend fun existeNombreCompletoOperario(nombre: String, apellido: String): Int

    // VERIFICAR SI USUARIO ADMIN EXISTE
    @Query("SELECT COUNT(*) FROM usuarios WHERE rut = '88888888-8'")
    suspend fun existeUsuarioAdmin(): Int

    // Obtener usuario por RUT
    @Query("SELECT * FROM usuarios WHERE rut = :rut")
    suspend fun getUsuarioByRut(rut: String): UsuarioEntity?

    // Obtener usuario por correo
    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun getUsuarioByCorreo(correo: String): UsuarioEntity?

    // Actualizar datos del usuario
    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity): Int

    // 🗑️ ELIMINAR USUARIO FÍSICAMENTE (Ya existente)
    @Query("DELETE FROM usuarios WHERE rut = :rut")
    suspend fun eliminarUsuario(rut: String): Int

    // Obtener todos los usuarios activos
    @Query("SELECT * FROM usuarios WHERE activo = 1")
    fun getAllUsuarios(): Flow<List<UsuarioEntity>>

    // FUNCIÓN PARA CREAR ADMIN INICIAL
    suspend fun crearUsuarioAdminInicial() {
        try {
            if (existeUsuarioAdmin() == 0) {
                val adminUser = UsuarioEntity(
                    rut = "88888888-8",
                    nombre = "Admin",
                    apellido = "EKONO",
                    correo = "admin@ekono.com",
                    direccion = "Oficina Central",
                    password = "admin123",
                    rol = "admin",
                    fechaRegistro = System.currentTimeMillis(),
                    activo = true
                )
                crearUsuarioAdmin(adminUser)
                println("✅ [DATABASE] USUARIO ADMIN CREADO AUTOMÁTICAMENTE")
                println("📧 Email: admin@ekono.com")
                println("🔐 Password: admin123")
                println("🎯 Rol: admin")
            } else {
                println("ℹ️ [DATABASE] Usuario admin ya existe")
            }
        } catch (e: Exception) {
            println("❌ [DATABASE] Error creando usuario admin: ${e.message}")
        }
    }
}