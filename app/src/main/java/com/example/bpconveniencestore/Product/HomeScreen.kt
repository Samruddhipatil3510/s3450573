package com.example.bpconveniencestore.Product

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Product.Model.AddProductDialog
import com.example.bpconveniencestore.Product.Model.Product
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences
import kotlinx.coroutines.launch
import com.google.firebase.firestore.DocumentSnapshot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, navigateToCart: () -> Unit) {
    val user = UserPreferences.getUserData()
    val products = remember { mutableStateListOf<Product>() }
    var loading by remember { mutableStateOf(false) }
    var lastVisible: DocumentSnapshot? by remember { mutableStateOf(null) }
    var endReached by remember { mutableStateOf(false) }

    val firebaseHelper = FirebaseHelper()

    // Coroutine scope for async fetching
    val coroutineScope = rememberCoroutineScope()

    // State for showing the Add Product Dialog (Admin only)
    var showAddProductDialog by remember { mutableStateOf(false) }

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
        endReached = false
        lastVisible = null
        products.clear()
        loadMoreProducts()
    }

    val fabContent: @Composable (() -> Unit) =
        if (user.usertype == "admin") {
            {
                // Admin's FAB to add a new product
                FloatingActionButton(onClick = { showAddProductDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Product"
                    )
                }
            }
        } else {
            {
                // Customer's FAB to go to the cart
                FloatingActionButton(onClick = { navigateToCart() }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Go to Cart"
                    )
                }
            }
        }

    // Add Product Dialog (Admin only)
    if (showAddProductDialog) {
        AddProductDialog(
            onDismiss = { showAddProductDialog = false },
            onAddProduct = { product ->
                firebaseHelper.addProduct(product) // Add product to Firebase
                products.add(0, product) // Add product to the list locally
                showAddProductDialog = false // Close the dialog after adding the product
            }
        )
    }

    Scaffold(
        floatingActionButton = fabContent,
        topBar = {
            TopAppBar(title = { Text("BP Convenience Store") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn to display products
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products.size) { product ->
                    ProductCard(
                        product = products[product],
                        loadMoreProducts = { refreshProducts() },
                        onAddToCart = { selectedProduct ->
                            CartManager.addItem(selectedProduct)
                        })
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Load more when scrolled to the bottom
                item {
                    when {
                        loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        !endReached -> loadMoreProducts()
                        else -> Spacer(modifier = Modifier.height(16.dp)) // No more items
                    }
                }
            }
        }
    }
}
