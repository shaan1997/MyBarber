package com.mybarber.navigation

import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mybarber.View.CreateProfileScreen

// Centralized screen routes
sealed class Screen(val route: String) {
    object Signup : Screen("signup")
    object CreateProfile : Screen("create_profile")
    object NextScreen : Screen("next_screen/{userName}") {
        fun createRoute(userName: String) = "next_screen/$userName"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Signup.route
    ) {
        // Signup Screen
        composable(Screen.Signup.route) {
            SignUpScreen(
                navController = navController,
                onBackClick = TODO(),
                onContinueClick = TODO(),
                modifier = TODO()
            )
        }

        // Create Profile Screen
        composable(Screen.CreateProfile.route) {
            CreateProfileScreen(
                onContinue = TODO(),
                navController = navController
            )
        }

        // Next Screen with argument
        composable(
            route = Screen.NextScreen.route,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
           // NextScreen(navController = navController, userName = userName)
        }
    }
}
