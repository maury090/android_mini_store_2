package com.example.android_mini_store.ui.theme.singIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme

@Composable
fun newUserScreen(navController: NavHostController) {
    Android_mini_storeTheme {
        SignInContent(navController)
    }
}

@Composable
fun SignInContent(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    // ESTADOS PARA ERRORES
    var nombreError by remember { mutableStateOf("") }
    var apellidoError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var rutError by remember { mutableStateOf("") }
    var direccionError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registro de nuevo usuario",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 18.dp)
        )

        // CAMPO NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = isValidNombre(it).message
            },
            label = { Text("Ingresa tu nombre") },
            placeholder = { Text("Pedro") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Gray
            ),
            isError = nombreError.isNotEmpty() && !isValidNombre(nombre).isValid,
            supportingText = {
                if (nombreError.isNotEmpty()) {
                    Text(
                        text = nombreError,
                        color = if (isValidNombre(nombre).isValid) Color(0xFF4CAF50) else Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // CAMPO APELLIDO
        OutlinedTextField(
            value = apellido,
            onValueChange = {
                apellido = it
                apellidoError = isValidApellido(it).message
            },
            label = { Text("Ingresa tu apellido") },
            placeholder = { Text("Picapiedra") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Gray
            ),
            isError = apellidoError.isNotEmpty() && !isValidApellido(apellido).isValid,
            supportingText = {
                if (apellidoError.isNotEmpty()) {
                    Text(
                        text = apellidoError,
                        color = if (isValidApellido(apellido).isValid) Color(0xFF4CAF50) else Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // CAMPO EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = isValidEmail(it).message
            },
            label = { Text("Ingresa tu correo") },
            placeholder = { Text("algo@correo.com") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Gray
            ),
            isError = emailError.isNotEmpty() && !isValidEmail(email).isValid,
            supportingText = {
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = if (isValidEmail(email).isValid) Color(0xFF4CAF50) else Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // CAMPO DIRECCIÓN
        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                direccionError = isValidDireccion(it).message
            },
            label = { Text("Ingresa tu dirección") },
            placeholder = { Text("Av. SiempreViva 742") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Gray
            ),
            isError = direccionError.isNotEmpty() && !isValidDireccion(direccion).isValid,
            supportingText = {
                if (direccionError.isNotEmpty()) {
                    Text(
                        text = direccionError,
                        color = if (isValidDireccion(direccion).isValid) Color(0xFF4CAF50) else Color.Red
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // CAMPO RUT
        OutlinedTextField(
            value = rut,
            onValueChange = { nuevoValor ->
                val textoFiltrado = nuevoValor.filter {
                    it.isDigit() || it == 'K' || it == 'k'
                }.uppercase()

                rut = textoFiltrado
                rutError = isValidRUT(rut).message
            },
            label = { Text("Ingresa tu RUT") },
            placeholder = { Text("123456789") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Gray
            ),
            isError = rutError.isNotEmpty() && !isValidRUT(rut).isValid,
            supportingText = {
                if (rutError.isNotEmpty()) {
                    Text(
                        text = rutError,
                        color = if (isValidRUT(rut).isValid) Color(0xFF4CAF50) else Color.Red
                    )
                } else {
                    Text(
                        text = "RUT entre 8-9 dígitos. Ej: 12345678 o 123456789",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // -------------------------------
        // BOTONES CON ICONOS
        // -------------------------------
        Column(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // BOTÓN REGISTRARSE (Done)
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Icono registrar"
                    )
                    Text(
                        text = "Registrarse",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // BOTÓN VOLVER (ArrowBack)
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Icono volver"
                    )
                    Text(
                        text = "Volver",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
