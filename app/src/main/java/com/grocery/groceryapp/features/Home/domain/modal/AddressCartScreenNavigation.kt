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
import com.grocery.groceryapp.features.Home.ui.screens.orderDetil
import com.grocery.groceryapp.features.Spash.ui.screens.menuitems
import com.wajahatkarim3.compose.books.ui.model.PassingAddress
import com.wajahatkarim3.compose.books.ui.model.PassingOrderResponse

@Composable
fun AddressCartScreenNavigation(
    context: Activity,sharedpreferenceCommon: sharedpreferenceCommon
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.CartScreen.route) {
        composable(ScreenRoute.AddressScreen.route) {
            addressScreen(PassingAddress(name=null),navController,context)
        }
        composable(ScreenRoute.CartScreen.route) {
            CartScreen(navController,context,sharedpreferenceCommon)
        }

        composable(ScreenRoute.OrderSuccessful.route){
            var model = navController.previousBackStackEntry?.arguments?.getParcelable<PassingOrderResponse>("orderstatus")
            OrderConfirmation(data = model!!)


        }

    }

}

