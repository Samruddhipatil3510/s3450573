package com.example.bpconveniencestore.nonAuth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Firebase.Firebase_auth
import com.example.bpconveniencestore.R
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit,    // Callback to trigger after successful login
    onRegisterClick: () -> Unit    // Callback to navigate to registration screen
) {
    val authService = Firebase_auth()  // Firebase auth helper instance
    var email by remember { mutableStateOf("") }        // Email input state
    var password by remember { mutableStateOf("") }     // Password input state
    var loading by remember { mutableStateOf(false) }   // Loading spinner state
    val firebaseHelper = remember { FirebaseHelper() }  // Helper to fetch user data from Firestore

    // Layout for login screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // App logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(150.dp) // Set height of the logo image
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text("Login to B & P Convenience Store", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password input field
        OutlinedTextField(
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Loading spinner shown when logging in
        if (loading) {
            CircularProgressIndicator()
        } else {
            // Login button
            Button(
                onClick = {
                    loading = true  // Show spinner while processing login

                    // Firebase authentication
                    authService.signInWithEmail(email, password) { task ->
                        if (task.isSuccessful) {
                            val user = task.result?.user

                            if (user != null) {
                                // Fetch additional user data from Firestore
                                firebaseHelper.getUserByEmail(user.email.toString()) { userData ->
                                    if (userData != null) {
                                        val name = userData["name"].toString()
                                        val email = userData["email"].toString()
                                        val password = userData["password"].toString()
                                        val usertype = userData["usertype"].toString() // Caution: Avoid storing passwords in plaintext

                                        // Save user data locally (SharedPreferences or similar)
                                        UserPreferences.saveUserData(name, email, password, usertype)
                                        println("Name: $name, Email: $email")
                                    } else {
                                        println("No user found with that email.")
                                    }
                                }
                            }

                            // Navigate to home screen
                            onLoginSuccess()
                            loading = false
                            println("Login successful: ${user?.email}")
                        } else {
                            // Login failed - show error message
                            loading = false
                            Toast.makeText(
                                navController.context,
                                "Login Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            println("Login failed: ${task.exception?.message}")
                        }
                    }
                }
            ) {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to registration screen
        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Sign Up")
        }
    }
}
