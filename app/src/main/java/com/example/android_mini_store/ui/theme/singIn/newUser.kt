package com.example.android_mini_store.ui.theme.singIn

import androidx.compose.material3.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme
import com.example.android_mini_store.config.TextoConfig
import com.example.android_mini_store.ui.auth.AuthViewModel
import androidx.compose.foundation.layout.size

@Composable
fun newUserScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val registerState by authViewModel.registerState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(registerState) {
        if (registerState is AuthViewModel.RegisterState.Success) {
            showSuccessDialog = true
        }
    }

    Android_mini_storeTheme {
        Scaffold(
            containerColor = Color(0xFFFBE10E)
        ) { paddingValues ->
            SignInContent(
                navController = navController,
                authViewModel = authViewModel,
                registerState = registerState,
                paddingValues = paddingValues
            )
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.navigate("login") {
                    popUpTo("newUser") { inclusive = true }
                }
                authViewModel.clearRegisterState()
            },
            title = {
                Text(text = "Registro Completado")
            },
            text = {
                Text("Usuario creado correctamente")
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("login") {
                            popUpTo("newUser") { inclusive = true }
                        }
                        authViewModel.clearRegisterState()
                    }
                ) {
                    Text("Iniciar Sesión")
                }
            }
        )
    }
}

