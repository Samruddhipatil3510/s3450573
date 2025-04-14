package com.example.bpconveniencestore.Firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHelper(
    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val auth: Firebase_auth = Firebase_auth()

    // Wraps registration logic
    fun registerUser(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onError: (String) -> Unit
    ) {
        auth.registerWithEmail(email, password) { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    onSuccess(it)
                } ?: onError("User is null after registration")
            } else {
                onError(task.exception?.message ?: "Unknown error")
            }
        }
    }

    // Save user data (no image upload)
    fun uploadUserProfile(
        userId: String,
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {


        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password // ⚠️ Avoid storing passwords in plain text
        )

        db.collection("users").document(userId)
            .set(userMap)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError("Data save failed: ${it.message}")
            }
    }
}
