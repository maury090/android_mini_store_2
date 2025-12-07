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
    val password: String,
    val direccion: String,
    val rol: String, // "admin", "cliente", "operario"
    val activo: Boolean = true,
    val fechaRegistro: Long = System.currentTimeMillis()
)