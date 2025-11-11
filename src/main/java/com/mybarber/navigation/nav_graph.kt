package com.mybarber.navigation

import AppointmentsScreen
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
import com.mybarber.View.dashboard.SearchContent
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
      //  startDestination = Screen.CreateProfile
        startDestination = Screen.Signup
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
                    navController.navigate(Screen.Dashboard)
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

}

@Composable
fun NavHostBottom(navController : NavHostController,  startDestination: String = "search") {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable("search") { SearchContent() }
        composable("appointments") {  AppointmentsScreen()  }
        composable("profile") { /* ProfileScreen() */ }
    }


}


