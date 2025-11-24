package com.example.loginapp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.loginapp.data.AppDatabase
import com.example.loginapp.data.UserRepository
import com.example.loginapp.datastore.PrefsDataStore
import com.example.loginapp.ui.ChangePasswordScreen
import com.example.loginapp.ui.RegisterScreen
import com.example.loginapp.ui.LoginScreen
import com.example.loginapp.ui.WelcomeScreen


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           UIPrincipal()
                }
            }
        }


@Composable
fun UIPrincipal(modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    val db = AppDatabase.get(contexto)
    val repo = UserRepository(db.userDao())
    val prefs = PrefsDataStore(contexto)
    val firstRun by prefs.isFirstRun.collectAsState(initial = true)

    var currentScreen by remember {
        mutableStateOf(if (firstRun) "register" else "login")
    }
    when (currentScreen) {
        "register" -> RegisterScreen(repo = repo, prefs = prefs,
            onRegistered = {currentScreen="Welcome"})
        "login" -> LoginScreen(repo = repo, onLoginOk = {currentScreen="welcome"})
        "welcome" -> WelcomeScreen(onChangePassword = {currentScreen= "changePass"})
        "changePass" -> ChangePasswordScreen(repo = repo, onChanged = {currentScreen="welcome"})
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UIPrincipal()
}