package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private val USERNAME = "usuario123"
    private val PASSWORD = "contrasena123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLoggedIn = remember { mutableStateOf(false) }
            val currentScreen = remember { mutableStateOf("Main") }  // Estado para manejar las pantallas
            val isLoading = remember { mutableStateOf(false) }  // Estado para mostrar la carga

            // Comprobamos el estado de login y mostramos la pantalla correspondiente
            if (isLoggedIn.value) {
                when (currentScreen.value) {
                    "Main" -> MainScreen(
                        onNavigateToTarjeta = { currentScreen.value = "Tarjeta" },
                        onNavigateToRutas = { currentScreen.value = "Rutas" })
                    "Tarjeta" -> TarjetaScreen(onBack = { currentScreen.value = "Main" })
                    "Rutas" -> RutasScreen(onBack = { currentScreen.value = "Main" })
                }
            } else {
                LoginScreen(
                    isLoading = isLoading,
                    onLogin = { username, password ->
                        isLoading.value = true
                        // Simulamos un retraso en la validación (simulando backend)
                        android.os.Handler().postDelayed({
                            if (username == USERNAME && password == PASSWORD) {
                                isLoggedIn.value = true
                            } else {
                                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                            }
                            isLoading.value = false
                        }, 2000)  // 2 segundos de espera
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(isLoading: MutableState<Boolean>, onLogin: (String, String) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
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
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Campo de contraseña
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Si estamos cargando, mostramos un indicador de progreso
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
            }

            // Botón de login
            Button(
                onClick = {
                    if (username.value.isBlank() || password.value.isBlank()) {
                        Toast.makeText(context, "Por favor ingresa usuario y contraseña", Toast.LENGTH_SHORT).show()
                    } else {
                        onLogin(username.value, password.value)
                    }
                },
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun MainScreen(onNavigateToTarjeta: () -> Unit, onNavigateToRutas: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a la Pantalla Principal",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botón de "Tarjeta"
            Button(
                onClick = { onNavigateToTarjeta() },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Tarjeta")
            }

            // Botón de "Rutas"
            Button(
                onClick = { onNavigateToRutas() },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Rutas")
            }
        }
    }
}

@Composable
fun TarjetaScreen(onBack: () -> Unit) {
    val saldo = remember { mutableStateOf(50) } // Simulando el saldo en la tarjeta
    val totalGastado = remember { mutableStateOf(20) } // Simulando el total gastado en la tarjeta
    val context = LocalContext.current  // Usamos LocalContext.current dentro del composable

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pantalla de Tarjeta",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Mostrar saldo de la tarjeta
            Text(
                text = "Saldo: $${saldo.value}",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Mostrar total gastado
            Text(
                text = "Total gastado: $${totalGastado.value}",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botón para recargar dinero
            Button(
                onClick = {
                    saldo.value += 20 // Simulamos agregar 20 al saldo
                    Toast.makeText(context, "Recarga realizada", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Recargar dinero")
            }

            // Botón para realizar gasto (si se desea controlar gastos)
            Button(
                onClick = {
                    // Simulamos un gasto de 10, si el saldo lo permite
                    if (saldo.value >= 10) {
                        saldo.value -= 10
                        totalGastado.value += 10
                        Toast.makeText(context, "Gasto realizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Realizar gasto de $10")
            }

            // Spacer para separar los botones
            Spacer(modifier = Modifier.height(16.dp))

            // Botón de regresar a la pantalla principal
            Button(onClick = { onBack() }) {
                Text("Regresar")
            }
        }
    }
}


@Composable
fun RutasScreen(onBack: () -> Unit) {
    val rutas = listOf("Ruta 1", "Ruta 2", "Ruta 3", "Ruta 4")
    val context = LocalContext.current  // Aquí obtenemos el contexto correctamente

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lista de Rutas",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Lista de rutas disponibles
            rutas.forEach { ruta ->
                Button(
                    onClick = {
                        // Colocamos el código Toast dentro de la lambda del onClick
                        Toast.makeText(context, "Ruta seleccionada: $ruta", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(ruta)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de regresar a la pantalla principal
            Button(onClick = { onBack() }) {
                Text("Regresar")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    MainScreen(onNavigateToTarjeta = {}, onNavigateToRutas = {})
}
