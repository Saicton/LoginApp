package com.example.loginapp.ui

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
import com.example.loginapp.data.UserRepository
import com.example.loginapp.datastore.PrefsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
 
@Composable
fun RegisterScreen(
    repo: UserRepository,
    prefs: PrefsDataStore,
    onRegistered: () -> Unit
) {
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {

        Text("Crear contraseña", fontSize = 22.sp)

        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") }
        )

        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirmar") }
        )

        Button(onClick = {
            if (pass == confirm && pass.isNotEmpty()) {
                error = ""
                CoroutineScope(Dispatchers.IO).launch {
                    repo.register(pass)
                    prefs.setFirstRunFalse()
                    withContext(Dispatchers.Main) { onRegistered() }
                }
            } else {
                error = "Las contraseñas no coinciden"
            }
        }) {
            Text("Registrar")
        }

        Text(error, color = Color.Red)
    }
}

