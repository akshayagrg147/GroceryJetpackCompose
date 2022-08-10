package com.grocery.groceryapp.Utils

sealed class ScreenRoute(val route: String) {
    object SplashScreen : ScreenRoute("splashscreen")
    object SignUpScreen : ScreenRoute("signupscreen")
    object LoginScreen : ScreenRoute("loginscreen")
    object LocateMeScreen:ScreenRoute("locateMeScreen")

    object HomeScreen:ScreenRoute("homescreen")


}
