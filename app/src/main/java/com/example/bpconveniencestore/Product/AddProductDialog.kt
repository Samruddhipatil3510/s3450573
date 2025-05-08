package com.example.bpconveniencestore.Product

import androidx.compose.foundation.text.KeyboardOptions

//package com.example.bpconveniencestore.Product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.bpconveniencestore.Product.Model.Product
import java.util.*

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAddProduct: (Product) -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add New Product") },
        text = {
            Column {
                // Input fields for product name, price, description, and quantity
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = productDescription,
                    onValueChange = { productDescription = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = productQuantity,
                    onValueChange = { productQuantity = it },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Validate inputs
                    if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty() && productQuantity.isNotEmpty()) {
                        val newProduct = Product(
                            id = UUID.randomUUID().toString(),
                            name = productName,
                            price = productPrice.toDouble(),
                            description = productDescription,
                            quantity = productQuantity.toInt()
                        )
                        // Add the product (e.g., send it to Firebase or local database)
                        onAddProduct(newProduct)
                    }
                }
            ) {
                Text("Add Product")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
