package com.example.android_mini_store.ui.theme.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.android_mini_store.Screen
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme

// 🆕 IMPORT PARA TOAST
import android.widget.Toast

// ICONOS OFFLINE
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd

// CONFIGURACIÓN DE TEXTO
import com.example.android_mini_store.config.TextoConfig

import com.example.android_mini_store.ui.auth.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    // 🆕 CORRECCIÓN: Especificar explícitamente el tipo del ViewModel
    val viewModel: LoginViewModel = viewModel<LoginViewModel>()

    // 🆕 AGREGADO: Contexto para Toast
    val context = LocalContext.current

    // ✅ Estado del login desde Room
    val loginState by authViewModel.loginState.collectAsState()

    // 🆕 CORRECCIÓN: Manejar el resultado del login CON TOAST
    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthViewModel.LoginState.Success -> {
                // 🆕 Login exitoso - navegar a pantalla CLIENTES
                navController.navigate(Screen.Cliente.route) {  // 🆕 CAMBIADO A Screen.Cliente.route
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
            is AuthViewModel.LoginState.Error -> {
                // 🆕 Mostrar mensaje específico de error CON TOAST
                val errorMessage = (loginState as AuthViewModel.LoginState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                authViewModel.clearLoginState()
            }
            else -> {}
        }
    }

    Android_mini_storeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            // 🆕 PASAMOS EL CONTEXT AL CONTENT
            LoginContent(navController, viewModel, authViewModel, context)
        }
    }
}

@Composable
fun LoginContent(
    navController: NavHostController,
    viewModel: LoginViewModel,
    authViewModel: AuthViewModel,
    context: android.content.Context // 🆕 AGREGADO: Context para Toast
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

        // Campo email/RUT
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                onClick = {
                    // 🆕 CORRECCIÓN: Validar campos vacíos con TOAST
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_LONG).show()
                    } else {
                        authViewModel.login(email, password)
                    }
                },
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            " Verificando...",
                            fontSize = TextoConfig.boton,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Login,
                            contentDescription = "Ingresar"
                        )
                        Text(
                            "  Ingresar",
                            fontSize = TextoConfig.boton,
                            modifier = Modifier.padding(start = 8.dp)
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
                        fontSize = TextoConfig.boton,
                        modifier = Modifier.padding(start = 8.dp)
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
                        fontSize = TextoConfig.boton,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}