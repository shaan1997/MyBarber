package com.mybarber.navigation

import com.mybarber.View.dashboard.screens.AppointmentsScreen
import com.mybarber.View.dashboard.screens.BookingConfirmedScreen
import com.mybarber.View.dashboard.screens.BookingScreen
import com.mybarber.View.dashboard.screens.ProfileScreen
import com.mybarber.View.dashboard.screens.SelectBarberScreen
import com.mybarber.View.dashboard.screens.UpcomingAppointmentScreen
import OtpScreen
import SignUpScreen
import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.mybarber.View.BarberProfileScreen
import com.mybarber.View.CreateProfileScreen
import com.mybarber.View.dashboard.MainScreen
import com.mybarber.View.dashboard.SearchContent
import com.mybarber.model.Barber
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

sealed interface Screen {
    @Serializable object Signup : Screen
    @Serializable object CreateProfile : Screen
    @Serializable data class OtpScreen(val mobileNumber: String) : Screen
    @Serializable object Dashboard : Screen
    @Serializable object Search : Screen
    @Serializable object Appointments : Screen
    @Serializable object Profile : Screen
    @Serializable data class BarberProfile(val barber: Barber) : Screen
    @Serializable data class Booking(val barberName: String) : Screen
    @Serializable object BookingConfirmed : Screen
    @Serializable object UpcomingAppointment : Screen
    @Serializable object SelectBarber : Screen
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any = Screen.CreateProfile,
    onProfileCreated: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Signup> {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onContinueClick = { phoneNO -> navController.navigate(Screen.OtpScreen(phoneNO)) }
            )
        }

        composable<Screen.CreateProfile> {
            CreateProfileScreen(
                onContinue = {
                    onProfileCreated()
                    navController.navigate(Screen.Dashboard) {
                        popUpTo(Screen.CreateProfile) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.OtpScreen> { backStackEntry ->
            val args : Screen.OtpScreen = backStackEntry.toRoute()
            OtpScreen(
                mobileNumber = args.mobileNumber,
                onBack = { navController.popBackStack() },
                onContinue = { navController.navigate(route = Screen.CreateProfile) }
            )
        }

        composable<Screen.Dashboard>{
            MainScreen(
                onBarberClick = { barber ->
                    navController.navigate(Screen.BarberProfile(barber))
                },
                // We pass the root controller to handle global navigation from within the dashboard tabs
                rootNavController = navController,
                onLogout = onLogout
            )
        }

        composable<Screen.BarberProfile>(
            typeMap = mapOf(typeOf<Barber>() to BarberNavType)
        ) { backStackEntry ->
            val args: Screen.BarberProfile = backStackEntry.toRoute()
            BarberProfileScreen(
                barber = args.barber,
                onBackClick = { navController.popBackStack() },
                onBookClick = { _ ->
                    navController.navigate(Screen.SelectBarber)
                }
            )
        }

        composable<Screen.SelectBarber> {
            SelectBarberScreen(
                onBackClick = { navController.popBackStack() },
                onBarberSelected = { barberName ->
                    navController.navigate(Screen.Booking(barberName))
                }
            )
        }

        composable<Screen.Booking> { backStackEntry ->
            val args: Screen.Booking = backStackEntry.toRoute()
            BookingScreen(
                barberName = args.barberName,
                onBackClick = { navController.popBackStack() },
                onBookClick = { navController.navigate(Screen.BookingConfirmed) }
            )
        }

        composable<Screen.BookingConfirmed> {
            BookingConfirmedScreen(
                onBackClick = { navController.popBackStack() },
                onViewAppointmentClick = { navController.navigate(Screen.UpcomingAppointment) },
                onCancelBookingClick = { navController.popBackStack() }
            )
        }

        composable<Screen.UpcomingAppointment> {
            UpcomingAppointmentScreen(
                onBackClick = { navController.popBackStack() },
                onEditClick = { /* Handle edit */ },
                onCancelClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun NavHostBottom(
    navController: NavHostController,
    onBarberClick: (Barber) -> Unit,
    rootNavController: NavHostController,
    onLogout: () -> Unit = {},
    startDestination: Any = Screen.Search
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable<Screen.Search> {
            SearchContent(onBarberClick = onBarberClick)
        }
        composable<Screen.Appointments> {
            AppointmentsScreen(
                onRebookClick = { barberName ->
                    rootNavController.navigate(Screen.Booking(barberName))
                },
                onActionClick = { _ ->
                    rootNavController.navigate(Screen.UpcomingAppointment)
                }
            )
        }
        composable<Screen.Profile> {
            ProfileScreen(
                onLogoutClick = {
                    onLogout()
                    rootNavController.navigate(Screen.Signup) {
                        popUpTo(rootNavController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}

val BarberNavType = object : NavType<Barber>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Barber? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }
    override fun parseValue(value: String): Barber {
        return Json.decodeFromString(Uri.decode(value))
    }
    override fun put(bundle: Bundle, key: String, value: Barber) {
        bundle.putString(key, Json.encodeToString(value))
    }
    override fun serializeAsValue(value: Barber): String {
        return Uri.encode(Json.encodeToString(value))
    }
}
