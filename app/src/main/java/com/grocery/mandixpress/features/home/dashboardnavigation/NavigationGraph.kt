package com.grocery.mandixpress.features.home.dashboardnavigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.grocery.mandixpress.sharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.data.modal.AllOrdersHistoryList
import com.grocery.mandixpress.data.modal.OrderIdResponse
import com.grocery.mandixpress.data.modal.PassingAddress
import com.grocery.mandixpress.features.home.Modal.ProfileScreen
import com.grocery.mandixpress.features.home.ui.screens.CartScreen
import com.grocery.mandixpress.features.home.ui.screens.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.features.home.ui.screens.ProfileScreenNavigation
import com.grocery.mandixpress.features.splash.splashnavigation.ItemScreenNavigation
import com.grocery.mandixpress.features.splash.ui.screens.ListItems
import com.grocery.mandixpress.features.splash.ui.screens.MapScreen
import com.grocery.mandixpress.features.splash.ui.screens.menuitems


@Composable
fun NavigationGraph(
    viewModal: HomeAllProductsViewModal,
    navController: NavHostController,
    context: HomeActivity,
    sharedPreferences: sharedpreferenceCommon

) {

    NavHost(navController, startDestination = DashBoardNavRoute.Home.screen_route) {
        composable(DashBoardNavRoute.Home.screen_route) {
            Homescreen(navController, sharedPreferences, viewModal)
        }
        composable(DashBoardNavRoute.DashBoardCategoryWisePagination.screen_route) {


            CategoryWiseDashboardAllData(context, navController)
        }

        composable(DashBoardNavRoute.SearchProductItems.screen_route) {
            SearchScreenProducts(navController, context)
        }

        composable(DashBoardNavRoute.ProductDetail.screen_route) {
            val data = it.arguments?.getString("data") ?: ""

            ItemScreenNavigation(context, data, navController)
        }

        composable(DashBoardNavRoute.PrivacyPolicyScreen.screen_route) {
            PrivacyPolicyScreen()
        }
        composable(DashBoardNavRoute.Profile.screen_route) {
            ProfileScreen(navController, context, sharedPreferences)
        }
        composable(DashBoardNavRoute.OrderDetail.screen_route) {
            val model =
                navController.previousBackStackEntry?.arguments?.getParcelable<AllOrdersHistoryList.Orders>(
                    "orderDetail"
                )
            if (model != null)
                orderDetil(data = model, navController)
        }

        composable(DashBoardNavRoute.SeeAllScreen.screen_route) {
            ListItems(navController, LocalContext.current, viewModal)

        }

        composable(DashBoardNavRoute.AllOrderHistory.screen_route) {

            ProfileScreenNavigation(navController, context)
        }
        composable(DashBoardNavRoute.AddressScreen.screen_route) {
            val data = it.arguments?.getString("data") ?: ""
            Log.d("datavalueget","$data")
            AllAddress(navController, context,data)
        }

        composable(DashBoardNavRoute.CartScreen.screen_route) {
            CartScreen(navController, context, sharedPreferences)
        }
        composable(DashBoardNavRoute.ApplyCoupons.screen_route) {
            ApplyCoupons(navController)
        }

        composable(DashBoardNavRoute.OrderSuccessful.screen_route) {
            val model =
                navController.previousBackStackEntry?.arguments?.getParcelable<OrderIdResponse>("orderstatus")
            OrderConfirmation(data = model ?: OrderIdResponse(), navController)


        }
        composable(DashBoardNavRoute.AddnewAddressScreen.screen_route) {
            val addressmodal =
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
        composable(DashBoardNavRoute.AddBottomSearch.screen_route) {
            CurvedBottomSheetWithButton()
        }

    }
}


