package com.grocery.groceryapp.Spash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.Utils.ScreenRoute

@Composable
fun splashScreenNavigation(context: Context) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {
        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(navController, context)
        }
        composable(ScreenRoute.SignUpScreen.route) {
            SignUpScreen(navController, context)
        }
        composable(ScreenRoute.LoginScreen.route) {
            loginScreen(navController, context)
        }
        composable(ScreenRoute.LocateMeScreen.route) {
            locateMeScreen(navController, context)
        }



    }

}