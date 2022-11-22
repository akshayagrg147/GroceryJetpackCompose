package vtsen.hashnode.dev.simplegooglemapapp.ui.screens

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.compose.*
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.CommonButton
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity
import com.grocery.groceryapp.features.Spash.ui.viewmodel.RegisterLoginViewModal
import dagger.hilt.android.qualifiers.ApplicationContext
import vtsen.hashnode.dev.simplegooglemapapp.ui.LocationUtils
import java.util.*

@SuppressLint("MissingPermission")
@Composable
fun MapScreen1(fusedLocationProviderClient: FusedLocationProviderClient,@ApplicationContext context: Context,sharedPreferences: sharedpreferenceCommon) {

    var currentLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }
    var address by remember {
        mutableStateOf("")
    }

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LocationUtils.getPosition(currentLocation), 12f
    )

    var requestLocationUpdate by remember { mutableStateOf(true) }

    MyGoogleMap(sharedPreferences,address,
        currentLocation,
        cameraPositionState, context = context
    ) {
        requestLocationUpdate = true
    }

    if (requestLocationUpdate) {
        LocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->

                    locationResult.lastLocation?.let { location ->
                        currentLocation = location
                    }

                }
            }
        )
    }
}

@Composable
private fun MyGoogleMap(
    sharedPreferences: sharedpreferenceCommon,
    address: String,
    currentLocation: Location, cameraPositionState: CameraPositionState,
    context: Context,
    onGpsIconClick: () -> Unit,
) {
    var add=address
    add= getAddress(context, LatLng(currentLocation.latitude,currentLocation.longitude))
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(zoomControlsEnabled = false)
        )
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
    ) {
//        Marker(
//            position = LocationUtils.getPosition(currentLocation),
//            title = "Your Title",
//            snippet = "Place Name"
//        )

    }

    GpsIconButton(onIconClick = onGpsIconClick)

    //DebugOverlay(cameraPositionState)
  //  placesApi(cameraPositionState,address)

    Column(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
        Text14_400(text = add, modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)

            .padding(10.dp)

        )
            CommonButton(
                text = "Continue",
                modifier = Modifier.fillMaxWidth()
            )
            {
                sharedPreferences.setCombineAddress(add)
                context.startActivity(Intent(context, HomeActivity::class.java))
            }

}}
private fun getAddress(context: Context,latLng: LatLng): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>?
    val address: Address?
    var addressText = ""

    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

    if (addresses.isNotEmpty()) {
        address = addresses[0]
        addressText = address.getAddressLine(0)
    } else{
        addressText = "its not appear"
    }
    return addressText
}

@Composable
fun placesApi( cameraPositionState: CameraPositionState, address: String,viewModal: RegisterLoginViewModal = hiltViewModel()) {
    var addr=address

    if (cameraPositionState.isMoving){
        addr=cameraPositionState.position.target.latitude.toString()
    }
    val context = LocalContext.current
    val intent =
        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, viewModal.field).build(context)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(it.data!!)

                Log.d("place LatLng: ", place.latLng.latitude.toString())

                // move the camera position of the map

                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(place.latLng, 15f))

            }

        }
    Column(modifier = Modifier.fillMaxSize(),Arrangement.SpaceBetween) {
        Text24_700(text = addr, modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)

            .padding(10.dp)
            .clickable { launcher.launch(intent) }
        )
        if(addr.isNotEmpty())
        CommonButton(
            text = "Continue",
            modifier = Modifier.fillMaxWidth()
        )
        {
        }
    }





}

@Composable
fun idlemap() {

//    AndroidView(
//        factory = { context ->
//            val view = LayoutInflater.from(context).inflate(R.layout.map_layout, null, false)
//            val mapFragment = context.fragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//
//            mapFragment.getMapAsync { map ->
//
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return
//                }
//                map.isMyLocationEnabled = true
//                map.uiSettings.isZoomControlsEnabled = true
//
//
//
//                map.setOnCameraIdleListener{
//                    val cameraPosition = map.cameraPosition
//                    val location = Location(cameraPosition.target.latitude, cameraPosition.target.longitude)
//                    viewModel.setLocation(location)
//                    viewModel.getNearestStops(location)
//                }
//
//
//            }
//
//            view
//        },
//        update = {  }
//    )





}

@Composable
private fun GpsIconButton(onIconClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(onClick = onIconClick) {
                Icon(
                    modifier = Modifier.padding(bottom = 100.dp, end = 20.dp),
                    painter = painterResource(id = R.drawable.location_pin),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun DebugOverlay(
    cameraPositionState: CameraPositionState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        val moving =
            if (cameraPositionState.isMoving) "moving" else "not moving"
        Text(
            text = "Camera is $moving",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = "Camera position is ${cameraPositionState.position}",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}


