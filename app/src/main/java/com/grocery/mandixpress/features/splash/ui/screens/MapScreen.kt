package com.grocery.mandixpress.features.splash.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.android.gms.location.*
import com.grocery.mandixpress.features.home.ui.ui.theme.GroceryAppTheme
import com.grocery.mandixpress.screens.MapScreen1

@Composable
fun MapScreen(
    context: Context
){

    var mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    MainScreen(mFusedLocationClient,true,context)


}
@Composable
fun MainScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    useSystemUIController: Boolean = true,context: Context) {
    GroceryAppTheme(useSystemUIController ) {
        MapScreen1(fusedLocationProviderClient,context)
    }




}
