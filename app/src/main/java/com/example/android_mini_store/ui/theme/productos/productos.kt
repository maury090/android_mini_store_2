package com.example.android_mini_store.ui.theme.productos

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_mini_store.R
import androidx.navigation.NavHostController
import com.example.android_mini_store.Screen

// Data class para los productos
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagenResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(navController: NavHostController)
{
    var menuExpanded by remember { mutableStateOf(false) }
    var tiendaExpanded by remember { mutableStateOf(false) }
    var tiendaSeleccionada by remember { mutableStateOf("Tiendas...") }
    val context = LocalContext.current

    // Lista de productos de ejemplo
    val productos = listOf(
        Producto(
            id = 1,
            nombre = "Aceite Coliseo 1L",
            descripcion = "Ahora: $1000 / Antes: $1350",
            imagenResId = R.drawable.aceite_coliseo
        ),
        Producto(
            id = 2,
            nombre = "Leche polvo ULA 800g",
            descripcion = "Ahora: $3000 / Antes: $5000",
            imagenResId = R.drawable.leche_ula
        ),
        Producto(
            id = 3,
            nombre = "Sal de mesa VENUS 1kg",
            descripcion = "Ahora: $180 / Antes: $200",
            imagenResId = R.drawable.sal_venus
        ),
        Producto(
            id = 4,
            nombre = "Té 100 un. Minute",
            descripcion = "Ahora: $2750 / Antes: $3000 ",
            imagenResId = R.drawable.te_jam
        ),
        Producto(
            id = 5,
            nombre = "Azúcar CRAV 1kg",
            descripcion = "Ahora: $700 / Antes: $990",
            imagenResId = R.drawable.azucarcrav
        ),
        Producto(
            id = 6,
            nombre = "Arroz Paladin 1kg",
            descripcion = "Ahora: $950 / Antes: $1100",
            imagenResId = R.drawable.arrozpaladin
        )
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
                    .background(Color.Red)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Menú hamburguesa
                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color(0xFFFBE10E)
                            )
                        }

                        // Menú desplegable
                        androidx.compose.material3.DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier = Modifier.background(Color.Red)
                        ) {
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Bebestibles", color = Color(0xFFFBE10E))
                                },
                                onClick = {}
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Aseo y cuidado personal", color = Color(0xFFFBE10E))
                                },
                                onClick = {}
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Abarrotes", color = Color(0xFFFBE10E))
                                },
                                onClick = {}
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Articulos de hogar", color = Color(0xFFFBE10E))
                                },
                                onClick = {}
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Iniciar Sesion", color = Color(0xFFFBE10E))
                                },
                                onClick = {
                                    menuExpanded = false
                                    navController.navigate("login")
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Productos de la semana",
                        color = Color(0xFFFBE10E),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFBE10E))
                .verticalScroll(rememberScrollState())
        ) {
            // selector de tiendas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Revisa nuestras tiendas a lo largo del país",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Menú desplegable de tiendas
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = tiendaExpanded,
                        onExpandedChange = { tiendaExpanded = !tiendaExpanded }
                    ) {
                        TextField(
                            value = tiendaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = tiendaExpanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Red,
                                unfocusedIndicatorColor = Color.Gray,
                                cursorColor = Color.Red
                            )
                        )

                        // Menu desplegable para tiendas
                        androidx.compose.material3.DropdownMenu(
                            expanded = tiendaExpanded,
                            onDismissRequest = { tiendaExpanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Av Alvarez 2290, Viña del Mar", color = Color.Black)
                                },
                                onClick = {
                                    tiendaSeleccionada = "Tienda Viña del Mar"
                                    tiendaExpanded = false
                                    Toast.makeText(context, "Tienda Viña del Mar seleccionada", Toast.LENGTH_SHORT).show()
                                }
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Almte. Latorre 437, La Calera", color = Color.Black)
                                },
                                onClick = {
                                    tiendaSeleccionada = "Tienda La Calera"
                                    tiendaExpanded = false
                                    Toast.makeText(context, "Tienda La Calera seleccionada", Toast.LENGTH_SHORT).show()
                                }
                            )
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text("Los Ceramistas 8633, La Reina", color = Color.Black)
                                },
                                onClick = {
                                    tiendaSeleccionada = "Tienda La Reina"
                                    tiendaExpanded = false
                                    Toast.makeText(context, "Tienda La Reina seleccionada", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grid de productos con Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Fila 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Producto 1
                    ProductoCard(
                        producto = productos[0],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[0].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Producto 2
                    ProductoCard(
                        producto = productos[1],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[1].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fila 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Producto 3
                    ProductoCard(
                        producto = productos[2],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[2].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Producto 4
                    ProductoCard(
                        producto = productos[3],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[3].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fila 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Producto 5
                    ProductoCard(
                        producto = productos[4],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[4].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Producto 6
                    ProductoCard(
                        producto = productos[5],
                        onClick = {
                            Toast.makeText(context, "Abriendo: ${productos[5].nombre}", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Botones de navegación circulares
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón circular 1 - SIN FUNCIONALIDAD
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                // Botón circular 2
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(50.dp),
                    shape = CircleShape,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(
                        text = "2",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Botón "Volver a Inicio"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {navController.navigate(Screen.Main.route)},
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(45.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Volver a Inicio",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = producto.imagenResId),
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Información del producto
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = producto.nombre,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = producto.descripcion,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}