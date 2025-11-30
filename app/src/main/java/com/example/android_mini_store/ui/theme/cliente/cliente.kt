package com.example.android_mini_store.ui.theme.clientes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// IMPORTS NECESARIOS
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import com.example.android_mini_store.Screen

import com.example.android_mini_store.ui.theme.Android_mini_storeTheme
import com.example.android_mini_store.config.TextoConfig
import com.example.android_mini_store.ui.auth.AuthViewModel

@Composable
fun ClientesScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    // Obtener estado del usuario actual
    val userState by authViewModel.currentUser.collectAsState()

    Android_mini_storeTheme {
        Scaffold(
            containerColor = Color(0xFFFBE10E)
        ) { paddingValues ->
            ClientesContent(
                navController = navController,
                authViewModel = authViewModel,
                userState = userState,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun ClientesContent(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userState: AuthViewModel.UsuarioState,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // 🟡 TÍTULO BIENVENIDA EKONO
        Text(
            text = "¡Bienvenido a EKONO!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = TextoConfig.tituloPantalla
        )

        // 🆕 NOMBRE COMPLETO DEL USUARIO
        when (userState) {
            is AuthViewModel.UsuarioState.Logged -> {
                val currentUser = userState.usuario
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sr/a",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = TextoConfig.subtitulo
                    )
                    Text(
                        text = "${currentUser.nombre} ${currentUser.apellido}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        fontSize = TextoConfig.tituloPantalla
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🆕 BOTONES DEL MENÚ CLIENTE
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 1️⃣ VER PRODUCTOS - VERDE ORIGINAL
                    Button(
                        onClick = {
                            // Sin función por ahora
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50), // 🟢 VERDE ORIGINAL
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Ver productos",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Ver Productos",
                                fontSize = TextoConfig.boton,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 2️⃣ HISTORIAL DE PEDIDOS - VERDE OSCURO
                    Button(
                        onClick = {
                            // Sin función por ahora
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF388E3C), // 🟢 VERDE OSCURO
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.History,
                                contentDescription = "Historial de pedidos",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Historial de Pedidos",
                                fontSize = TextoConfig.boton,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 3️⃣ MI PERFIL - AZUL
                    Button(
                        onClick = {
                            // Sin función por ahora
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3), // 🔵 AZUL
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Mi perfil",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Mi Perfil",
                                fontSize = TextoConfig.boton,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 4️⃣ OPCIONES - GRIS CON ICONO BUILD
                    Button(
                        onClick = {
                            // Sin función por ahora
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray, // 🔘 GRIS (mismo que MainActivity)
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Build, // 🛠️ ICONO BUILD (mismo que MainActivity)
                                contentDescription = "Opciones",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Opciones",
                                fontSize = TextoConfig.boton,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 5️⃣ CERRAR SESIÓN - ROJO ORIGINAL
                    Button(
                        onClick = {
                            authViewModel.logout()
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Cliente.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, // 🔴 ROJO ORIGINAL
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.ExitToApp,
                                contentDescription = "Cerrar sesión",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                "Cerrar Sesión",
                                fontSize = TextoConfig.boton,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            else -> {
                // USUARIO NO LOGGEADO
                Text(
                    text = "No hay usuario loggeado",
                    color = Color.Red,
                    fontSize = TextoConfig.textoNormal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}