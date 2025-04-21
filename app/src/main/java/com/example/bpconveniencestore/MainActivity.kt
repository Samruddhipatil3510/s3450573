package com.example.bpconveniencestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.bpconveniencestore.Firebase.FirebaseHelper
import com.example.bpconveniencestore.Navigations.AppNavigation
import com.example.bpconveniencestore.Product.Model.Product
import com.example.bpconveniencestore.Product.Model.sampleProducts
import com.example.bpconveniencestore.Sharedprefrencespackage.UserPreferences
import com.example.bpconveniencestore.ui.theme.BpConveniencestoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserPreferences.init(this)
        val firebaseHelper =  FirebaseHelper()
        firebaseHelper.storeProducts(sampleProducts)
        enableEdgeToEdge()
        setContent {
            BpConveniencestoreTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)

            }
        }
    }
}
