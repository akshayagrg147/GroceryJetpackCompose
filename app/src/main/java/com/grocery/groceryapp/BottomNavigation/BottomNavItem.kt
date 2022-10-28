package com.grocery.groceryapp.BottomNavigation

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.ScreenRoute


sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Shop", R.drawable.ic_shop,"Shop")
    object MyNetwork: BottomNavItem("Explore",
       R.drawable.ic_explore,"Explore")
//    object AddPost: BottomNavItem("cart",R.drawable.ic_cart,"cart")
    object Notification: BottomNavItem("Favourite",R.drawable.ic_favorite,"Favourite")
    object Profile: BottomNavItem("Account",R.drawable.ic_accounts,"Account")
    object AddressScreen : BottomNavItem("AddressScreen",1,"AddressScreen")
    object AllOrderHistory : BottomNavItem("AllOrderHistory",1,"AllOrderHistory")
    object AddnewAddressScreen : BottomNavItem("AddnewAddressScreen",R.drawable.location_icon,"{data}/AddnewAddressScreen"){
        fun senddata(data:String)="$data/AddnewAddressScreen"
    }
    object OrderDetail : BottomNavItem("OrderDetail",R.drawable.location_icon,"{data}/OrderDetail"){
        fun senddata(data:String)="$data/OrderDetail"
    }

    object PrivacyPolicyScreen : BottomNavItem("PrivacyPolicyScreen",R.drawable.location_icon,"PrivacyPolicy")
    //submenu items
    object MenuItems : BottomNavItem("MenuItems",0,"{data}/MenuItems"){
        fun senddata(data:String)="$data/MenuItems"

    }
}