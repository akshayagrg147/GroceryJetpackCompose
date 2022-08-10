package com.grocery.groceryapp.Spash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.Home.ui.homescreen
import com.grocery.groceryapp.Utils.ScreenRoute

@Composable
fun homescreenNavigation(context: Context) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route) {
        composable(ScreenRoute.HomeScreen.route) {
         homescreen()
        }




    }

}