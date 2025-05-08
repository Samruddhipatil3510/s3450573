package com.example.bpconveniencestore.Navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bpconveniencestore.Cart.CartScreen
import com.example.bpconveniencestore.Cart.CartManager
import com.example.bpconveniencestore.MainPage.HomeScreen
import com.example.bpconveniencestore.nonAuth.LoginScreen
import com.example.bpconveniencestore.nonAuth.RegistrationScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    // Set the start destination.
    val startDestination = Route.Login

    // Main navigation host that handles navigation between screens.
    NavHost(navController = navController, startDestination = startDestination) {
        unauthenticatedNavRoutes(navController) // Routes for login/register screens
        authenticatedNavRoutes(navController)   // Routes for home/cart screens
    }
}

// Defines navigation routes for unauthenticated users (e.g., login and registration)
fun NavGraphBuilder.unauthenticatedNavRoutes(navController: NavHostController) {
    composable(Route.Login) {
        LoginScreen(
            navController,
            // Navigate to Home screen on successful login, removing Login from back stack
            onLoginSuccess = {
                navController.navigate(Route.Home) {
                    popUpTo(Route.Login) { inclusive = true }
                }
            },
            // Navigate to Register screen when user clicks register
            onRegisterClick = {
                navController.navigate(Route.Register)
            }
        )
    }

    composable(Route.Register) {
        RegistrationScreen(
            navController,
            // Navigate to Home screen on successful registration
            onRegisterSuccess = {
                navController.navigate(Route.Home) {
                    popUpTo(Route.Login) { inclusive = true }
                }
            },
            // Return to Login screen when user wants to go back
            onBackToLogin = {
                navController.popBackStack()
            }
        )
    }
}

// Defines navigation routes for authenticated users (e.g., home and cart)
fun NavGraphBuilder.authenticatedNavRoutes(navController: NavHostController) {
    composable(Route.Home) {
        HomeScreen(
            navController,
            // When user clicks cart icon/button, navigate to Cart screen
            navigateToCart = {
                navController.navigate(Route.Cart)
            }
        )
    }

    composable(Route.Cart) {
        CartScreen(
            cartItems = CartManager.cartItems, // Provide current cart items
            cartManager = CartManager          // Handle cart actions (add/remove)
        )
    }
}
