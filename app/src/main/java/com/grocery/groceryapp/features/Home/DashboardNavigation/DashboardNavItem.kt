package com.grocery.groceryapp.DashBoardNavRouteNavigation


sealed class DashBoardNavRoute( var screen_route:String=""){

    object Home : DashBoardNavRoute("Shop")
    object SearchProductItems : DashBoardNavRoute("Search")


    object DashBoardCategoryWisePagination: DashBoardNavRoute("DashBoardCategoryWisePagination")
    object Profile: DashBoardNavRoute("Account")
    object AddressScreen : DashBoardNavRoute("AddressScreen")
    object AllOrderHistory : DashBoardNavRoute("AllOrderHistory")
    object CartScreen : DashBoardNavRoute("CartScreen")
    object OrderSuccessful : DashBoardNavRoute("OrderSuccessful")
    object ProductDetail : DashBoardNavRoute("{data}/ProductDetail"){
        fun senddata(data:String)="$data/ProductDetail"

    }
    object AddnewAddressScreen : DashBoardNavRoute("AddnewAddressScreen")

    object OrderDetail : DashBoardNavRoute("OrderDetail")

    object MapScreen : DashBoardNavRoute("MapScreen")


    object PrivacyPolicyScreen : DashBoardNavRoute("PrivacyPolicy")
    //submenu items
    object MenuItems : DashBoardNavRoute("{data}/MenuItems"){
        fun senddata(data:String)="$data/MenuItems"

    }


}