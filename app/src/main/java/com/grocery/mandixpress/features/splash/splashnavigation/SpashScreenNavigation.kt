package com.grocery.mandixpress.features.splash.splashnavigation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.grocery.mandixpress.features.splash.ui.screens.*

@Composable
fun splashScreenNavigation(
    context: Activity,
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {
        composable(ScreenRoute.SplashScreen.route) {
            SplashScreen(navController, context)
        }
        composable(ScreenRoute.SignUpScreen.route) {
            val data = it.arguments?.getString("data") ?: "7508075534_136027"
            Log.d("data_Check", data)

                SignUpScreen(navController, context,data.split("_")[0],data.split("_")[1])
        }
        composable(ScreenRoute.LoginScreen.route) {
            loginScreen(navController, context)
        }
        composable(ScreenRoute.LocateMeScreen.route) {
            val mobileNumber = it.arguments?.getString("data") ?: ""
            LocateMeScreen( context,navController,mobileNumber)
        }
        composable(ScreenRoute.MapScreen.route) {
            MapScreen( context)
        }
    }

}

