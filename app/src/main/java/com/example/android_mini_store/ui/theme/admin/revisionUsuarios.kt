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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.android_mini_store.data.dataBase.AppDatabase
import com.example.android_mini_store.data.repository.usuarioRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionUsuariosScreen(
    navController: NavHostController
) {
    // OBTENER VIEWMODEL
    val adminViewModel: AdminViewModel = viewModel(
        factory = AdminViewModelFactory(
            usuarioRepository(
                AppDatabase.getDatabase(LocalContext.current).usuarioDao()
            )
        )
    )

    // OBSERVAR ESTADOS
    val usuarios by adminViewModel.usuariosState.collectAsState()
    val isLoading by adminViewModel.loadingState.collectAsState()
    val error by adminViewModel.errorState.collectAsState()

    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Cliente") }

    // Cargar usuarios al entrar a la pantalla
    LaunchedEffect(Unit) {
        adminViewModel.cargarUsuarios()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Usuarios Registrados",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
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
                .padding(16.dp)
        ) {
            // MENÚ DESPLEGABLE
            Text(
                text = "Usuario a visualizar:",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cliente") },
                        onClick = {
                            selectedOption = "Cliente"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Operario Tienda") },
                        onClick = {
                            selectedOption = "Operario Tienda"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Todos") },
                        onClick = {
                            selectedOption = "Todos"
                            expanded = false
                            adminViewModel.cargarUsuarios()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ESTADO DE CARGA
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                        Text("Cargando usuarios...", color = Color.Black)
                    }
                }
                return@Column
            }

            // ESTADO DE ERROR
            if (error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { adminViewModel.cargarUsuarios() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                return@Column
            }

            // TABLA DE USUARIOS
            if (usuarios.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay usuarios registrados",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            } else {
                // ENCABEZADO DE LA TABLA
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Nombre",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "RUT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = "E-mail",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = "Info",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // LISTA DE USUARIOS EN FORMATO TABLA
                LazyColumn {
                    items(usuarios) { usuario ->
                        // ✅ CORREGIDO: Pasar navController como parámetro
                        FilaUsuario(
                            usuario = usuario,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilaUsuario(
    usuario: com.example.android_mini_store.data.dataBase.UsuarioEntity,
    navController: NavHostController // ✅ AGREGAR este parámetro
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // COLUMNA 1: NOMBRE
            Text(
                text = "${usuario.nombre} ${usuario.apellido}",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.weight(2f)
            )

            // COLUMNA 2: RUT
            Text(
                text = usuario.rut ?: "Sin RUT",
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.weight(1.5f)
            )

            // COLUMNA 3: E-MAIL
            Text(
                text = usuario.correo,
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier.weight(2f)
            )

            // COLUMNA 4: ENLACE "+" - ✅ AHORA CON navController DISPONIBLE
            TextButton(
                onClick = {
                    println("👤 [ADMIN] Navegando a información de: ${usuario.nombre} ${usuario.apellido}")
                    navController.navigate("usuario_info") // ✅ FUNCIONA
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ver información",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}