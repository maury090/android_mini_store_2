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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row


// ICONOS OFFLINE
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

// Navegación
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_mini_store.ui.theme.login.LoginScreen
import com.example.android_mini_store.ui.theme.singIn.newUserScreen
import com.example.android_mini_store.ui.theme.productos.ProductosScreen
import com.example.android_mini_store.ui.theme.opciones.OpcionesScreen
import com.example.android_mini_store.ui.theme.opciones.TextoAppScreen

// ✅ NUEVO IMPORT: DataStore
import com.example.android_mini_store.data.PreferencesManager

// Pantalla de carga
import androidx.compose.material3.CircularProgressIndicator
import kotlinx.coroutines.delay

// Dialogo cierre app
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

// Rutas
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Products : Screen("products")
    object Login : Screen("login")
    object NewUser : Screen("newUser")
    object Loading : Screen("loading")
    object Opciones : Screen("opciones")
    object TextoApp : Screen("textoApp")
}

class MainActivity : ComponentActivity() {
    // ✅ NUEVA VARIABLE: PreferencesManager
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ NUEVA INICIALIZACIÓN: DataStore
        preferencesManager = PreferencesManager(this)

        setContent {
            Android_mini_storeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFBE10E))
                ) {
                    // ✅ MODIFICADO: Pasar preferencesManager a AppNavigation
                    AppNavigation(preferencesManager = preferencesManager)
                }
            }
        }
    }
}

// ✅ MODIFICADO: Agregar parámetro preferencesManager
@Composable
fun AppNavigation(preferencesManager: PreferencesManager) {
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
        composable(Screen.Products.route) {
            ProductosScreen(navController = navController)
        }
        composable(Screen.Opciones.route) {
            OpcionesScreen(navController = navController)
        }
        // ✅ MODIFICADO: Pasar preferencesManager a TextoAppScreen
        composable(Screen.TextoApp.route) {
            TextoAppScreen(
                navController = navController,
                preferencesManager = preferencesManager
            )
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

        Spacer(modifier = Modifier.height(60.dp))

        ButtonsVertical(navController = navController)

        Spacer(modifier = Modifier.height(32.dp))

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
    }
}

@Composable
fun ButtonsVertical(navController: NavHostController) {

    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Salir de la aplicación") },
            text = { Text("¿Estás seguro de que quieres salir de EKONO?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        (context as? Activity)?.finishAffinity()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Sí, salir")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showExitDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ▶ Ver productos
        Button(
            onClick = { navController.navigate(Screen.Products.route) },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Productos")
                Spacer(modifier = Modifier.size(8.dp))
                Text("Ver productos")
            }
        }

        // ▶ Iniciar sesión
        Button(
            onClick = { navController.navigate(Screen.Loading.route) },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Lock, contentDescription = "Login")
                Spacer(Modifier.size(8.dp))
                Text("Iniciar sesión")
            }
        }

        // ▶ Opciones
        Button(
            onClick = { navController.navigate(Screen.Opciones.route) },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Build, contentDescription = "Opciones")
                Spacer(Modifier.size(8.dp))
                Text("Opciones")
            }
        }

        // ▶ Salir
        Button(
            onClick = { showExitDialog = true },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0000),
                contentColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                Spacer(Modifier.size(8.dp))
                Text("Salir de la APP")
            }
        }
    }
}

@Composable
fun LoadingScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Loading.route) { inclusive = true }
        }
    }

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