@Composable
fun SignInContent(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    registerState: AuthViewModel.RegisterState,
    paddingValues: PaddingValues
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf("") }
    var apellidoError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var rutError by remember { mutableStateOf("") }
    var direccionError by remember { mutableStateOf("") }
    var contrasenaError by remember { mutableStateOf("") }
    var roomError by remember { mutableStateOf("") }

    // 🆕 CORRECCIÓN: Estado para controlar validaciones solo al enviar
    var shouldValidateAll by remember { mutableStateOf(false) }

    LaunchedEffect(registerState) {
        if (registerState is AuthViewModel.RegisterState.Error) {
            roomError = (registerState as AuthViewModel.RegisterState.Error).message
        }
    }

    val scrollState = rememberScrollState()
    val isLoading = registerState is AuthViewModel.RegisterState.Loading

    // 🆕 CORRECCIÓN: Función para validar todos los campos
    fun validateAllFields(): Boolean {
        val nombreValidation = isValidNombre(nombre)
        val apellidoValidation = isValidApellido(apellido)
        val emailValidation = isValidEmail(email)
        val direccionValidation = isValidDireccion(direccion)
        val rutValidation = isValidRUT(rut)
        val contrasenaValidation = isValidContrasena(contrasena)

        nombreError = if (!nombreValidation.isValid) nombreValidation.message else ""
        apellidoError = if (!apellidoValidation.isValid) apellidoValidation.message else ""
        emailError = if (!emailValidation.isValid) emailValidation.message else ""
        direccionError = if (!direccionValidation.isValid) direccionValidation.message else ""
        rutError = if (!rutValidation.isValid) rutValidation.message else ""
        contrasenaError = if (!contrasenaValidation.isValid) contrasenaValidation.message else ""

        return nombreValidation.isValid &&
                apellidoValidation.isValid &&
                emailValidation.isValid &&
                direccionValidation.isValid &&
                rutValidation.isValid &&
                contrasenaValidation.isValid
    }

    val DefaultTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedBorderColor = Color.Gray,
        unfocusedBorderColor = Color.Gray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 14.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Registro de nuevo usuario",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 18.dp),
            fontSize = TextoConfig.tituloPantalla
        )

        if (roomError.isNotEmpty()) {
            Text(
                text = roomError,
                color = Color.Red,
                fontSize = TextoConfig.textoNormal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // --- CAMPOS DE TEXTO CORREGIDOS ---

        // 🆕 CORRECCIÓN: CAMPO NOMBRE - Validación mejorada
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                // 🆕 Validación en tiempo real solo básica, completa al enviar
                nombreError = if (shouldValidateAll) {
                    val validation = isValidNombre(it)
                    if (!validation.isValid) validation.message else ""
                } else if (it.isNotEmpty() && it.length < 2) {
                    "Mínimo 2 caracteres"
                } else {
                    ""
                }
                roomError = ""
            },
            label = { Text("Ingresa tu nombre", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("Pedro", fontSize = TextoConfig.pequeno) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            colors = DefaultTextFieldColors,
            isError = nombreError.isNotEmpty(),
            supportingText = {
                if (nombreError.isNotEmpty()) {
                    Text(text = nombreError, color = Color.Red, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = !isLoading
        )

        // 🆕 CORRECCIÓN: CAMPO APELLIDO - Validación mejorada
        OutlinedTextField(
            value = apellido,
            onValueChange = {
                apellido = it
                // 🆕 Validación en tiempo real solo básica, completa al enviar
                apellidoError = if (shouldValidateAll) {
                    val validation = isValidApellido(it)
                    if (!validation.isValid) validation.message else ""
                } else if (it.isNotEmpty() && it.length < 2) {
                    "Mínimo 2 caracteres"
                } else {
                    ""
                }
                roomError = ""
            },
            label = { Text("Ingresa tu apellido", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("Picapiedra", fontSize = TextoConfig.pequeno) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            colors = DefaultTextFieldColors,
            isError = apellidoError.isNotEmpty(),
            supportingText = {
                if (apellidoError.isNotEmpty()) {
                    Text(text = apellidoError, color = Color.Red, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = !isLoading
        )

        // 🆕 CORRECCIÓN: CAMPO EMAIL - Validación mejorada
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                // 🆕 Validación en tiempo real solo básica, completa al enviar
                emailError = if (shouldValidateAll) {
                    val validation = isValidEmail(it)
                    if (!validation.isValid) validation.message else ""
                } else if (it.isNotEmpty() && !it.contains("@")) {
                    "Email debe contener @"
                } else {
                    ""
                }
                roomError = ""
            },
            label = { Text("Ingresa tu correo", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("algo@correo.com", fontSize = TextoConfig.pequeno) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            colors = DefaultTextFieldColors,
            isError = emailError.isNotEmpty(),
            supportingText = {
                if (emailError.isNotEmpty()) {
                    Text(text = emailError, color = Color.Red, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = !isLoading
        )

        // 🆕 CORRECCIÓN: CAMPO DIRECCIÓN - Validación mejorada
        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                // 🆕 Validación en tiempo real solo básica, completa al enviar
                direccionError = if (shouldValidateAll) {
                    val validation = isValidDireccion(it)
                    if (!validation.isValid) validation.message else ""
                } else if (it.isNotEmpty() && it.length < 5) {
                    "Mínimo 5 caracteres"
                } else {
                    ""
                }
                roomError = ""
            },
            label = { Text("Ingresa tu dirección", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("Av. SiempreViva 742", fontSize = TextoConfig.pequeno) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            colors = DefaultTextFieldColors,
            isError = direccionError.isNotEmpty(),
            supportingText = {
                if (direccionError.isNotEmpty()) {
                    Text(text = direccionError, color = Color.Red, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = !isLoading
        )

        // 🆕 CORRECCIÓN: CAMPO RUT - Validación mejorada
        OutlinedTextField(
            value = rut,
            onValueChange = { nuevoValor ->
                val textoFiltrado = nuevoValor.filter { it.isDigit() || it == 'K' || it == 'k' }.uppercase()
                rut = textoFiltrado
                // 🆕 Validación en tiempo real solo básica, completa al enviar
                rutError = if (shouldValidateAll) {
                    val validation = isValidRUT(textoFiltrado)
                    if (!validation.isValid) validation.message else ""
                } else if (textoFiltrado.isNotEmpty() && textoFiltrado.length < 8) {
                    "RUT debe tener 8-9 dígitos"
                } else {
                    ""
                }
                roomError = ""
            },
            label = { Text("Ingresa tu RUT", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("123456789", fontSize = TextoConfig.pequeno) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            colors = DefaultTextFieldColors,
            isError = rutError.isNotEmpty(),
            supportingText = {
                if (rutError.isNotEmpty()) {
                    Text(text = rutError, color = Color.Red, fontSize = TextoConfig.pequeno)
                } else {
                    Text(text = "RUT entre 8-9 dígitos", color = Color.Gray, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            enabled = !isLoading
        )

        // 🆕 CORRECCIÓN: CONTRASEÑA - Validación mejorada
        OutlinedTextField(
            value = contrasena,
            onValueChange = { nuevoValor ->
                if (nuevoValor.length <= 8) {
                    contrasena = nuevoValor
                    // 🆕 Validación en tiempo real solo básica, completa al enviar
                    contrasenaError = if (shouldValidateAll) {
                        val validation = isValidContrasena(nuevoValor)
                        if (!validation.isValid) validation.message else ""
                    } else if (nuevoValor.isNotEmpty() && nuevoValor.length < 4) {
                        "Mínimo 4 caracteres"
                    } else {
                        ""
                    }
                    roomError = ""
                }
            },
            label = { Text("Ingresa tu contraseña", fontSize = TextoConfig.textoNormal) },
            placeholder = { Text("m1c0ntr4s3n4", fontSize = TextoConfig.pequeno) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            colors = DefaultTextFieldColors,
            isError = contrasenaError.isNotEmpty(),
            supportingText = {
                if (contrasenaError.isNotEmpty()) {
                    Text(text = contrasenaError, color = Color.Red, fontSize = TextoConfig.pequeno)
                } else {
                    Text(text = "4-8 caracteres", color = Color.Gray, fontSize = TextoConfig.pequeno)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            enabled = !isLoading
        )

        // BOTONES
        Column(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 16.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 🆕 CORRECCIÓN: BOTÓN REGISTRARSE - Validación completa
            Button(
                onClick = {
                    shouldValidateAll = true // 🆕 Activar validaciones completas

                    // 🆕 Usar función de validación completa
                    val allFieldsValid = validateAllFields()

                    if (allFieldsValid) {
                        roomError = ""
                        authViewModel.registrarUsuario(
                            rut = rut,
                            nombre = nombre,
                            apellido = apellido,
                            correo = email,
                            direccion = direccion,
                            password = contrasena,
                            rol = "cliente"
                        )
                    } else {
                        roomError = "Corrija los errores en el formulario"
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Done, contentDescription = "Registrar")
                        Text("Registrarse", modifier = Modifier.padding(start = 8.dp), fontSize = TextoConfig.boton)
                    }
                }
            }

            // BOTÓN VOLVER
            Button(
                onClick = { if (!isLoading) navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                enabled = !isLoading
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    Text("Volver", modifier = Modifier.padding(start = 8.dp), fontSize = TextoConfig.boton)
                }
            }
        }
    }
}