package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.features.Home.ui.CartScreen
import com.grocery.groceryapp.features.Home.ui.screens.addressScreen

@Composable
fun AddressCartScreenNavigation(
    context: Activity
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.CartScreen.route) {
        composable(ScreenRoute.AddressScreen.route) {
            addressScreen(navController,context)
        }
        composable(ScreenRoute.CartScreen.route) {
            CartScreen(navController,context)
        }

    }

}

