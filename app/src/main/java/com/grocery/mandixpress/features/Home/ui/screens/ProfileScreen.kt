package com.grocery.mandixpress.features.Home.Modal

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.LoginActivity
import com.grocery.mandixpress.R
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.Text13_body1
import com.grocery.mandixpress.Utils.Text16_h1
import com.grocery.mandixpress.data.modal.UserResponse
import com.grocery.mandixpress.features.Home.ui.screens.HomeActivity
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProfileEvent
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProfileViewModal
import java.util.jar.Manifest


@Composable
fun profileScreen(
    navController: NavHostController,
    context: HomeActivity, sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    var selectedImage by remember { mutableStateOf<Uri?>(Uri.parse(sharedpreferenceCommon.getImageUri())) }
    val callClicked=remember{
        mutableStateOf(false)
    }
    val profileResponse by viewModal.responseLiveData.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri

            sharedpreferenceCommon.setImageUri(selectedImage.toString())

        }
    LaunchedEffect(key1 = Unit) {
        viewModal.onEvent(ProfileEvent.callingUserProfile(sharedpreferenceCommon.getMobileNumber()))

    }
    Column() {
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            if (profileResponse.data?.statusCode == 200) {

                ProfileSection(
                    if (sharedpreferenceCommon.getImageUri() != "")
                        sharedpreferenceCommon.getImageUri().toUri()
                    else selectedImage, viewModal, launcher, navController, Modifier
                )



                ProfileDescription(
                    displayName = "${profileResponse.data?.name?.name}",
                    description = "Valueable Customer",

                    )
            } else {
                ShimmerAnimation()

            }
        }

        //profile section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(whiteColor)
                .padding(8.dp)
        )
        {

            Column(
                modifier = Modifier
                    .background(color = whiteColor)
            )
            {
                Spacer(modifier = Modifier.height(1.dp))
                if (profileResponse.data?.statusCode == 200)
                    StatSection(modifier = Modifier, navController, profileResponse.data!!)
                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
                    Row() {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delicery_address),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(text = "Address Book",
                            color = headingColor,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 5.dp)
                                .clickable {
                                    navController.navigate(DashBoardNavRoute.AddressScreen.screen_route)
                                })
                    }


                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )
              /*  Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween) {
                    Row() {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_paymentmethod),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "Payment Methods", color = headingColor,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }


                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray).padding(start = 5.dp, end = 20.dp))*/
              if(callClicked.value){
                  AutoRequestCallPermission(viewModal.getDeliveryBoyNumber())
              }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.clickable {
                        val applicationNameId = context.applicationInfo.labelRes
                        val appPackageName = context.packageName
                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "text/plain"
                        i.putExtra(Intent.EXTRA_SUBJECT, context.getString(applicationNameId))
                        val text = "Install this cool application: "
                        val link = "https://play.google.com/store/apps/details?id=$appPackageName"
                        i.putExtra(Intent.EXTRA_TEXT, "$text $link")
                        context.startActivity(Intent.createChooser(i, "Share link:"))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "share app", color = headingColor,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }


                }

                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.clickable {
                     callClicked.value = true

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.contact_us),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "Contact us", color = headingColor,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }


                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.clickable { navController.navigate(DashBoardNavRoute.PrivacyPolicyScreen.screen_route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.terms_and_conditions),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "Terms & condition", color = headingColor,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }


                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )
  /*              Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.clickable { navController.navigate(DashBoardNavRoute.PrivacyPolicyScreen.screen_route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_promocode),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "Privacy Policy", color = headingColor,
                            modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                        .padding(start = 5.dp, end = 20.dp)
                )*/
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.clickable { navController.navigate(DashBoardNavRoute.PrivacyPolicyScreen.screen_route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.log_out),
                            contentDescription = "",
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 10.dp, top = 5.dp),
                            tint = blackColor
                        )
                        Text13_body1(
                            text = "Log out", color = headingColor,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 5.dp)
                                .clickable {

                                    if( viewModal.clearSharedPreference()){
                                        val intent = Intent(context, LoginActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        context.startActivity(intent)
                                    }

                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.Center
                ) {
                    Text13_body1(text = "v 1.0.0", color = headingColor)

                }


            }


        }


    }


}

