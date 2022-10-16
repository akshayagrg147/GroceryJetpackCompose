package com.grocery.groceryapp.BottomNavigation

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.ScreenRoute


sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Shop", R.drawable.ic_shop,"Shop")
    object MyNetwork: BottomNavItem("Explore",
       R.drawable.ic_explore,"Explore")
//    object AddPost: BottomNavItem("cart",R.drawable.ic_cart,"cart")
    object Notification: BottomNavItem("Favourite",R.drawable.ic_favorite,"Favourite")
    object Jobs: BottomNavItem("Account",R.drawable.ic_accounts,"Account")
    object AddressScreen : BottomNavItem("AddressScreen",R.drawable.location_icon,"AddressScreen")
    //submenu items
    object MenuItems : BottomNavItem("MenuItems",0,"{data}/MenuItems"){
        fun senddata(data:String)="$data/MenuItems"

    }
}