package com.example.android_mini_store.ui.theme.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.android_mini_store.data.dataBase.UsuarioEntity
import com.example.android_mini_store.data.repository.usuarioRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionUsuariosScreen(
    navController: NavHostController,
    adminViewModel: AdminViewModel
) {
    // 1. OBTENER VIEWMODEL
    val adminViewModel: AdminViewModel = viewModel(
        factory = AdminViewModelFactory(
            usuarioRepository(
                AppDatabase.getDatabase(LocalContext.current).usuarioDao()
            )
        )
    )

    // 2. OBSERVAR ESTADOS
    val usuarios by adminViewModel.usuariosState.collectAsState()
    val isLoading by adminViewModel.loadingState.collectAsState()
    val error by adminViewModel.errorState.collectAsState()

    // 3. ESTADOS DEL MENÚ DESPLEGABLE
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Cliente") }

    // 🚨 CORRECCIÓN: Llamadas a través de adminViewModel
    LaunchedEffect(selectedOption) {
        if (selectedOption == "Cliente") {
            adminViewModel.cargarUsuariosClientes() // ✅ CORREGIDO
        } else if (selectedOption == "Todos") {
            adminViewModel.cargarUsuarios() // ✅ CORREGIDO
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios Registrados", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
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

            // FILTRO (Menú desplegable)
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
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(text = { Text("Cliente") }, onClick = { selectedOption = "Cliente"; expanded = false })
                    DropdownMenuItem(text = { Text("Operario Tienda") }, onClick = { selectedOption = "Operario Tienda"; expanded = false })
                    DropdownMenuItem(text = { Text("Todos") }, onClick = { selectedOption = "Todos"; expanded = false })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ESTADOS DE CARGA Y ERROR
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                        Text("Cargando usuarios...", color = Color.Black)
                    }
                }
                return@Column
            }

            if (error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(text = "Error: $error", color = Color.Red, textAlign = TextAlign.Center)
                        Button(
                            onClick = {
                                // 🚨 CORRECCIÓN DE LA LLAMADA EN BOTÓN
                                if (selectedOption == "Cliente") adminViewModel.cargarUsuariosClientes()
                                else adminViewModel.cargarUsuarios()
                            }
                        ) { Text("Reintentar") }
                    }
                }
                return@Column
            }

            // LISTA DE CARDS DE USUARIOS (Reemplazo de la tabla)
            if (usuarios.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No hay usuarios registrados", color = Color.Gray, fontSize = 18.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(usuarios) { usuario ->
                        UsuarioCard(
                            usuario = usuario,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// COMPONENTE CARD: SÓLO NOMBRE Y RUT
// ----------------------------------------------------
@Composable
fun UsuarioCard(
    usuario: UsuarioEntity,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                println("👤 [ADMIN] Click en ${usuario.nombre} - Navegando a detalle")
                // Navegación a la futura vista de detalle
                navController.navigate("usuario_info/${usuario.rut}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // COLUMNA 1: NOMBRE COMPLETO
            Text(
                text = "${usuario.nombre} ${usuario.apellido}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )

            // COLUMNA 2: RUT
            Text(
                text = usuario.rut ?: "Sin RUT",
                color = Color.DarkGray,
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
        }
    }
}