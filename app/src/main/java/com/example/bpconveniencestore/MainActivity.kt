package com.example.bpconveniencestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.bpconveniencestore.ui.theme.BpConveniencestoreTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BpConveniencestoreTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("home") { HomeScreen() }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login to B & P Convenience Store", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    loading = true
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            loading = false
                            if (task.isSuccessful) {
                                navController.navigate("home")
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Login Failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            ) {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { /* TODO: Navigate to SignUp Screen */ }) {
            Text("Don't have an account? Sign Up")
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to B & P Convenience Store!", style = MaterialTheme.typography.headlineSmall)
    }
}
