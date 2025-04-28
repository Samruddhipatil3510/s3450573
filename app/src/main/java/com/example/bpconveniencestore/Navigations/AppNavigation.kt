package com.example.bpconveniencestore.Navigations



import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bpconveniencestore.Cart.CartScreen
import com.example.bpconveniencestore.Product.CartManager
import com.example.bpconveniencestore.Product.HomeScreen
import com.example.bpconveniencestore.nonAuth.LoginScreen
import com.example.bpconveniencestore.nonAuth.RegistrationScreen

@Composable
fun AppNavigation(navController: NavHostController) {

//    val startDestination = if (currentUser != null) Route.Home else Route.Login
    val startDestination = Route.Login

    NavHost(navController = navController, startDestination = startDestination) {
        unauthenticatedNavRoutes(navController)
        authenticatedNavRoutes(navController)
    }


}

fun NavGraphBuilder.unauthenticatedNavRoutes(navController: NavHostController) {
    composable(Route.Login) {
        LoginScreen(
            navController,
            onLoginSuccess = {
                navController.navigate(Route.Home) {
                    popUpTo(Route.Login) { inclusive = true }
                }
            },
            onRegisterClick = {
                navController.navigate(Route.Register)
            }
        )
    }

    composable(Route.Register) {
        RegistrationScreen(
            navController,
            onRegisterSuccess = {
                navController.navigate(Route.Home) {
                    popUpTo(Route.Login) { inclusive = true }
                }
            },
            onBackToLogin = {
                navController.popBackStack()
            }
        )
    }
}



fun NavGraphBuilder.authenticatedNavRoutes(navController: NavHostController) {
    composable(Route.Home) {
        HomeScreen(navController,
            navigateToCart = {
                navController.navigate(Route.Cart)  // Navigate to Cart
            }

            )
    }

    composable(Route.Cart) {   // 👈 New Cart Route
        CartScreen(

            cartItems = CartManager.cartItems,
            cartManager = CartManager

        )
    }
}