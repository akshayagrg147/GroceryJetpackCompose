package com.grocery.mandixpress.features.home.dashboardnavigation


sealed class DashBoardNavRoute(var screen_route: String = "") {

    object Home : DashBoardNavRoute("Shop")
    object SearchProductItems : DashBoardNavRoute("Search")

    object DashBoardCategoryWisePagination : DashBoardNavRoute("DashBoardCategoryWisePagination")

    object Profile : DashBoardNavRoute("Account")
    object AddressScreen : DashBoardNavRoute("{data}/AddressScreen"){
        fun senddata(data: String) = "$data/AddressScreen"
    }
    object AllOrderHistory : DashBoardNavRoute("AllOrderHistory")
    object CartScreen : DashBoardNavRoute("CartScreen")
    object OrderSuccessful : DashBoardNavRoute("OrderSuccessful")
    object ApplyCoupons : DashBoardNavRoute("ApplyCoupons")
    object ProductDetail : DashBoardNavRoute("{data}/ProductDetail") {
        fun senddata(data: String) = "$data/ProductDetail"

    }

    object AddnewAddressScreen : DashBoardNavRoute("AddnewAddressScreen")

    object OrderDetail : DashBoardNavRoute("OrderDetail")
    object SeeAllScreen : DashBoardNavRoute("seeAllScreen")

    object MapScreen : DashBoardNavRoute("MapScreen")
    object AddBottomSearch : DashBoardNavRoute("AddBottomSearch")


    object PrivacyPolicyScreen : DashBoardNavRoute("PrivacyPolicy")

    //submenu items
    object MenuItems : DashBoardNavRoute("MenuItems")


}