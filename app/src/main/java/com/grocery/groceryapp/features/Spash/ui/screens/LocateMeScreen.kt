package com.grocery.groceryapp.features.Spash

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.grocery.groceryapp.HiltApplication.Companion.context

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.CommonButton
import com.grocery.groceryapp.Utils.Text14_h1
import com.grocery.groceryapp.common.CommonProgressBar
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.greyLightColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.MapScreenViewModal
import vtsen.hashnode.dev.simplegooglemapapp.ui.LocationUtils
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.LocationPermissionsAndSettingDialogs
import java.util.*

@Composable
fun locateMeScreen( context: Context,mapScreenViewModal: MapScreenViewModal= hiltViewModel()) {
    var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context!!)
    var latitude: Double? = null
    var longitude: Double? = null
    var isDialog by remember { mutableStateOf(true) }
    var requestLocationUpdate by remember { mutableStateOf(true) }
    var combinedaddress by remember { mutableStateOf("") }
    if (isDialog)
        CommonProgressBar()
    if (requestLocationUpdate) {
        Log.d("latitudeandlongitude","call1")

        LocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                Log.d("latitudeandlongitude","call2")
                LocationUtils.requestLocationResultCallback(mFusedLocationClient) { locationResult ->
                    locationResult.lastLocation?.let { location ->
                        latitude = location.latitude
                        longitude = location.longitude
                        Log.d("latitudeandlongitude","call3")
                        getAddressFromLatLng(
                            context,
                            latitude!!,
                            longitude!!,
                            mapScreenViewModal
                        ) { lat, lng, addrss, city ->
                            if (addrss != null) {
                                isDialog=false
                                combinedaddress = "${addrss.getAddressLine(0)},$city"
                                mapScreenViewModal.setAddress(combinedaddress,city)
                            }

                        }
                    }

                }
            }
        )
    }

    Column( verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()) {

        Column( modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "splash image",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text14_h1(
                        text = "Get our service in nearby of your locations",
                        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
                    )
            Text14_h1(
                text = "${combinedaddress}",
                color= greyLightColor,
                modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
            )
            Column( modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.Bottom)
            {
                CommonButton(
                    text = "Locate Me",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    backgroundColor = titleColor,
                    color = Color.White,
                    enabled = combinedaddress.isNotEmpty()
                )
                {
                    context.startActivity(Intent(context, HomeActivity::class.java))

                }
            }

            }




    }

}
fun getAddressFromLatLng(
    context: Context, latitude: Double, longitude: Double,
    mapScreenViewModal: MapScreenViewModal,
    callback: (Double, Double, Address?, String) -> Unit,

    ) {
    val gcd = Geocoder(context, Locale.getDefault())
    Log.d("latitudeandlongitude","$latitude $longitude")
    mapScreenViewModal.ConvertLatLngToAddress(latitude,longitude,gcd){
        if(it.isNotEmpty()){
            callback(
                latitude, longitude, it[0], getCityNameByCoordinates(
                    latitude,
                    longitude
                )!!
            )
        }
        else{
            callback(
                latitude!!, longitude!!, null, "empty"
            )
        }
    }




}
private fun getCityNameByCoordinates(lat: Double, lon: Double): String? {
    var cityname: String = "empty"
    try {
        val addresses: List<Address> =
            Geocoder(context, Locale.getDefault()).getFromLocation(lat, lon, 10)

        if (addresses.isNotEmpty()) {
            for (adr in addresses) {
                if (adr.locality != null && adr.locality.isNotEmpty()) {
                    cityname = adr.locality
                    break
                }
            }
        }
    }
    catch (e:Exception){
        Log.d("TAG", "getCityNameByCoordinates: ${e.message}")
    }
    return cityname
}