package com.example.bpconveniencestore.Cart

import androidx.compose.runtime.mutableStateListOf
import com.example.bpconveniencestore.Product.Model.Product

object CartManager {

    // Mutable list to store products added to the cart
    val cartItems = mutableStateListOf<Product>()

    // Function to add a product to the cart
    fun addItem(product: Product) {
        cartItems.add(product)
    }

    // Function to remove a product from the cart
    fun removeItem(product: Product) {
        cartItems.remove(product)
    }

    // Function to clear all items from the cart
    fun clearCart() {
        cartItems.clear()
    }

    // Optional: Get total number of items in cart
    fun getTotalItems(): Int {
        return cartItems.size
    }

    // Optional: Calculate total price of items in cart
    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price }
    }
}