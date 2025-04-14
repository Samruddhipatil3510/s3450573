package com.example.bpconveniencestore.Product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bpconveniencestore.Product.Model.sampleProducts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
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

            LazyColumn {
                items(sampleProducts.size) { index ->
                    ProductCard(product = sampleProducts[index])
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

