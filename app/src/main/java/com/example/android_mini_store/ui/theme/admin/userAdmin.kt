package com.example.android_mini_store.ui.theme.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android_mini_store.Screen
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme

@Composable
fun UserAdminScreen(
    navController: NavHostController
) {
    Android_mini_storeTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Mensaje de bienvenida
            Text(
                text = "Bienvenido a EKONO",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Subtítulo de usuario admin
            Text(
                text = "Usuario admin",
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Botones de administración
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🆕 BOTÓN 1: REVISAR USUARIOS - AHORA CON FUNCIONALIDAD COMPLETA
                Button(
                    onClick = {
                        // 🆕 NAVEGAR A LA PANTALLA DE REVISIÓN DE USUARIOS
                        navController.navigate(Screen.RevisionUsuarios.route)
                        println("🔍 [ADMIN] Navegando a revisión de usuarios")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Verde
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Revisar usuarios",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = " Revisar usuarios",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Botón 2: Crear usuario tienda (por implementar)
                Button(
                    onClick = {
                        // TODO: Navegar a crear usuario tienda
                        println("🏪 [ADMIN] Crear usuario tienda - Por implementar")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Verde
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = "Crear usuario tienda",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = " Crear usuario tienda",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Botón 3: Stock tienda (por implementar)
                Button(
                    onClick = {
                        // TODO: Navegar a stock tienda
                        println("📦 [ADMIN] Stock tienda - Por implementar")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Verde
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Inventory,
                        contentDescription = "Stock tienda",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = " Stock tienda",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // 🆕 BOTÓN 4: CERRAR SESIÓN - AHORA CON FUNCIONALIDAD COMPLETA
                Button(
                    onClick = {
                        // 🆕 CERRAR SESIÓN Y VOLVER AL LOGIN
                        println("🚪 [ADMIN] Cerrando sesión de admin")
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Admin.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = " Cerrar sesión",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}