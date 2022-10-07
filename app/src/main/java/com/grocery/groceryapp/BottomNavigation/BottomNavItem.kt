package com.grocery.groceryapp.BottomNavigation

import com.grocery.groceryapp.R


sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Shop", R.drawable.ic_shop,"Shop")
    object MyNetwork: BottomNavItem("Explore",
       R.drawable.ic_explore,"Explore")
//    object AddPost: BottomNavItem("cart",R.drawable.ic_cart,"cart")
    object Notification: BottomNavItem("Favourite",R.drawable.ic_favorite,"Favourite")
    object Jobs: BottomNavItem("Account",R.drawable.ic_accounts,"Account")
}