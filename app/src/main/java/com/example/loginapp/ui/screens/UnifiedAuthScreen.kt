package com.example.loginapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginapp.data.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UnifiedAuthScreen(
    repo: UserRepository,
    onAuthSuccess: (String, Boolean) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var userChecked by remember { mutableStateOf(false) }
    var userExists by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔐",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (!userChecked) "Bienvenido"
            else if (userExists) "¡Hola de nuevo!"
            else "Crear nueva cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (!userChecked) "Ingresa tu usuario para continuar"
            else if (userExists) "Ingresa tu contraseña"
            else "Crea tu contraseña",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                if (userChecked) {
                    userChecked = false
                    password = ""
                    confirmPassword = ""
                    error = ""
                }
            },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading && !userChecked
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!userChecked) {
            Button(
                onClick = {
                    if (username.isEmpty()) {
                        error = "Ingresa un usuario"
                    } else {
                        error = ""
                        isLoading = true
                        CoroutineScope(Dispatchers.IO).launch {
                            val exists = repo.userExists(username)
                            withContext(Dispatchers.Main) {
                                isLoading = false
                                userExists = exists
                                userChecked = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Continuar", fontSize = 16.sp)
                }
            }
        }

        AnimatedVisibility(
            visible = userChecked,
            enter = fadeIn(animationSpec = tween(500)) + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!userExists) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        if (userExists) {
                            // INICIAR SESIÓN
                            if (password.isEmpty()) {
                                error = "Ingresa tu contraseña"
                            } else {
                                error = ""
                                isLoading = true
                                CoroutineScope(Dispatchers.IO).launch {
                                    val success = repo.login(username, password)
                                    withContext(Dispatchers.Main) {
                                        isLoading = false
                                        if (success) {
                                            onAuthSuccess(username, false)
                                        } else {
                                            error = "Contraseña incorrecta"
                                        }
                                    }
                                }
                            }
                        } else {
                            // CREAR CUENTA
                            when {
                                password.isEmpty() -> error = "Ingresa una contraseña"
                                password != confirmPassword -> error = "Las contraseñas no coinciden"
                                password.length < 4 -> error = "Mínimo 4 caracteres"
                                else -> {
                                    error = ""
                                    isLoading = true
                                    CoroutineScope(Dispatchers.IO).launch {
                                        repo.register(username, password)
                                        withContext(Dispatchers.Main) {
                                            isLoading = false
                                            onAuthSuccess(username, true)
                                        }
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading,
                    colors = if (userExists)
                        ButtonDefaults.buttonColors()
                    else
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            if (userExists) "Iniciar Sesión" else "Crear Cuenta",
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        userChecked = false
                        username = ""
                        password = ""
                        confirmPassword = ""
                        error = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Usar otro usuario")
                }
            }
        }

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}