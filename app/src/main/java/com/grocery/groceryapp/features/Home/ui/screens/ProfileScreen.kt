package com.grocery.groceryapp.features.Home.Modal

import android.net.Uri
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.HiltApplication
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.Text13_700
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.headingColor
import com.grocery.groceryapp.features.Home.ui.viewmodal.ProfileViewModal
import java.io.File




@Composable
fun profileScreen(
    navController: NavHostController,
    context: HomeActivity,sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    var selectedImage by remember { mutableStateOf<Uri?>(Uri.parse(sharedpreferenceCommon.getImageUri())) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImage = uri
        sharedpreferenceCommon.setImageUri(selectedImage.toString())

    }

    viewModal.setState()



    val size by animateDpAsState(
       targetValue = viewModal.orderstate.value,
//        tween(durationMillis = 3000, delayMillis = 200, easing = LinearOutSlowInEasing)
    spring(Spring.DampingRatioMediumBouncy)
    )
    Column() {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Image(
                painter = rememberImagePainter(sharedpreferenceCommon.getImageUri()),

                contentDescription = "splash image",
                modifier = Modifier
                    .width(if (size < 80.dp) size else 80.dp)
                    .height(if (size < 80.dp) size else 80.dp)
                    .size(64.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable {
                        launcher.launch("image/jpeg")
                    }
            )
            Column(modifier = Modifier

                .padding(start = 20.dp, top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                if (viewModal.responseLiveData.value.statusCode == 200) {


                    Text16_700(text = "${viewModal.responseLiveData.value.name?.name}")
                    Text14_400(text = "Valueable Customer")
                } else {

                    Text16_700(text = "Akshay Garg")
                    Text14_400(text = "Valueable Customer")


                }

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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable {


                navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route)

            }, Arrangement.SpaceBetween
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_orders_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text14_400(text = "Your Orders",color= headingColor, modifier = Modifier.padding(start = 15.dp, top = 5.dp))
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
                    painter = painterResource(id = R.drawable.ic_delicery_address),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text14_400(text = "Address Book",color= headingColor, modifier = Modifier
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
                        .size(20.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text14_400(
                    text = "Payment Methods",color= headingColor,
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
                        .size(20.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text14_400(
                    text = "Privacy Policy",color= headingColor,
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

    }


}