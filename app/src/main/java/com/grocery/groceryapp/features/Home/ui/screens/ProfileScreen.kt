package com.grocery.groceryapp.features.Home.Modal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.Text13_body1
import com.grocery.groceryapp.Utils.Text16_h1
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.grey
import com.grocery.groceryapp.features.Home.ui.ui.theme.headingColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor
import com.grocery.groceryapp.features.Home.ui.viewmodal.ProfileViewModal


@Composable
fun profileScreen(
    navController: NavHostController,
    context: HomeActivity, sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    var selectedImage by remember { mutableStateOf<Uri?>(Uri.parse(sharedpreferenceCommon.getImageUri())) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            selectedImage = uri
            sharedpreferenceCommon.setImageUri(selectedImage.toString())

        }
    Column() {
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()){
            ProfileSection(if(sharedpreferenceCommon.getImageUri()!="")
                sharedpreferenceCommon.getImageUri().toUri()
            else selectedImage, viewModal, launcher, navController,Modifier
            )

            if (viewModal.responseLiveData.value.statusCode == 200) {

                ProfileDescription(
                    displayName = "${viewModal.responseLiveData.value.name?.name}",
                    description = "Valueable Customer",

                    )
            } else {
                ProfileDescription(
                    displayName = "Akshay Garg",
                    description = "Valueable Customer",

                    )

            }
        }

        //profile section
        Box(modifier = Modifier
            .fillMaxSize()
            .background(whiteColor)
            .padding(8.dp))
        {

            Column(modifier = Modifier
                .background(color = whiteColor)
               )
            {
                Spacer(modifier = Modifier.height(6.dp))
                StatSection(modifier = Modifier, navController)
                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
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
                        Text13_body1(text = "Address Book", color = headingColor, modifier = Modifier
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
                        .background(Color.Gray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
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
                            painter = painterResource(id = R.drawable.ic_promocode),
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
                        .background(Color.Gray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "plain/text"
                        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("support@suprixsolution.in"))
                        intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
                        intent.putExtra(Intent.EXTRA_TEXT, "mail body")
                       context. startActivity(Intent.createChooser(intent, ""))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_promocode),
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
                        .background(Color.Gray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
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
                        .background(Color.Gray)
                        .padding(start = 5.dp, end = 20.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.SpaceBetween
                ) {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp), Arrangement.Center
                ){
                    Text13_body1(text = "v 1.0.0", color = headingColor)

                }


            }


        }



    }


}
fun uriToBitmap(uri:Uri?,context: Context) : Bitmap{
     val bitmap =  if (Build.VERSION.SDK_INT < 28) {
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
    val context = LocalContext.current
    Column(modifier = modifier) {
        Column(

            modifier = Modifier

                .padding(horizontal = 20.dp)
        ) {

            Image(
                painter = rememberImagePainter(
                   try {
                       uri
                   }
                   catch (e:Exception)
                   {
                       R.drawable.avatar
                   }

                ),
                contentDescription = "splash image",
                modifier = Modifier
//                    .width(if (size < 80.dp) size else 80.dp)
//                    .height(if (size < 80.dp) size else 80.dp)
                    .size(100.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable {
                        launcher.launch("image/jpeg")
                    },
                contentScale = ContentScale.Crop
            )


        }


    }
}

@Composable
fun StatSection(modifier: Modifier = Modifier, navController: NavController) {
    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ProfileStat(numberText = "30", text = "Cancelled") {
            navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)
        }
        ProfileStat(numberText = "100", text = "Orders") {
            navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)
        }
        ProfileStat(numberText = "70", text = "Delivered") {
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
            text = displayName,

            )
        Text13_body1(
            text = description,

            )
    }
}