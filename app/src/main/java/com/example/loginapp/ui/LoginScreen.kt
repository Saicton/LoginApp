package com.xx.loginapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xx.loginapp.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
 
@Composable
fun LoginScreen(
    repo: UserRepository,
    onLoginOk: () -> Unit
) {
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {

        Text("Iniciar sesión", fontSize = 22.sp)

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") }
        )

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val ok = repo.login(pass)
                withContext(Dispatchers.Main) {
                    if (ok) onLoginOk()
                    else error = "Contraseña incorrecta"
                }
            }
        }) {
            Text("Entrar")
        }

        Text(error, color = Color.Red)
    }
}
