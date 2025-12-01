package com.example.android_mini_store.ui.theme.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android_mini_store.data.dataBase.UsuarioEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioInfoScreen(
    navController: NavHostController,
    usuario: UsuarioEntity
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "Perfil Cliente",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                "${usuario.nombre} ${usuario.apellido}",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                )
            )
        },
        containerColor = Color(0xFFFBE10E)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // TABLA CON DATOS REALES DE LA BASE DE DATOS
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // ✅ RUT (dato real de la BD)
                    InfoRow(
                        descripcion = "RUT:",
                        valor = usuario.rut ?: "No registrado"
                    )

                    // ✅ NOMBRE COMPLETO (datos reales de la BD)
                    InfoRow(
                        descripcion = "Nombre completo:",
                        valor = "${usuario.nombre} ${usuario.apellido}"
                    )

                    // ✅ E-MAIL (dato real de la BD)
                    InfoRow(
                        descripcion = "E-mail:",
                        valor = usuario.correo
                    )

                    // ✅ DIRECCIÓN (dato real de la BD)
                    InfoRow(
                        descripcion = "Dirección:",
                        valor = usuario.direccion ?: "No registrada"
                    )

                    // ✅ ROL (dato real de la BD)
                    InfoRow(
                        descripcion = "Tipo de usuario:",
                        valor = usuario.rol ?: "cliente"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN ELIMINAR USUARIO
            Button(
                onClick = {
                    println("🗑️ [ADMIN] Eliminar usuario: ${usuario.nombre} ${usuario.apellido}")
                    // TODO: Implementar lógica de eliminación
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Eliminar Usuario",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // BOTÓN ATRÁS
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Atrás",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun InfoRow(descripcion: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = descripcion,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = valor,
            color = Color.DarkGray,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}