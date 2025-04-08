package com.example.bpconveniencestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class Firebase_auth {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Get current user
    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    // Sign in with email and password
    fun signInWithEmail(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task)
            }
    }

    // Register with email and password
    fun registerWithEmail(
        email: String,
        password: String,
        callback: (Task<AuthResult>) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task)
            }
    }

    // Sign out
    fun signOut() {
        firebaseAuth.signOut()
    }
}
