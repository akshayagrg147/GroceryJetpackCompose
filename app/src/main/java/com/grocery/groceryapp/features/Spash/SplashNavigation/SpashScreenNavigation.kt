package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.features.Spash.ui.viewmodel.RegisterLoginViewModal
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.features.Spash.ui.screens.MapScreen

@Composable
fun splashScreenNavigation(context: Activity,viewModal: RegisterLoginViewModal) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.MapScreen.route) {
        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(navController, context)
        }
        composable(ScreenRoute.SignUpScreen.route) {
            SignUpScreen(navController, context,viewModal)
        }
        composable(ScreenRoute.LoginScreen.route) {
            loginScreen(navController, context)
        }
        composable(ScreenRoute.LocateMeScreen.route) {
            locateMeScreen(navController, context)
        }
        composable(ScreenRoute.MapScreen.route) {
            MapScreen(navController, context)
        }
    }

}

