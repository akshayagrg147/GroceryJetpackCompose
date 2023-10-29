package com.grocery.mandixpress.features.splash.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.android.gms.location.*
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.home.ui.ui.theme.GroceryAppTheme
import com.grocery.mandixpress.screens.MapScreen1

@Composable
fun MapScreen(
    navController: NavHostController,
    context: Context,
    sharedPreferences: sharedpreferenceCommon
){

    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    MainScreen(mFusedLocationClient,true,context,sharedPreferences)


}
@Composable
fun MainScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    useSystemUIController: Boolean = true,context: Context, sharedPreferences: sharedpreferenceCommon) {
    GroceryAppTheme(useSystemUIController ) {
        MapScreen1(fusedLocationProviderClient,context,sharedPreferences)
    }




}
