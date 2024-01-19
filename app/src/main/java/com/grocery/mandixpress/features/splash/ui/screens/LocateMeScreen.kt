package com.grocery.mandixpress.features.splash.ui.screens

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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.grocery.mandixpress.HiltApplication.Companion.context

import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.CommonButton
import com.grocery.mandixpress.Utils.Text14_h1
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.common.Utils.Companion.extractSixDigitNumber
import com.grocery.mandixpress.features.home.ui.screens.HomeActivity
import com.grocery.mandixpress.features.home.ui.ui.theme.greyLightColor
import com.grocery.mandixpress.features.home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.home.ui.ui.theme.titleColor
import com.grocery.mandixpress.features.splash.ui.viewmodel.MapScreenViewModal
import com.grocery.mandixpress.screens.LocationUtils
import com.grocery.mandixpress.screens.LocationPermissionsAndSettingDialogs
import java.util.*

@Composable
fun LocateMeScreen( context: Context,mapScreenViewModal: MapScreenViewModal= hiltViewModel()) {
    var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0
    var isDialog by remember { mutableStateOf(true) }
    var requestLocationUpdate by remember { mutableStateOf(true) }
    var combinedaddress by remember { mutableStateOf("") }
    if (isDialog)
        CommonProgressBar(text = "Fetching Location....")
    if (requestLocationUpdate) {

        LocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                Log.d("latitudeandlongitude","call2")
                LocationUtils.requestLocationResultCallback(mFusedLocationClient) { locationResult ->
                    locationResult.lastLocation?.let { location ->
                        latitude = location.latitude
                        longitude = location.longitude
                        getAddressFromLatLng(
                            context,
                            latitude?:0.0,
                            longitude?:0.0,
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
                    color= headingColor,

                        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
                    )
            Text14_h1(
                text = combinedaddress,
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
                {  Log.d("errorcheck","${extractSixDigitNumber(combinedaddress)}")
                    if(extractSixDigitNumber(combinedaddress)?.length==6){
                        mapScreenViewModal.savePinCode(extractSixDigitNumber(combinedaddress))
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    }
                    else{
                        Log.d("errorcheck","${extractSixDigitNumber(combinedaddress)}")
                    }



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
    mapScreenViewModal.ConvertLatLngToAddress(latitude,longitude,gcd){
        if(it.isNotEmpty()){
            callback(
                latitude, longitude, it[0], getCityNameByCoordinates(
                    latitude,
                    longitude
                )
            )
        }
        else{
            callback(
                latitude, longitude, null, "empty"
            )
        }
    }




}
private fun getCityNameByCoordinates(lat: Double, lon: Double): String {
    var cityname = "empty"
    try {
        val addresses: List<Address> =
            Geocoder(context!!, Locale.getDefault()).getFromLocation(lat, lon, 10)?: emptyList()

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