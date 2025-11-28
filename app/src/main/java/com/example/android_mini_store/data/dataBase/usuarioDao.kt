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

    // Verificar si RUT ya existe
    @Query("SELECT COUNT(*) FROM usuarios WHERE rut = :rut")
    suspend fun rutExiste(rut: String): Int

    // Verificar si correo ya existe
    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    suspend fun correoExiste(correo: String): Int

    // Obtener usuario por RUT
    @Query("SELECT * FROM usuarios WHERE rut = :rut")
    suspend fun getUsuarioByRut(rut: String): UsuarioEntity?

    // Obtener usuario por correo
    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun getUsuarioByCorreo(correo: String): UsuarioEntity?

    // Actualizar datos del usuario
    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity): Int

    // Obtener todos los usuarios activos
    @Query("SELECT * FROM usuarios WHERE activo = 1")
    fun getAllUsuarios(): Flow<List<UsuarioEntity>>
}