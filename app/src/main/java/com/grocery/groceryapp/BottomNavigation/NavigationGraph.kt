package com.grocery.groceryapp.BottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.grocery.groceryapp.Home.Modal.profileScreen
import com.grocery.groceryapp.Home.ui.CartScreen
import com.grocery.groceryapp.Home.ui.SearchScreen
import com.grocery.groceryapp.Home.ui.homescreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            homescreen()
        }
        composable(BottomNavItem.MyNetwork.screen_route) {
            SearchScreen()
        }
        composable(BottomNavItem.AddPost.screen_route) {
            CartScreen()
        }
        composable(BottomNavItem.Notification.screen_route) {
            NotificationScreen()
        }
        composable(BottomNavItem.Jobs.screen_route) {
            profileScreen()
        }
    }
}