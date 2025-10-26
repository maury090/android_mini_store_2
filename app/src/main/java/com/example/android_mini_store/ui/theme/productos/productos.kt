package com.example.android_mini_store.ui.theme.productos

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductosScreen() {
    val viewModel: ProductosViewModel = viewModel()
    val context = LocalContext.current

    // Estados del ViewModel
    val tiendaSeleccionada by viewModel.tiendaSeleccionada.collectAsState()
    val ubicacion by viewModel.ubicacion.collectAsState()
    val permisosUbicacion by viewModel.permisosUbicacion.collectAsState()
    val mostrarUbicacion by viewModel.mostrarUbicacion.collectAsState()
    val productos by viewModel.productos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Productos",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Subtítulo
        Text(
            text = "Busca las tiendas mas cercanas a ti o activa tu ubicacion",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Fila: Dropdown + Botón Ubicación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Dropdown de Tiendas (65% del ancho)
            TiendasDropdown(
                tiendaSeleccionada = tiendaSeleccionada,
                onTiendaSeleccionada = { viewModel.cambiarTienda(it) },
                modifier = Modifier.weight(0.65f)
            )

            // Botón de Ubicación (35% del ancho)
            Button(
                onClick = {
                    if (permisosUbicacion) {
                        // Simular obtención de ubicación
                        viewModel.actualizarUbicacion("Ubicación: Lat -12.0464, Lon -77.0428")
                    } else {
                        // Solicitar permisos
                        solicitarPermisosUbicacion(context) { concedidos ->
                            viewModel.actualizarPermisos(concedidos)
                            if (concedidos) {
                                viewModel.actualizarUbicacion("Ubicación: Lat -12.0464, Lon -77.0428")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(0.35f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text("Ubicación", fontSize = 14.sp)
            }
        }

        // Mensaje de ubicación (si está disponible)
        if (mostrarUbicacion && ubicacion.isNotEmpty()) {
            Text(
                text = ubicacion,
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Sección de descuentos
        Text(
            text = "Revisa nuestros descuentos ekoProductos de la semana",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Grid de productos (2 columnas, máximo 3 filas = 6 productos)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(520.dp) // Altura fija para mostrar máximo 3 filas
        ) {
            items(productos) { producto ->
                ProductoCard(producto = producto)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendasDropdown(
    tiendaSeleccionada: String,
    onTiendaSeleccionada: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val tiendas = listOf("Tienda 1", "Tienda 2", "Tienda 3")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = tiendaSeleccionada,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            placeholder = { Text("tiendas...") },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            tiendas.forEach { tienda ->
                DropdownMenuItem(
                    text = { Text(tienda) },
                    onClick = {
                        onTiendaSeleccionada(tienda)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Etiqueta de descuento
            Box(
                modifier = Modifier
                    .align(Alignment.Start)
            ) {
                Text(
                    text = producto.descuento,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color(0xFFFF5722), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // Espacio para imagen (simulada)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Imagen", color = Color.Gray, fontSize = 12.sp)
            }

            // Información del producto
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 2
                )
                Text(
                    text = "S/. ${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Función simulada para solicitar permisos (debes implementar la real)
fun solicitarPermisosUbicacion(context: Context, onResult: (Boolean) -> Unit) {
    // En una implementación real, usarías ActivityResultContracts
    // Por ahora simulamos que se conceden los permisos
    Toast.makeText(context, "Solicitando permisos de ubicación...", Toast.LENGTH_SHORT).show()

    // Simulamos una respuesta después de 1 segundo
    android.os.Handler(context.mainLooper).postDelayed({
        onResult(true)
        Toast.makeText(context, "Permisos de ubicación concedidos", Toast.LENGTH_SHORT).show()
    }, 1000)
}