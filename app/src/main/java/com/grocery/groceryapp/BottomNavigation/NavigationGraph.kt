package com.grocery.groceryapp.BottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.data.modal.OrderIdResponse
import com.grocery.groceryapp.features.Home.Modal.profileScreen
import com.grocery.groceryapp.features.Home.ui.*
import com.grocery.groceryapp.features.Home.ui.screens.*
import com.grocery.groceryapp.features.Spash.ProfileScreenNavigation
import com.grocery.groceryapp.features.Spash.ui.screens.MapScreen
import com.grocery.groceryapp.features.Spash.ui.screens.menuitems
import com.wajahatkarim3.compose.books.ui.model.PassingAddress


@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    context: HomeActivity,
    sharedPreferences:sharedpreferenceCommon

) {


    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            homescreen(navController,context)
        }
        composable(BottomNavItem.SearchProductItems.screen_route){
            SearchScreenProducts()
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            SearchScreen()
        }
//        composable(BottomNavItem.AddPost.screen_route) {
//            CartScreen()
//        }
        composable(BottomNavItem.Notification.screen_route) {
            SearchScreen()
        }
        composable(BottomNavItem.PrivacyPolicyScreen.screen_route) {
            PrivacyPolicyScreen()
        }
        composable(BottomNavItem.Profile.screen_route) {
            profileScreen(navController,context)
        }
        composable(BottomNavItem.OrderDetail.screen_route) {
            var model = navController.previousBackStackEntry?.arguments?.getParcelable<AllOrdersHistoryList>("orderDetail")

            orderDetil(data = model?:AllOrdersHistoryList(),navController)
        }
        composable(BottomNavItem.AllOrderHistory.screen_route) {

            ProfileScreenNavigation(navController,context)
        }
        composable(BottomNavItem.AddressScreen.screen_route) {
            AllAddress(navController,context)
        }

        composable(BottomNavItem.CartScreen.screen_route) {
            CartScreen(navController,context,sharedPreferences)
        }

        composable(BottomNavItem.OrderSuccessful.screen_route){
            var model = navController.previousBackStackEntry?.arguments?.getParcelable<OrderIdResponse>("orderstatus")
            OrderConfirmation(data = model?:OrderIdResponse(),navController)


        }
        composable(BottomNavItem.AddnewAddressScreen.screen_route) {
            var addressmodal = navController.previousBackStackEntry?.arguments?.getParcelable<PassingAddress>("address")
            if(addressmodal!=null){
                addressScreen(address = addressmodal, navController = navController,context)
            }
            else{
                addressScreen(address = PassingAddress(), navController = navController,context)
            }



        }
        composable(BottomNavItem.MenuItems.screen_route){
            val data=it.arguments?.getString("data")?:""
            menuitems(navController,context,data)
        }
        composable(BottomNavItem.MapScreen.screen_route) {
            MapScreen(navController, context,sharedPreferences)
        }
    }
}
