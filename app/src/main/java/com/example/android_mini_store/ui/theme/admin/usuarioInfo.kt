package com.example.android_mini_store.ui.theme.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android_mini_store.config.TextoConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.Toast
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioInfoScreen(
    navController: NavHostController,
    adminViewModel: AdminViewModel,
    rutUsuario: String // RUT pasado desde MainActivity
) {
    // 1. Estados Observables del ViewModel
    val usuarioDetalle by adminViewModel.usuarioDetalleState.collectAsState()
    val loadingState by adminViewModel.loadingState.collectAsState()
    val errorState by adminViewModel.errorState.collectAsState()
    val updateSuccess by adminViewModel.updateSuccessState.collectAsState()

    // 2. Estado local para el campo editable (Dirección)
    var nuevaDireccion by remember { mutableStateOf("") }

    // 3. Contexto para Toast
    val context = LocalContext.current

    // 4. Estado para mostrar Toast de campo vacío
    var showEmptyFieldToast by remember { mutableStateOf(false) }

    // 5. Efecto: Carga los datos del usuario al iniciar la pantalla
    LaunchedEffect(rutUsuario) {
        adminViewModel.fetchUsuarioDetalle(rutUsuario)
    }

    // 6. Efecto: Sincroniza el campo editable con la dirección cargada (o actualizada)
    LaunchedEffect(usuarioDetalle) {
        usuarioDetalle?.direccion?.let {
            nuevaDireccion = it
        }
    }

    // 7. Efecto: Mostrar Toast cuando se actualiza la dirección exitosamente
    LaunchedEffect(updateSuccess) {
        if (updateSuccess) {
            Toast.makeText(context, "✅ Información actualizada correctamente", Toast.LENGTH_SHORT).show()
            adminViewModel.resetUpdateSuccess()
        }
    }

    // 8. Efecto: Mostrar Toast cuando se intenta guardar campo vacío
    LaunchedEffect(showEmptyFieldToast) {
        if (showEmptyFieldToast) {
            Toast.makeText(context, "⚠️ La dirección no puede estar vacía", Toast.LENGTH_SHORT).show()
            // Resetear después de mostrar
            showEmptyFieldToast = false
        }
    }

    // Colores de la aplicación
    val backgroundColor = Color(0xFFFFD700) // Amarillo (#FFD700)
    val accentColor = Color(0xFF4CAF50)     // Verde para acentos
    val cardBackground = Color.White        // Fondo blanco para las tarjetas

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Información del Usuario",
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Manejo de estados de carga/error
                if (loadingState && usuarioDetalle == null) {
                    Spacer(Modifier.height(32.dp))
                    CircularProgressIndicator(color = Color.Black)
                    Text(
                        "Cargando datos...",
                        Modifier.padding(top = 16.dp),
                        color = Color.Black
                    )
                } else if (errorState != null) {
                    Spacer(Modifier.height(32.dp))
                    Text("Error: $errorState", color = Color.Red)
                } else if (usuarioDetalle == null) {
                    Spacer(Modifier.height(32.dp))
                    Text("Usuario no encontrado.", color = Color.Black)
                }

                // Vista principal (se muestra solo si hay datos)
                usuarioDetalle?.let { usuario ->
                    Spacer(Modifier.height(24.dp))

                    // --- TABLA DE INFORMACIÓN NO EDITABLE ---
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBackground
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text("Datos del Usuario",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            InfoRow("RUT:", usuario.rut)
                            InfoRow("Nombre:", "${usuario.nombre} ${usuario.apellido}")
                            InfoRow("Correo:", usuario.correo)
                            InfoRow("Rol:", usuario.rol.replaceFirstChar { it.uppercase() })
                            InfoRow("Activo:", if (usuario.activo) "Sí" else "No")

                            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            val fecha = dateFormat.format(Date(usuario.fechaRegistro))
                            InfoRow("Registro:", fecha)
                        }
                    }

                    Spacer(Modifier.height(32.dp))

                    // --- CAMPO EDITABLE: DIRECCIÓN ---
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text(
                                "Dirección", // (opcional) eliminado
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            OutlinedTextField(
                                value = nuevaDireccion,
                                onValueChange = { nuevaDireccion = it },
                                label = {
                                    Text(
                                        "Dirección de usuario",
                                        color = Color.Gray
                                    )
                                },
                                placeholder = {
                                    Text("Ej: Calle Principal 123")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                enabled = !loadingState,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accentColor,
                                    unfocusedBorderColor = Color.Gray,
                                    focusedLabelColor = accentColor,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = accentColor,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                ),
                                shape = MaterialTheme.shapes.medium
                            )

                            // Nota modificada
                            Text(
                                "Para mantener información no cambies lo de este campo",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // --- BOTÓN DE ACTUALIZACIÓN ---
                    val hayCambios = nuevaDireccion.isNotEmpty() && nuevaDireccion != usuario.direccion

                    Button(
                        onClick = {
                            // Solo guardar si hay cambios y contenido
                            adminViewModel.actualizarDireccionUsuario(
                                rut = usuario.rut,
                                nuevaDireccion = nuevaDireccion
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = Color.White,
                            disabledContainerColor = accentColor.copy(alpha = 0.5f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f)
                        ),

                        enabled = !loadingState && hayCambios,
                        shape = MaterialTheme.shapes.large,
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        if (loadingState) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                Icons.Default.Save,
                                contentDescription = "Guardar",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Actualizar Dirección",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Indicador de estado del campo
                    Spacer(Modifier.height(16.dp))

                    if (hayCambios) {
                        Text(
                            "📝 Hay cambios pendientes por guardar",
                            color = accentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    } else if (nuevaDireccion.isEmpty()) {
                        Text(
                            "⚠️ El campo dirección no puede quedar vacío",
                            color = Color.Red.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    } else {
                        Text(
                            "✅ La dirección actual está guardada",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }

                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

// Componente para mostrar una fila de información no editable
@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}