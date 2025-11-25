package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.loginapp.data.AppDatabase
import com.example.loginapp.data.UserRepository
import com.example.loginapp.datastore.PrefsDataStore
import com.example.loginapp.ui.screens.ChangePasswordScreenSimple
import com.example.loginapp.ui.screens.SplashScreen
import com.example.loginapp.ui.screens.UnifiedAuthScreen
import com.example.loginapp.ui.screens.WelcomeScreen
import com.example.loginapp.ui.theme.LoginAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginAppTheme {
                UIPrincipal()
            }
        }
    }
}

@Composable
fun UIPrincipal() {
    val contexto = LocalContext.current
    val db = AppDatabase.get(contexto)
    val prefs = PrefsDataStore(contexto)
    val repo = UserRepository(db.userDao(), prefs)

    var currentScreen by remember { mutableStateOf("splash") }
    var currentUsername by remember { mutableStateOf("") }
    var isNewUser by remember { mutableStateOf(false) }

    when (currentScreen) {
        "splash" -> SplashScreen(
            onTimeout = {
                currentScreen = "auth"
            }
        )

        "auth" -> UnifiedAuthScreen(
            repo = repo,
            onAuthSuccess = { username, newUser ->
                currentUsername = username
                isNewUser = newUser
                currentScreen = "welcome"
            }
        )

        "welcome" -> WelcomeScreen(
            username = currentUsername,
            isNewUser = isNewUser,
            onChangePassword = { currentScreen = "changePass" },
            onLogout = {
                currentUsername = ""
                isNewUser = false
                currentScreen = "auth"
            }
        )

        "changePass" -> ChangePasswordScreenSimple(
            username = currentUsername,
            repo = repo,
            onChanged = {
                isNewUser = false
                currentScreen = "welcome"
            }
        )
    }
}