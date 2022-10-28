package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.features.Home.ui.CartScreen
import com.grocery.groceryapp.features.Home.ui.screens.OrderConfirmation
import com.grocery.groceryapp.features.Home.ui.screens.addressScreen
import com.grocery.groceryapp.features.Spash.ui.screens.menuitems

@Composable
fun AddressCartScreenNavigation(
    context: Activity,sharedpreferenceCommon: sharedpreferenceCommon
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.CartScreen.route) {
        composable(ScreenRoute.AddressScreen.route) {
            addressScreen(null,navController,context)
        }
        composable(ScreenRoute.CartScreen.route) {
            CartScreen(navController,context,sharedpreferenceCommon)
        }

        composable(ScreenRoute.OrderSuccessful.route){
            val data=it.arguments?.getBoolean("data")
            OrderConfirmation(data!!,navController,context)
        }

    }

}

