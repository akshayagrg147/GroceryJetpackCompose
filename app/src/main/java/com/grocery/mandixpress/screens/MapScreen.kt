package com.grocery.mandixpress.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.grocery.mandixpress.R
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.CommonButton
import com.grocery.mandixpress.Utils.CommonHeader
import com.grocery.mandixpress.Utils.CommonNumberField
import com.grocery.mandixpress.Utils.CommonTextFieldNonEditable
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text14_h1
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.features.home.ui.screens.HomeActivity
import com.grocery.mandixpress.features.home.ui.screens.PredictionItem
import com.grocery.mandixpress.features.home.ui.ui.theme.ShimmerColorShades
import com.grocery.mandixpress.features.home.ui.ui.theme.black_111111
import com.grocery.mandixpress.features.home.ui.ui.theme.titleColor
import com.grocery.mandixpress.features.home.ui.ui.theme.whiteColor
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.Locale


@SuppressLint("MissingPermission")
@Composable
fun MapScreen1(
    fusedLocationProviderClient: FusedLocationProviderClient,
    @ApplicationContext context: Context,
    sharedPreferences: sharedpreferenceCommon,
    navController: NavHostController,
    viewModal: HomeAllProductsViewModal= hiltViewModel()
) {
    var currentLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }
    var currentSavedLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }
    var address by remember { mutableStateOf("") }
    var cameraPositionState = rememberCameraPositionState()
    var hasInitialLocation by remember { mutableStateOf(false) }
    var searchScreenVisibility by remember { mutableStateOf(false) }
    var isCameraIdle by remember { mutableStateOf(false) }
    if(searchScreenVisibility) {
        searchLocation(cameraPositionState, viewModal1 = viewModal!!, sharedPreferences, context,navController) {
            searchScreenVisibility = true
        }
    }
    else{

        // Request location and set initial camera position
        LaunchedEffect(Unit) {
            LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                locationResult.lastLocation?.let { location ->

                    currentLocation = location
                    if (!hasInitialLocation) {
                        // Set initial camera position to user's location
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LocationUtils.getPosition(location), 12f
                        )
                        hasInitialLocation = true
                    }
                }
            }
        }

        // Detect camera movement and handle idle state
        LaunchedEffect(cameraPositionState.isMoving) {
            if (!cameraPositionState.isMoving) {
                isCameraIdle = true
            } else {
                isCameraIdle = false
            }
        }

        // Debounce logic: wait 3 seconds after the camera stops moving
        LaunchedEffect(isCameraIdle) {
            if (isCameraIdle) {
                delay(3000) // Wait for 3 seconds
                if (!cameraPositionState.isMoving) {
                    val targetLatLng = cameraPositionState.position.target
                    address = getAddress(context, targetLatLng)
                    isCameraIdle = false // Reset the idle state

                }
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        )

        // Render the UI elements
        MyGoogleMap(
            viewModal,
            isCameraIdle = isCameraIdle,
            navController = navController,
            sharedPreferences = sharedPreferences,
            address = address,
            currentLocation = currentLocation,
            cameraPositionState = cameraPositionState,
            context = context,
            onGpsIconClick = {
                LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                    locationResult.lastLocation?.let { location ->
                        currentLocation = location
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LocationUtils.getPosition(location), 12f
                        )
                    }
                }
            }
        ) {
            searchScreenVisibility = true
        }
    }

}

