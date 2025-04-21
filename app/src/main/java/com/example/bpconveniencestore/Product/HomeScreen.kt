package com.example.bpconveniencestore.Product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Product.Model.Product
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val user = UserPreferences.getUserData()
    val products = remember { mutableStateListOf<Product>() }
    var loading by remember { mutableStateOf(false) }
    var lastVisible: DocumentSnapshot? by remember { mutableStateOf(null) }
    var endReached by remember { mutableStateOf(false) }


    val firebaseHelper = FirebaseHelper()

    // Coroutine scope for async fetching
    val coroutineScope = rememberCoroutineScope()

    // Function to load more products (pagination)
    fun loadMoreProducts() {
        if (loading || endReached) return

        loading = true
        coroutineScope.launch {
            val result = firebaseHelper.loadProducts(lastVisible)

            result.onSuccess { querySnapshot ->
                val newProducts = querySnapshot.documents.mapNotNull {
                    it.toObject(Product::class.java)
                }

                if (newProducts.isNotEmpty()) {
                    products.addAll(newProducts)
                    lastVisible = querySnapshot.documents.lastOrNull()
                } else {
                    endReached = true // No more products
                }
                loading = false
            }

            result.onFailure {
                loading = false
                println("Error fetching products: ${it.localizedMessage}")
            }
        }
    }

    fun refreshProducts() {
        endReached=false
        lastVisible = null
        products.clear()
        loadMoreProducts()
    }
    val fabContent: @Composable (() -> Unit) =
        if (user.usertype == "admin") {
            {
                ///Todo  add click for new page navigation Super Admin
                FloatingActionButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        } else {
            {
                ///Todo  add click for new page navigation Customers
                /*
                * New Page To show Customer Items
                *
                * */
                FloatingActionButton(onClick = { /* Handle click */ }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Add"
                    )
                }
            }
        }
    Scaffold(

        floatingActionButton = fabContent,
        topBar = {
            TopAppBar(
                title = { Text("BP Convenience Store") }
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // respect Scaffold's padding (for AppBar)
                .padding(16.dp)        // custom screen padding
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn displaying products
            // LazyColumn displaying products
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products.size) { product ->
                    ProductCard(product = products[product], loadMoreProducts = { refreshProducts() })
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Load more when scrolled to the bottom
                item {
                    when {
                        loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        !endReached -> loadMoreProducts()
                        else -> Spacer(modifier = Modifier.height(16.dp)) // or "No more items" text
                    }
                }

            }
        }
    }
}


