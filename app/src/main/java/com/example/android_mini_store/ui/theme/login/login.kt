package com.example.android_mini_store.ui.theme.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.android_mini_store.Screen
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme
import kotlinx.coroutines.delay

// ICONOS OFFLINE
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd

// CONFIGURACIÓN DE TEXTO
import com.example.android_mini_store.config.TextoConfig

// ✅ IMPORT CORREGIDO - VERIFICA ESTA LÍNEA
import com.example.android_mini_store.ui.auth.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val viewModel: LoginViewModel = viewModel()

    val showSnackbar by viewModel.showSnackbar.collectAsState()

    // ✅ Estado del login desde Room
    val loginState by authViewModel.loginState.collectAsState()

    // Ocultar automáticamente el Snackbar después de 3 segundos
    if (showSnackbar) {
        LaunchedEffect(showSnackbar) {
            delay(3000)
            viewModel.hideSnackbar()
        }
    }

    // ✅ Manejar el resultado del login con Room
    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthViewModel.LoginState.Success -> {
                // Login exitoso - navegar a pantalla principal
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
            is AuthViewModel.LoginState.Error -> {
                // Mostrar error de Room
                viewModel.showSnackbarWithMessage(
                    (loginState as AuthViewModel.LoginState.Error).message
                )
                authViewModel.clearLoginState()
            }
            else -> {}
        }
    }

    Android_mini_storeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            LoginContent(navController, viewModel, authViewModel)

            // Snackbar en la PARTE SUPERIOR
            if (showSnackbar) {
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .statusBarsPadding(),
                    action = {
                        TextButton(
                            onClick = { viewModel.hideSnackbar() }
                        ) {
                            Text(
                                "Cerrar",
                                color = Color.White,
                                fontSize = TextoConfig.boton
                            )
                        }
                    }
                ) {
                    Text(
                        "Campos de correo y contraseña vacíos",
                        color = Color.White,
                        fontSize = TextoConfig.textoNormal
                    )
                }
            }
        }
    }
}

private fun LoginViewModel.showSnackbarWithMessage(
    message: String
) {
}

@Composable
fun LoginContent(
    navController: NavHostController,
    viewModel: LoginViewModel,
    authViewModel: AuthViewModel
) {

    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    // ✅ Estado de loading desde Room
    val isLoading = authViewModel.loginState.collectAsState().value is AuthViewModel.LoginState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 38.dp),
            fontSize = TextoConfig.tituloPantalla
        )

        // Campo email
        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = {
                Text(
                    "Correo electrónico o RUT",
                    fontSize = TextoConfig.textoNormal
                )
            },
            placeholder = {
                Text(
                    "ejemplo@correo.com o 123456789",
                    fontSize = TextoConfig.pequeno
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Campo contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = {
                Text(
                    "Contraseña",
                    fontSize = TextoConfig.textoNormal
                )
            },
            placeholder = {
                Text(
                    "Ingresa tu contraseña",
                    fontSize = TextoConfig.pequeno
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedBorderColor = Color(0xFF4CAF50),
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Botones con iconos
        Column(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Botón de Ingresar
            Button(
                onClick = {authViewModel.clearLoginState()},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    Text("Cargando...")
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Login,
                            contentDescription = "Ingresar"
                        )
                        Text(
                            "  Ingresar",
                            fontSize = TextoConfig.boton
                        )
                    }
                }
            }

            // Botón Regístrate
            Button(
                onClick = { navController.navigate(Screen.NewUser.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isLoading
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Registrarse"
                    )
                    Text(
                        "  Regístrate",
                        fontSize = TextoConfig.boton
                    )
                }
            }

            // Botón Volver al Inicio
            Button(
                onClick = { navController.navigate(Screen.Main.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isLoading
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Volver al inicio"
                    )
                    Text(
                        "  Volver al Inicio",
                        fontSize = TextoConfig.boton
                    )
                }
            }
        }
    }
}