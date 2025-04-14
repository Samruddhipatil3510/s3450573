package com.example.bpconveniencestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bpconveniencestore.Firebase.Firebase_auth
import com.example.bpconveniencestore.Navigations.AppNavigation
import com.example.bpconveniencestore.ui.theme.BpConveniencestoreTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BpConveniencestoreTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
//                val navController = rememberNavController()
//                NavHost(navController = navController, startDestination = "login") {
//                    composable("login") { LoginScreen(navController) }
//                    composable("home") { HomeScreen() }
//
//                    composable("registerscreen") { RegistrationScreen() }

              //  }
            }
        }
    }
}


//@Composable
//fun HomeScreen() {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Welcome to B & P Convenience Store!", style = MaterialTheme.typography.headlineSmall)
//    }
//}
