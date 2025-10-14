package com.mybarber.navigation

import OtpScreen
import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.mybarber.View.CreateProfileScreen
import com.mybarber.View.dashboard.MainScreen
import kotlinx.serialization.Serializable

// Define your screens using @Serializable instead of sealed + route string
sealed interface Screen {

    @Serializable
    object Signup : Screen

    @Serializable
    object CreateProfile : Screen

    @Serializable
    data class OtpScreen(val mobileNumber: String) : Screen

    @Serializable
    object Dashboard : Screen

}



@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard
        //startDestination = Screen.Signup
    ) {
        // Signup Screen
        composable<Screen.Signup> {

            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { phoneNO -> navController.navigate(Screen.OtpScreen(phoneNO),) }
            )
        }

        // Create Profile Screen
        composable<Screen.CreateProfile> {
            CreateProfileScreen(
                onContinue = {
                    //navController.navigate(Screen.NextScreen())
                }
            )
        }

        composable<Screen.OtpScreen> { backStackEntry ->
            val args : Screen.OtpScreen = backStackEntry.toRoute()
            OtpScreen(
                mobileNumber = args.mobileNumber,
                onBack = {
                    navController.popBackStack()
                },
                onContinue = {
                    navController.navigate(route = Screen.CreateProfile)
                }
            )
        }

        composable<Screen.Dashboard>{
            MainScreen()
        }


    }

//    @Composable
//    NavHostBottom(navController, startDestination = "search") {
//        composable("search") { MainScreen(navController) }
//        composable("appointments") { /* AppointmentScreen() */ }
//        composable("profile") { /* ProfileScreen() */ }
//    }

}
