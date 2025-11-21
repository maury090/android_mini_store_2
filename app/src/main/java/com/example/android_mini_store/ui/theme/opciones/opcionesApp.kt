package com.example.android_mini_store.ui.theme.opciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.android_mini_store.Screen

@Composable
fun OpcionesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBE10E))
    ) {
        // Barra superior roja personalizada
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Red)
        ) {
            // Flecha de retroceso en esquina superior izquierda
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(56.dp) // Área de click más grande
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver atrás",
                    tint = Color(0xFFFBE10E), // Color amarillo del fondo de la app
                    modifier = Modifier.size(24.dp)
                )
            }

            // Título centrado
            Text(
                text = "Opciones",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFBE10E), // Color amarillo del fondo de la app
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Contenido de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cards de opciones
            BotonOpcion(
                titulo = "Texto de tu APP",
                subtitulo = "Personaliza la tipografía de la aplicación",
                icono = Icons.Default.TextFields,
                onClick = { navController.navigate("textoApp")}
            )

            BotonOpcion(
                titulo = "Colores de tu APP",
                subtitulo = "Cambia el esquema de colores de la aplicación",
                icono = Icons.Default.Palette,
                onClick = { }
            )

            BotonOpcion(
                titulo = "Modificaciones futuras APP",
                subtitulo = "Disponible en nuevas actualizaciones",
                icono = Icons.Default.Build,
                onClick = { }
            )

            // Espacio de 20dp entre los cards y el botón
            Spacer(modifier = Modifier.height(20.dp))

            // boton pagina de inicio
            Button(
                onClick = {navController.navigate(Screen.Main.route)},
                modifier = Modifier
                    .fillMaxWidth(0.8f) // 80% del ancho para mejor apariencia
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red, // Color rojo como solicitaste
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Volver a página inicio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun BotonOpcion(
    titulo: String,
    subtitulo: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(30.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir",
                tint = Color.DarkGray,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}