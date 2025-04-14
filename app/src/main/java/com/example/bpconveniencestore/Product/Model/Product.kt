package com.example.bpconveniencestore.Product.Model


data class Product(
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String
)

val sampleProducts = listOf(
    Product("Coca-Cola", 1.50, "Cold and refreshing.", ""),
    Product("Bread Loaf", 2.00, "Freshly baked daily.", ""),
    Product("Rice - 5kg", 12.99, "Premium Basmati Rice.", ""),
    Product("Eggs - 12 pack", 3.49, "Farm fresh eggs.", "")
)
