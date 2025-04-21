package com.example.bpconveniencestore.Product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Product.Model.Product
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences

@Composable
fun ProductCard(product: Product, loadMoreProducts: () -> Unit){
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            ) {
                Icon(
                    Icons.Default.ShoppingCart, contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text("£${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(product.description, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(8.dp))

                if (UserPreferences.getUserData().usertype == "admin") {
                    if (product.quantity == 0) {
                        Button(onClick = {
                            FirebaseHelper().updateProductQuantity(product, 10)
                            loadMoreProducts()
                        }) {
                            Text("Update Quantity")
                        }
                    }
                } else {
                    if (product.quantity != 0) {
                        Button(onClick = { /* Add to cart or buy */ }) {
                            Text("Buy")
                        }
                    } else {
                        Text("Not Available", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
