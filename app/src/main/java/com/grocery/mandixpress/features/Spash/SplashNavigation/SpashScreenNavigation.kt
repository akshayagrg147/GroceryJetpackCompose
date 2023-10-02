package com.grocery.mandixpress.features.Spash

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.mandixpress.features.Spash.ui.screens.MapScreen

@Composable
fun splashScreenNavigation(
    context: Activity,

    sharedPreferences: sharedpreferenceCommon
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {
        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(navController, context,sharedPreferences)
        }
        composable(ScreenRoute.SignUpScreen.route) {
            val mobileNumber = it.arguments?.getString("data") ?: ""
            SignUpScreen(navController, context,sharedPreferences,mobileNumber)
        }
        composable(ScreenRoute.LoginScreen.route) {
            loginScreen(navController, context,sharedPreferences)
        }
        composable(ScreenRoute.LocateMeScreen.route) {
            locateMeScreen( context)
        }
        composable(ScreenRoute.MapScreen.route) {
            MapScreen(navController, context,sharedPreferences)
        }
    }

}

