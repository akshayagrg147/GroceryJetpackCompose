package com.grocery.groceryapp.DashBoardNavRouteNavigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.Gson
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.data.modal.OrderIdResponse
import com.grocery.groceryapp.features.Home.Modal.profileScreen
import com.grocery.groceryapp.features.Home.ui.CartScreen
import com.grocery.groceryapp.features.Home.ui.PrivacyPolicyScreen
import com.grocery.groceryapp.features.Home.ui.screens.homescreen
import com.grocery.groceryapp.features.Home.ui.screens.*
import com.grocery.groceryapp.features.Spash.ProfileScreenNavigation
import com.grocery.groceryapp.features.Spash.ui.screens.MapScreen
import com.grocery.groceryapp.features.Spash.ui.screens.menuitems
import com.grocery.groceryapp.data.modal.PassingAddress
import com.grocery.groceryapp.features.Home.domain.modal.getProductCategory
import com.grocery.groceryapp.features.Spash.CategoryWiseDashboardAllData
import com.grocery.groceryapp.features.Spash.ItemScreenNavigation


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    context: HomeActivity,
    sharedPreferences: sharedpreferenceCommon

) {

    NavHost(navController, startDestination = DashBoardNavRoute.Home.screen_route) {
        composable(DashBoardNavRoute.Home.screen_route) {
            homescreen(navController,sharedPreferences)
        }
        composable(DashBoardNavRoute.DashBoardCategoryWisePagination.screen_route) {
            CategoryWiseDashboardAllData(context,navController)
        }

        composable(DashBoardNavRoute.SearchProductItems.screen_route) {
            SearchScreenProducts(navController, context)
        }

        composable(DashBoardNavRoute.ProductDetail.screen_route) {
            val data = it.arguments?.getString("data") ?: ""

            ItemScreenNavigation(context,data, navController)
        }

        composable(DashBoardNavRoute.PrivacyPolicyScreen.screen_route) {
            PrivacyPolicyScreen()
        }
        composable(DashBoardNavRoute.Profile.screen_route) {
            profileScreen(navController, context,sharedPreferences)
        }
        composable(DashBoardNavRoute.OrderDetail.screen_route) {
            var model =
                navController.previousBackStackEntry?.arguments?.getParcelable<AllOrdersHistoryList>(
                    "orderDetail"
                )

            orderDetil(data = model ?: AllOrdersHistoryList(), navController)
        }
        composable(DashBoardNavRoute.AllOrderHistory.screen_route) {

            ProfileScreenNavigation(navController, context)
        }
        composable(DashBoardNavRoute.AddressScreen.screen_route) {
            AllAddress(navController, context)
        }

        composable(DashBoardNavRoute.CartScreen.screen_route) {
            CartScreen(navController, context, sharedPreferences)
        }
        composable(DashBoardNavRoute.ApplyCoupons.screen_route){
            ApplyCoupons(navController)
        }

        composable(DashBoardNavRoute.OrderSuccessful.screen_route) {
            var model =
                navController.previousBackStackEntry?.arguments?.getParcelable<OrderIdResponse>("orderstatus")
            OrderConfirmation(data = model ?: OrderIdResponse(), navController)


        }
        composable(DashBoardNavRoute.AddnewAddressScreen.screen_route) {
            var addressmodal =
                navController.previousBackStackEntry?.arguments?.getParcelable<PassingAddress>("address")
            if (addressmodal != null) {
                addressScreen(address = addressmodal, navController = navController)
            } else {
                addressScreen(address = PassingAddress(), navController = navController)
            }


        }
        composable(DashBoardNavRoute.MenuItems.screen_route) {
            menuitems(navController, context)
        }
        composable(DashBoardNavRoute.MapScreen.screen_route) {
            MapScreen(navController, context, sharedPreferences)
        }
    }
}


