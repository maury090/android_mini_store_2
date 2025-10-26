package com.example.android_mini_store.ui.theme.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {

    // Estado para la tienda seleccionada
    private val _tiendaSeleccionada = MutableStateFlow("Tienda 1")
    val tiendaSeleccionada: StateFlow<String> = _tiendaSeleccionada.asStateFlow()

    // Estado para la ubicación
    private val _ubicacion = MutableStateFlow("")
    val ubicacion: StateFlow<String> = _ubicacion.asStateFlow()

    // Estado para permisos de ubicación
    private val _permisosUbicacion = MutableStateFlow(false)
    val permisosUbicacion: StateFlow<Boolean> = _permisosUbicacion.asStateFlow()

    // Estado para mostrar mensaje de ubicación
    private val _mostrarUbicacion = MutableStateFlow(false)
    val mostrarUbicacion: StateFlow<Boolean> = _mostrarUbicacion.asStateFlow()

    // Lista de productos de ejemplo
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        // Inicializar con productos de ejemplo
        cargarProductosEjemplo()
    }

    // Función para cambiar la tienda seleccionada
    fun cambiarTienda(nuevaTienda: String) {
        _tiendaSeleccionada.value = nuevaTienda
    }

    // Función para actualizar la ubicación
    fun actualizarUbicacion(nuevaUbicacion: String) {
        _ubicacion.value = nuevaUbicacion
        _mostrarUbicacion.value = true
    }

    // Función para actualizar estado de permisos
    fun actualizarPermisos(concedidos: Boolean) {
        _permisosUbicacion.value = concedidos
    }

    // Función para ocultar el mensaje de ubicación
    fun ocultarUbicacion() {
        _mostrarUbicacion.value = false
    }

    // Cargar productos de ejemplo
    private fun cargarProductosEjemplo() {
        val productosEjemplo = listOf(
            Producto(
                id = 1,
                nombre = "Arroz Integral",
                precio = 2.50,
                imagenUrl = "",
                descuento = "10%"
            ),
            Producto(
                id = 2,
                nombre = "Aceite de Oliva",
                precio = 5.99,
                imagenUrl = "",
                descuento = "15%"
            ),
            Producto(
                id = 3,
                nombre = "Leche Deslactosada",
                precio = 1.80,
                imagenUrl = "",
                descuento = "5%"
            ),
            Producto(
                id = 4,
                nombre = "Pasta Integral",
                precio = 1.20,
                imagenUrl = "",
                descuento = "20%"
            ),
            Producto(
                id = 5,
                nombre = "Atún en Lata",
                precio = 3.25,
                imagenUrl = "",
                descuento = "12%"
            ),
            Producto(
                id = 6,
                nombre = "Galletas Integrales",
                precio = 2.10,
                imagenUrl = "",
                descuento = "8%"
            )
        )
        _productos.value = productosEjemplo
    }
}

// Data class para productos
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenUrl: String,
    val descuento: String
)