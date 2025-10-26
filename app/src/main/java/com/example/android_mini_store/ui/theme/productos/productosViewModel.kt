package com.example.android_mini_store.ui.theme.productos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.android_mini_store.R

class ProductosViewModel : ViewModel() {

    // Estado para la tienda seleccionada
    private val _tiendaSeleccionada = MutableStateFlow("Tienda 1")
    val tiendaSeleccionada: StateFlow<String> = _tiendaSeleccionada.asStateFlow()

    // Lista de productos de ejemplo CON IMÁGENES
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        cargarProductosEjemplo()
    }

    // Función para cambiar la tienda seleccionada
    fun cambiarTienda(nuevaTienda: String) {
        _tiendaSeleccionada.value = nuevaTienda
    }

    // Cargar productos de ejemplo CON REFERENCIAS A DRAWABLE
    private fun cargarProductosEjemplo() {
        val productosEjemplo = listOf(
            Producto(
                id = 1,
                nombre = "Arroz Extra",
                precio = 3.20,
                imagenResId = R.drawable.aceite_coliseo,
                descuento = "10%"
            ),
            Producto(
                id = 2,
                nombre = "Aceite Vegetal",
                precio = 8.50,
                imagenResId = R.drawable.aceite, // Referencia al drawable
                descuento = "15%"
            ),
            Producto(
                id = 3,
                nombre = "Leche Entera",
                precio = 4.80,
                imagenResId = R.drawable.leche, // Referencia al drawable
                descuento = "5%"
            ),
            Producto(
                id = 4,
                nombre = "Fideos Tallarín",
                precio = 2.10,
                imagenResId = R.drawable.fideos, // Referencia al drawable
                descuento = "20%"
            ),
            Producto(
                id = 5,
                nombre = "Atún en Lata",
                precio = 6.25,
                imagenResId = R.drawable.atun, // Referencia al drawable
                descuento = "12%"
            ),
            Producto(
                id = 6,
                nombre = "Galletas Soda",
                precio = 3.10,
                imagenResId = R.drawable.galletas, // Referencia al drawable
                descuento = "8%"
            )
        )
        _productos.value = productosEjemplo
    }
}

// Data class para productos CON resource ID
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenResId: Int, // Int para resource ID de drawable
    val descuento: String
)