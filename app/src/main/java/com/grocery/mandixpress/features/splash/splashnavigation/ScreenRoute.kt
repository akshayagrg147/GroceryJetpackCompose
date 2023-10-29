package com.grocery.mandixpress.features.splash.splashnavigation

sealed class ScreenRoute(val route: String) {
    object SplashScreen : ScreenRoute("splashscreen")
    object SignUpScreen : ScreenRoute("{data}/signupscreen"){
        fun senddata(data:String)="$data/signupscreen"

    }
    object LoginScreen : ScreenRoute("loginscreen")
    object LocateMeScreen: ScreenRoute("locateMeScreen")
    object MapScreen: ScreenRoute("mapscreen")

    object HomeScreen: ScreenRoute("homescreen")
    object OrderHistory: ScreenRoute("OrderHistory")
    object ItemDetailScreen: ScreenRoute("ItemDetailScreen")

    //cart screen
    object CartScreen : ScreenRoute("CartScreen")
    object AddressScreen : ScreenRoute("AddressScreen")

    object OrderSuccessful : ScreenRoute("OrderSuccessful"){



    }


}