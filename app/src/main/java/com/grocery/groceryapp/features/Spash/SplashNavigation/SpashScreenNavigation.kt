package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.groceryapp.features.Spash.ui.screens.MapScreen

@Composable
fun splashScreenNavigation(
    context: Activity,

    sharedPreferences: sharedpreferenceCommon
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.LocateMeScreen.route) {
        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(navController, context,sharedPreferences)
        }
        composable(ScreenRoute.SignUpScreen.route) {
            SignUpScreen(navController, context,sharedPreferences)
        }
        composable(ScreenRoute.LoginScreen.route) {
            loginScreen(navController, context,sharedPreferences)
        }
        composable(ScreenRoute.LocateMeScreen.route) {
            locateMeScreen(navController, context)
        }
        composable(ScreenRoute.MapScreen.route) {
            MapScreen(navController, context,sharedPreferences)
        }
    }

}

