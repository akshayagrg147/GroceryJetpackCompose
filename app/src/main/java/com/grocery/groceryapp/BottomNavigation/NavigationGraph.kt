package com.grocery.groceryapp.BottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.grocery.groceryapp.features.Home.Modal.profileScreen
import com.grocery.groceryapp.features.Home.ui.HomeActivity
import com.grocery.groceryapp.features.Home.ui.SearchScreen
import com.grocery.groceryapp.features.Home.ui.homescreen
import com.grocery.groceryapp.features.Home.ui.screens.AllAddress
import com.grocery.groceryapp.features.Spash.ui.screens.menuitems

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    context: HomeActivity,

) {

    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            homescreen(navController,context)
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
        composable(BottomNavItem.Jobs.screen_route) {
            profileScreen(navController,context)
        }
        composable(BottomNavItem.AddressScreen.screen_route) {
            AllAddress(navController,context)
        }
        composable(BottomNavItem.MenuItems.screen_route){
            val data=it.arguments?.getString("data")?:""
            menuitems(navController,data)
        }
    }
}
