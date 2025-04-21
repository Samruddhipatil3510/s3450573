package com.example.bpconveniencestore.nonAuth

import android.widget.Toast
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
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Firebase.Firebase_auth
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val authService = Firebase_auth()
    var email by remember { mutableStateOf("adil@gmail.com") }
    var password by remember { mutableStateOf("Adil123") }
    var loading by remember { mutableStateOf(false) }
    val firebaseHelper = remember { FirebaseHelper() }

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

                    authService.signInWithEmail(email, password) { task ->
                        if (task.isSuccessful) {
                            val user = task.result?.user

                            if (user != null) {
                                firebaseHelper.getUserByEmail(user.email.toString()) { userData ->
                                    if (userData != null) {
                                        val name = userData["name"].toString()
                                        val email = userData["email"].toString()
                                        val password = userData["password"].toString()
                                        val usertype=userData["usertype"].toString()// if you store this (not recommended)
                                        UserPreferences.saveUserData(name, email, password,usertype)
                                        println("Name: $name, Email: $email")
                                    } else {
                                        println("No user found with that email.")
                                    }
                                }
                            }
                            onLoginSuccess()
                            loading=false
                            println("Login successful: ${user?.email}")
                        } else {
                            loading=false
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
        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Sign Up")
        }
    }
}
