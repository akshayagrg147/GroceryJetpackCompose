package com.grocery.groceryapp.features.Spash.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.MapScreen1

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
