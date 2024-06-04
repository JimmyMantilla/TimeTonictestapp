package com.example.timetonictestapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.B_C
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.Detail
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.Landing
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.Login
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.O_U
import com.example.timetonictestapp.ui.navigation.TimeTonicScreens.SessionKey
import com.example.timetonictestapp.ui.screens.bookinfo.LandingDetailScreen
import com.example.timetonictestapp.ui.screens.landing.LandingScreen
import com.example.timetonictestapp.ui.screens.login.LoginScreen


@Composable
fun TimeTonicApp(
    navController: NavHostController = rememberNavController()
) {
    // Set up navigation graph
    NavHost(
        navController = navController,
        startDestination = Login.route // Start destination is Login screen
    ) {
        // Define navigation routes and associated composables
        composable(Login.route) {
            // Show Login screen
            LoginScreen(
                navigateToLandingPage = { loginResponse ->
                    // Navigate to Landing screen with session key and o_u as arguments
                    navController.navigate("${Landing.route}/${loginResponse.sesskey}/${loginResponse.o_u}")
                }
            )
        }
        composable(
            route = "${Landing.route}/{${SessionKey.route}}/{${O_U.route}}",
            arguments = listOf(
                navArgument(SessionKey.route) { type = NavType.StringType },
                navArgument(O_U.route) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve session key and o_u from arguments
            val sesskey = requireNotNull(backStackEntry.arguments?.getString(SessionKey.route))
            val o_u = requireNotNull(backStackEntry.arguments?.getString(O_U.route))

            // Show Landing screen with session key and o_u
            LandingScreen(
                navigateToBookDetail = { bookResponse ->
                    // Navigate to Detail screen with session key, o_u, and book code as arguments
                    navController.navigate("${Detail.route}/${bookResponse.sesskey}/${bookResponse.o_u}/${bookResponse.b_c}")
                },
                navigateToLogin = {
                    // Navigate back to Login screen
                    navController.navigate(Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                sesskey = sesskey,
                o_u = o_u
            )
        }
        composable(
            route = "${Detail.route}/{${SessionKey.route}}/{${O_U.route}}/{${B_C.route}}",
            arguments = listOf(
                navArgument(SessionKey.route) { type = NavType.StringType },
                navArgument(O_U.route) { type = NavType.StringType },
                navArgument(B_C.route) { type = NavType.StringType }
            )
        ) {
            // Show Detail screen
            LandingDetailScreen(
                navigateToLogin = {
                    // Navigate back to Login screen
                    navController.navigate(Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                navigateUp = {
                    // Navigate up in the navigation stack
                    navController.navigateUp()
                }
            )
        }
    }
}
