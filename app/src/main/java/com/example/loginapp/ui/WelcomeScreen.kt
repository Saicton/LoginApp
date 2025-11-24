package com.xx.loginapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
 
@Composable
fun WelcomeScreen(onChangePassword: () -> Unit) {
    Column(Modifier.padding(20.dp)) {
        Text("Bienvenido!", fontSize = 26.sp)
        Button(onClick = onChangePassword) {
            Text("Cambiar contraseña")
        }
    }
}

