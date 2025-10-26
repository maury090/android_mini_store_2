package com.example.android_mini_store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.android_mini_store.ui.theme.Android_mini_storeTheme
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
//import necesario para la navegacion entre vistas
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_mini_store.ui.theme.login.LoginScreen
import com.example.android_mini_store.ui.theme.singIn.newUserScreen

// IMPORT AGREGADO: Nueva pantalla de productos
import com.example.android_mini_store.ui.theme.productos.ProductosScreen

//import pantalla de carga
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.coroutines.delay

//rutas
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Products : Screen("products")
    object Login : Screen("login")
    object NewUser : Screen("newUser")
    object Loading : Screen("loading")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_mini_storeTheme {
                // FONDO PARA TODA LA APLICACIÓN
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFBE10E))
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreenWithWelcome(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Loading.route) {
            LoadingScreen(navController = navController)
        }
        composable(Screen.NewUser.route) {
            newUserScreen(navController = navController)
        }
        // NUEVA PANTALLA AGREGADA: Pantalla de productos
        composable(Screen.Products.route) {
            ProductosScreen()
        }
    }
}

@Composable
fun MainScreenWithWelcome(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mensaje de bienvenida
        Text(
            text = "¡Bienvenido a EKONO!",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Lugar de tu super descuento.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Espaciado mensaje de bienvenida y botones
        Spacer(modifier = Modifier.height(60.dp))

        // Botones
        ButtonsVertical(navController = navController)

        // Espaciado entre botones y el card
        Spacer(modifier = Modifier.height(32.dp))

        // CARD
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                // elementos alineados vertical
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Minimercados EKONO",
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )


                    Image(
                        painter = painterResource(id = R.drawable.ekono2),
                        contentDescription = "logo ekono",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ButtonsVertical(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // BOTÓN MODIFICADO: Ahora navega a la pantalla de productos
        Button(
            onClick = {
                navController.navigate(Screen.Products.route)
            },
            modifier = Modifier
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(text = "Ver productos")
        }

        Button(
            onClick = { navController.navigate(Screen.Loading.route) },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Iniciar sesión",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(text = "Opciones")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0000),
                contentColor = Color.White
            )
        ) {
            Text(text = "Salir de la APP")
        }
    }
}

//pantalla de carga
@Composable
fun LoadingScreen(navController: NavHostController) {
    // temporizador
    LaunchedEffect(Unit) {
        delay(1000) // tiempo establecido de pantalla de carga
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Loading.route) { inclusive = true }
        }
    }

    // ui de la pantalla de carga
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBE10E)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(90.dp),
            color = Color(0xFF4CAF50),
            strokeWidth = 4.dp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Cargando...",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}