@Composable
fun searchLocation(
    cameraPositionState: CameraPositionState,
    viewModal1: HomeAllProductsViewModal,
    shared_preferenceCommon: sharedpreferenceCommon,
    context: Context,
    navController: NavHostController,
    searchItemClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )

    ) {
        val placesClient: PlacesClient = Places.createClient(LocalContext.current)
        val predictions by viewModal1.predictions.collectAsState()
        CommonHeader(text = "Select Location", color = Color.Black) {
            navController.navigate(
                DashBoardNavRoute.Home.screen_route,
                NavOptions.Builder().setPopUpTo(navController.graph.startDestinationId, inclusive = false).build()
            )
        }
//        Text14_h2(
//            text = "Select Delivery Address", color = headingColor, modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.CenterHorizontally)
//                .padding(start = 20.dp, top = 20.dp)
//        )
//        Spacer(modifier = Modifier.height(12.dp))
        // Text inside the curved background
        CommonNumberField(
            trailingIcon = com.bumptech.glide.R.drawable.abc_ic_search_api_material,
            iconColor = Color.LightGray,
            text = remember { mutableStateOf("") },
            placeholder = stringResource(id = R.string.address),
            keyboardType=KeyboardType.Text,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            viewModal1.searchAddress(it, placesClient)
            // namestate.value = it.length >= 3
        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 10.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.homeicon), // Replace with your icon resource
//                contentDescription = "Location Icon",
//                modifier = Modifier
//                    .size(24.dp)
//
////                            colorFilter = ColorFilter.tint(seallcolor)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
////            Column(modifier = Modifier.clickable {
////
//////                scope.launch { viewModal.clearDatabase() }
//////                sharedpreferenceCommon.setSearchAddress(sharedpreferenceCommon.getCombinedAddress())
//////
//////                sharedpreferenceCommon.setPinCode(
//////                    Utils.extractSixDigitNumber(
//////                        sharedpreferenceCommon.getCombinedAddress()
//////                    ) ?: ""
//////                )
//////                val intent = Intent(context, HomeActivity::class.java)
//////                intent.flags =
//////                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//////                context?.startActivity(intent)
////            }) {
////                Text12_h1(
////                    text = "Use Your Current location",
////                    color = seallcolor,
////
////                    )
////            //    Text12_body1(text = sharedpreferenceCommon.getCombinedAddress())
////            }
////            Spacer(modifier = Modifier.weight(1f))
//
//            Image(
//                painter = painterResource(id = com.google.android.material.R.drawable.material_ic_keyboard_arrow_right_black_24dp), // Replace with your icon resource
//                contentDescription = "next",
//                modifier = Modifier
//                    .size(24.dp)
//
//            )
//
//        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider()

        LazyColumn {
            items(predictions) { prediction ->

                PredictionItem(prediction = prediction) {
                    val gc = Geocoder(context)
                    val list = gc.getFromLocationName(prediction.placeId, 1)
                    val address = list!![0]
                    val cameraPosition = CameraPosition.Builder()
                        .target(LatLng(address.latitude,address.longitude)) // Sets the center of the map to the address location
                        .zoom(12f) // Sets the zoom level
                        .build()

                    // Move the camera to the new position

                    cameraPositionState.position = cameraPosition
//                    val pincode = Utils.extractSixDigitNumber(it).toString()
//                    if (pincode.length > 4) {
//                        shared_preferenceCommon.setSearchAddress(it)
//
//                        shared_preferenceCommon.setPinCode(pincode)
//                        val intent = Intent(context, HomeActivity::class.java)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        context?.startActivity(intent)
//                    } else {
//                        Toast
//                            .makeText(
//                                context,
//                                "Please select with pincode",
//                                Toast.LENGTH_SHORT
//                            )
//                            .show()
//                    }
                }
            }


        }
    }

}
@Composable
private fun MyGoogleMap(
    viewModal: HomeAllProductsViewModal,
    isCameraIdle: Boolean,
    navController: NavHostController,
    sharedPreferences: sharedpreferenceCommon,
    address: String,
    currentLocation: Location,
    cameraPositionState: CameraPositionState,
    context: Context, onGpsIconClick: () -> Unit, clickAddress: () -> Unit
) {
    GpsIconButton(onIconClick = onGpsIconClick)

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            CommonHeader(text = "Select Location", color = Color.Black) {
                navController.navigate(
                    DashBoardNavRoute.Home.screen_route,
                    NavOptions.Builder().setPopUpTo(navController.graph.startDestinationId, inclusive = false).build()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            CommonTextFieldNonEditable(modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable {


                },
                text = remember {
                    mutableStateOf("")
                },
                placeholder = stringResource(id = R.string.address_enter)
            ) {
                clickAddress()
            }

        }

        Column(modifier = Modifier
            .fillMaxWidth()
            ) {
            CenteredTextAndImage{


            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier
                .background(whiteColor)
                .padding(bottom = 10.dp)) {

                if (!isCameraIdle) {

                    Row{
                        Image(
                            painter = painterResource(id = R.drawable.address),
                            contentDescription = "",
                            modifier = Modifier
                                .padding()
                                .align(Alignment.CenterVertically)
                                .size(30.dp)

                        )

                        Text12_body1(
                            text = address,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(10.dp)
                        )
                    }


                    CommonButton(
                        text = "Continue",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val pincode = Utils.extractSixDigitNumber(address).toString()
                        viewModal.insertAddresses(    cameraPositionState.position.target.latitude,   cameraPositionState.position.target.longitude,pincode,address = address)
                        sharedPreferences.setCombineAddress(address)
                        val intent = Intent(context, HomeActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    }
                } else {
                    Text14_h1(
                        color = black_111111,
                        text = "Fetching Address",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(start = 10.dp, top = 5.dp)
                    )
                    ShimmerAnimationCategoryAddress()

                }
            }

        }

    }
}
@Composable
fun CenteredTextAndImage(insertAddress:()->Unit) {
    // Parent Row to center align horizontally
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), // Adjust padding as needed
        horizontalArrangement = Arrangement.Center
    ) {
        // Inner Row with white background and rounded corners
        Row(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp) // Padding inside the background
                .clip(RoundedCornerShape(8.dp)) // Ensures the content is clipped to rounded corners
        ) {
            Image(
                painter = painterResource(id = R.drawable.address),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.width(5.dp)) // Space between Image and Text

            Text(
                text = "Locate Me",
                fontSize = 12.sp, // Adjust text size as needed
                color = titleColor, // Text color
                modifier = Modifier
                    .padding(10.dp).clickable{
                        insertAddress()

                    }
            )
        }
    }
}
@Composable
fun ShimmerAnimationCategoryAddress(
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerAddress(brush = brush)

}
@Composable
fun ShimmerAddress(
    brush: Brush
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Row (modifier = Modifier.fillMaxWidth()){
            Spacer(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .background(brush = brush)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .height(30.dp)
                    .background(brush = brush)
            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(50.dp)
                .background(brush = brush)
        )
    }
}


private fun getAddress(context: Context, latLng: LatLng): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    return addresses?.firstOrNull()?.getAddressLine(0) ?: "Address not found"
}

@Composable
private fun GpsIconButton(onIconClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
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