fun uriToBitmap(uri: Uri?, context: Context): Bitmap {
    val bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images
            .Media.getBitmap(context.contentResolver, uri)

    } else {
        val source = ImageDecoder
            .createSource(context.contentResolver, uri!!)
        ImageDecoder.decodeBitmap(source)

    }
    return bitmap

}

@Composable
fun ProfileSection(
    uri:
    Uri?, viewModal: ProfileViewModal, launcher:
    ManagedActivityResultLauncher<String, Uri?>, navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Log.d("profileScreen", "profileScreen 11: ${uri}")
    val context = LocalContext.current
    Column(modifier = modifier) {
        Column(

            modifier = Modifier

                .padding(horizontal = 20.dp)
        ) {

            Image(
                painter = rememberImagePainter(
                    R.drawable.avatar
//                   try {
//                       uri
//                   }
//                   catch (e:Exception)
//                   {
//                       R.drawable.avatar
//                   }

                ),
                contentDescription = "splash image",
                modifier = Modifier
//                    .width(if (size < 80.dp) size else 80.dp)
//                    .height(if (size < 80.dp) size else 80.dp)
                    .padding(20.dp)
                    .size(50.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable {
                        //    launcher.launch("image/jpeg")
                    },
                contentScale = ContentScale.Crop
            )


        }


    }
}

@Composable
fun StatSection(modifier: Modifier = Modifier, navController: NavController, data: UserResponse) {
    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ProfileStat(numberText = data.name?.cancel.toString(), text = "Cancelled") {
            navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)
        }
        ProfileStat(numberText = data.name?.order.toString(), text = "Orders") {
            navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)
        }
        ProfileStat(numberText = data.name?.deliver.toString(), text = "Delivered") {
            navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)
        }
    }
}


@Composable
fun ProfileStat(
    numberText: String,
    text: String,
    modifier: Modifier = Modifier, orderclicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { orderclicked() }
    ) {
        Text16_h1(
            text = numberText, color = blackColor
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text13_body1(text = text, color = grey)
    }
}

@Composable
fun ShimmerAnimation(
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

    ShimmerItem(brush = brush)

}


@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .padding(start = 50.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(50.dp)) {
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .background(brush = brush)
            )
Column() {
    Spacer(
        modifier = Modifier
            .width(90.dp)
            .height(20.dp)
            .background(brush = brush)
    )
    Spacer(modifier = Modifier.height(10.dp))
    Spacer(
        modifier = Modifier
            .width(90.dp)
            .height(20.dp)
            .background(brush = brush)
    )
}

        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .background(brush = brush)
            )

            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .background(brush = brush)
            )
        }
    }


}







@Composable
fun ProfileDescription(
    displayName: String,
    description: String,


    ) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text13_body1(
            text = displayName,color=Purple700

            )
        Text13_body1(
            text = description,color= bodyTextColor

            )
    }
}



@Composable
fun AutoRequestCallPermission(phoneNumber: String) {
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, you can make the call
            makeCall(context, phoneNumber)
        } else {
            // Permission denied, handle the denial or show an explanation

        }
    }

    val isPermissionGranted = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Request the CALL_PHONE permission
            requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
        } else {
            // Permission is already granted
            isPermissionGranted.value = true
            makeCall(context, phoneNumber)
        }
    }


}

fun makeCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$phoneNumber")

    // Check if the app has the CALL_PHONE permission before launching the call
    if (ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        context.startActivity(intent)
    } else {
        // Handle the case where the app does not have the CALL_PHONE permission

    }
}
