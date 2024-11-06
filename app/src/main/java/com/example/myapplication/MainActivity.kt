package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private val USERNAME = "usuario123"
    private val PASSWORD = "contrasena123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado para saber si el usuario ha iniciado sesión
            val isLoggedIn = remember { mutableStateOf(false) }
            val currentScreen = remember { mutableStateOf("Main") }  // Estado para manejar las pantallas

            // Comprobamos el estado de login y mostramos la pantalla correspondiente
            if (isLoggedIn.value) {
                when (currentScreen.value) {
                    "Main" -> MainScreen(onNavigateToTarjeta = { currentScreen.value = "Tarjeta" }, onNavigateToRutas = { currentScreen.value = "Rutas" })
                    "Tarjeta" -> TarjetaScreen(onBack = { currentScreen.value = "Main" })
                    "Rutas" -> RutasScreen(onBack = { currentScreen.value = "Main" })
                }
            } else {
                LoginScreen(onLogin = { username, password ->
                    // Comprobamos las credenciales
                    if (username == USERNAME && password == PASSWORD) {
                        isLoggedIn.value = true
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Título con el nombre del proyecto
        Text(
            text = "Way-Go",
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de usuario
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Campo de contraseña
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón de login
        Button(onClick = { onLogin(username.value, password.value) }) {
            Text("Login")
        }
    }
}

@Composable
fun MainScreen(onNavigateToTarjeta: () -> Unit, onNavigateToRutas: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bienvenido a la Pantalla Principal",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón de "Tarjeta"
        Button(
            onClick = { onNavigateToTarjeta() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Tarjeta")
        }

        // Botón de "Rutas"
        Button(
            onClick = { onNavigateToRutas() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Rutas")
        }
    }
}

@Composable
fun TarjetaScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Pantalla de Tarjeta",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón de regresar a la pantalla principal
        Button(onClick = { onBack() }) {
            Text("Regresar")
        }
    }
}

@Composable
fun RutasScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Pantalla de Rutas",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Botón de regresar a la pantalla principal
        Button(onClick = { onBack() }) {
            Text("Regresar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainScreen(onNavigateToTarjeta = {}, onNavigateToRutas = {})
}
