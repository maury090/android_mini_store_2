package com.example.android_mini_store.ui.theme.opciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android_mini_store.config.TextoConfig
import com.example.android_mini_store.data.PreferencesManager
import kotlinx.coroutines.launch

// Enum para los tamaños de fuente
enum class TamanioFuente(val descripcion: String, val factor: Float) {
    PEQUENA("Pequeña", 0.8f),
    STANDAR("Estándar", 1.0f),
    GRANDE("Grande", 1.2f)
}

@Composable
fun TextoAppScreen(navController: NavHostController, preferencesManager: PreferencesManager) {
    val coroutineScope = rememberCoroutineScope()

    // Estado para el tamaño de fuente seleccionado
    var tamanioSeleccionado by remember { mutableStateOf(TamanioFuente.STANDAR) }

    // Cargar la configuración guardada al iniciar
    LaunchedEffect(Unit) {
        preferencesManager.textoTamanio.collect { factorGuardado ->
            // Determinar cuál opción está seleccionada basada en el factor guardado
            tamanioSeleccionado = when (factorGuardado) {
                TamanioFuente.PEQUENA.factor -> TamanioFuente.PEQUENA
                TamanioFuente.GRANDE.factor -> TamanioFuente.GRANDE
                else -> TamanioFuente.STANDAR
            }
            // Actualizar la configuración global
            TextoConfig.actualizarTamanio(factorGuardado)
        }
    }

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
                    .size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver atrás",
                    tint = Color(0xFFFBE10E),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Título centrado
            Text(
                text = "Texto de tu APP",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFBE10E),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        // Contenido de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título de la sección - CENTRADO
            Text(
                text = "Tamaño de fuente",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            // Descripción - CENTRADA
            Text(
                text = "Selecciona el tamaño de texto que prefieres para la aplicación",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            // RadioButtons para los tamaños de fuente
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Opción Pequeña
                    FilaRadioButton(
                        texto = "Pequeña",
                        textoEjemplo = "Este es un texto de ejemplo con tamaño pequeño",
                        tamanioFuente = 14.sp,
                        seleccionado = tamanioSeleccionado == TamanioFuente.PEQUENA,
                        onClick = { tamanioSeleccionado = TamanioFuente.PEQUENA }
                    )

                    // Opción Estándar
                    FilaRadioButton(
                        texto = "Estándar",
                        textoEjemplo = "Este es un texto de ejemplo con tamaño estándar",
                        tamanioFuente = 16.sp,
                        seleccionado = tamanioSeleccionado == TamanioFuente.STANDAR,
                        onClick = { tamanioSeleccionado = TamanioFuente.STANDAR }
                    )

                    // Opción Grande
                    FilaRadioButton(
                        texto = "Grande",
                        textoEjemplo = "Este es un texto de ejemplo con tamaño grande",
                        tamanioFuente = 18.sp,
                        seleccionado = tamanioSeleccionado == TamanioFuente.GRANDE,
                        onClick = { tamanioSeleccionado = TamanioFuente.GRANDE }
                    )
                }
            }

            // Espacio de 25dp entre el card y el botón
            Spacer(modifier = Modifier.height(25.dp))

            // Botón "Guardar configuración" - FUNCIONAL
            Button(
                onClick = {
                    // ✅ GUARDAR CONFIGURACIÓN AL PRESIONAR EL BOTÓN
                    coroutineScope.launch {
                        // Guardar en DataStore
                        preferencesManager.guardarTextoTamanio(tamanioSeleccionado.factor)
                        // Actualizar configuración global
                        TextoConfig.actualizarTamanio(tamanioSeleccionado.factor)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = "Guardar configuración",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun FilaRadioButton(
    texto: String,
    textoEjemplo: String,
    tamanioFuente: androidx.compose.ui.unit.TextUnit,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = seleccionado,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(
            selected = seleccionado,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF4CAF50),
                unselectedColor = Color.Gray
            )
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = textoEjemplo,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                fontSize = tamanioFuente
            )
        }
    }
}