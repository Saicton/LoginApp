package com.xx.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.xx.loginapp.ui.ChangePasswordScreen
import com.xx.loginapp.ui.LoginScreen
import com.xx.loginapp.ui.RegisterScreen
import com.xx.loginapp.ui.WelcomeScreen
import com.xx.loginapp.data.AppDatabase
import com.xx.loginapp.datastore.PrefsDataStore
import com.xx.loginapp.data.UserRepository

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
fun UIPrincipal () {
    
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UIPrincipal()
}