package com.example.bpconveniencestore.Product.Model


data class Product(
    val id: String="",
    val name: String="",
    val price: Double=0.0,
    val description: String="",
    val imageUrl: String="",
    val quantity: Int=0
)

val sampleProducts = listOf(
    Product("","Coca-Cola", 1.50, "Cold and refreshing.", "", 0),
    Product("","Bread Loaf", 2.00, "Freshly baked daily.", "", 10),
    Product("","Rice - 5kg", 12.99, "Premium Basmati Rice.", "", 10),
    Product("","Eggs - 12 pack", 3.49, "Farm fresh eggs.", "", 10),

)
