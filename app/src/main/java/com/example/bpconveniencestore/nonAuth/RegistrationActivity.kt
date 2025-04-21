package com.example.bpconveniencestore.nonAuth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences

@Composable
fun RegistrationScreen(
    navigation: NavHostController,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit,

    ) {
    val context = LocalContext.current
    val firebaseHelper = remember { FirebaseHelper() }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = onBackToLogin) {
            Text("← Back to Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Register", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                firebaseHelper.registerUser(
                    email = email,
                    password = password,
                    onSuccess = { user ->
                        Toast.makeText(context, "Registered: ${user.email}", Toast.LENGTH_SHORT).show()
                        firebaseHelper.uploadUserProfile(
                            user.uid,
                            name,
                            email ,
                            password,"Customer",
                            onSuccess = {
                                UserPreferences.saveUserData(name, email, password,"Customer")
                                println("Name: $name, Email: $email")
                                onRegisterSuccess()
                                Toast.makeText(context, "Dummy profile uploaded", Toast.LENGTH_SHORT).show()
                            },
                            onError = {
                                Toast.makeText(context, "Upload error: $it", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    onError = {
                        Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }) {
            Text("Register")
        }
    }
}
