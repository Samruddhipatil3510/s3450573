package com.example.bpconveniencestore.Firebase

import android.util.Log
import com.example.bpconveniencestore.Product.Model.Product
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirebaseHelper(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    private val auth: Firebase_auth = Firebase_auth()

    // Wraps registration logic
    fun registerUser(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onError: (String) -> Unit,
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
        usertype: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {


        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "usertype" to usertype// ⚠️ Avoid storing passwords in plain text
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

    fun getUserByEmail(email: String, onResult: (Map<String, Any>?) -> Unit) {


        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userData = documents.documents[0].data
                    onResult(userData)
                } else {
                    onResult(null)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(null)
            }
    }

    fun storeProducts(products: List<Product>) {
        val db = FirebaseFirestore.getInstance()

        products.forEach { product ->
            db.collection("products")
                .whereEqualTo("name", product.name) // Use a unique field if available
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        val docRef = db.collection("products").document() // Generate document reference
                        val productWithId = product.copy(id = docRef.id)   // Copy product with ID

                        docRef.set(productWithId)
                            .addOnSuccessListener {
                                println("${product.name} uploaded successfully with ID: ${docRef.id}")
                            }
                            .addOnFailureListener { e ->
                                println("Failed to upload ${product.name}: ${e.message}")
                            }
                    } else {
                        println("Product ${product.name} already exists in the database.")
                    }
                }
                .addOnFailureListener { e ->
                    println("Failed to check for existing product: ${e.message}")
                }
        }
    }



    suspend fun loadProducts(lastVisible: DocumentSnapshot? = null): Result<QuerySnapshot> {
        return try {
            // Building the query based on pagination
            val query: Query = if (lastVisible == null) {
                db.collection("products").limit(10) // initial load
            } else {
                db.collection("products").startAfter(lastVisible).limit(10) // paginate
            }

            val querySnapshot = query.get().await()  // Firebase Firestore call using await
            Result.success(querySnapshot)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun updateProductQuantity(product: Product, newQuantity: Int) {
        val db = FirebaseFirestore.getInstance()

        db.collection("products")
            .document(product.id)
            .update("quantity", newQuantity)
            .addOnSuccessListener {
                println("Product quantity updated successfully.")
            }
            .addOnFailureListener { e ->
                println("Failed to update product quantity: ${e.message}")
            }
    }

    fun addProduct(product: Any) {

    }
    class FirebaseHelper {

        fun addProduct(product: Product) {
            val db = FirebaseFirestore.getInstance()
            val productRef = db.collection("products").document(product.id)
            productRef.set(product).addOnSuccessListener {
                Log.d("FirebaseHelper", "Product added successfully")
            }.addOnFailureListener {
                Log.e("FirebaseHelper", "Error adding product", it)
            }
        }

        fun loadProducts(lastVisible: DocumentSnapshot?) {
            // Implement loading products with pagination
        }
    }

}
