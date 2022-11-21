package com.grocery.groceryapp.BottomNavigation

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.features.Spash.ui.screens.MapScreen


sealed class BottomNavItem(var title:String, var icon:Int=0, var screen_route:String=""){

    object Home : BottomNavItem("Shop", R.drawable.ic_shop,"Shop")
    object SearchProductItems : BottomNavItem("Search", R.drawable.ic_shop,"Search")


    object Profile: BottomNavItem("Account",R.drawable.ic_accounts,"Account")
    object AddressScreen : BottomNavItem("AddressScreen",1,"AddressScreen")
    object AllOrderHistory : BottomNavItem("AllOrderHistory",1,"AllOrderHistory")
    object CartScreen : BottomNavItem("CartScreen",1,"CartScreen")
    object OrderSuccessful : BottomNavItem("OrderSuccessful",1,"OrderSuccessful")
    object ProductDetail : BottomNavItem("ProductDetail",0,"{data}/ProductDetail"){
        fun senddata(data:String)="$data/ProductDetail"

    }
    object AddnewAddressScreen : BottomNavItem("AddnewAddressScreen",R.drawable.location_icon,"AddnewAddressScreen")

    object OrderDetail : BottomNavItem("OrderDetail",R.drawable.location_icon,"OrderDetail")

    object MapScreen : BottomNavItem("MapScreen",1,"MapScreen")


    object PrivacyPolicyScreen : BottomNavItem("PrivacyPolicyScreen",R.drawable.location_icon,"PrivacyPolicy")
    //submenu items
    object MenuItems : BottomNavItem("MenuItems",0,"{data}/MenuItems"){
        fun senddata(data:String)="$data/MenuItems"

    }


}