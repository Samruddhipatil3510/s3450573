package com.example.bpconveniencestore.Cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bpconveniencestore.Product.CartManager
import com.example.bpconveniencestore.Product.Model.Product

@Composable
fun CartScreen(cartItems: SnapshotStateList<Product>, cartManager: CartManager) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            Text("Your cart is empty.")
        } else {
            LazyColumn {
                items(cartItems.size) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = cartItems[product].name)
                                Text(text = "£${cartItems[product].price}")
                            }
                            Button(onClick = {
                                cartManager.removeItem(cartItems[product]) // ✅ Using CartManager now
                            }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total: £${cartItems.sumOf { it.price }}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    CartManager.clearCart() // ✅ Clear the cart if needed (after checkout)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Cart")
            }
        }
    }
}
