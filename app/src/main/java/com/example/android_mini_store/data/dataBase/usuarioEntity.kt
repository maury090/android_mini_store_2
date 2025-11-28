package com.example.android_mini_store.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val rut: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val direccion: String,
    val password: String,
    val rol: String = "cliente", // "cliente", "empleado", "admin"
    val fechaRegistro: Long = System.currentTimeMillis(),
    val activo: Boolean = true
)

