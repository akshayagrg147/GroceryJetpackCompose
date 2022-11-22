package com.grocery.groceryapp.features.Home.Modal

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.viewmodal.ProfileViewModal

@Composable
fun profileScreen(
    navController: NavHostController,
    context: HomeActivity,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    var bounc=true

    viewModal.setState()


    val size by animateDpAsState(
       targetValue = viewModal.orderstate.value,
//        tween(durationMillis = 3000, delayMillis = 200, easing = LinearOutSlowInEasing)
    spring(Spring.DampingRatioMediumBouncy)
    )
    Column() {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                if (viewModal.responseLiveData.value.statusCode == 200) {
                    Image(
                        painter = painterResource(id = R.drawable. avatar),

                        contentDescription = "splash image",
                        modifier = Modifier
                            .width(if(size<100.dp) size else 100.dp)
                            .height( if(size<100.dp) size else 100.dp)
                    )
                    Text16_700(text = "${viewModal.responseLiveData.value.name?.name}")
                    Text14_400(text = "Valueable Customer")
                } else {
                    Image(
                        painter = painterResource(id = R.drawable. avatar),

                        contentDescription = "splash image",
                        modifier = Modifier
                            .width(if(size<100.dp) size else 100.dp)
                            .height( if(size<100.dp) size else 100.dp)
                    )
                    Text16_700(text = "Akshay Garg")
                    Text14_400(text = "Valueable Customer")

//                    Toast.makeText(
//                        context,
//                        "${viewModal.responseLiveData.value.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
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
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text16_700(text = "Orders", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp),
                tint = blackColor
            )


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
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text16_700(text = "Add Address", modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp)
                    .clickable {
                        navController.navigate(DashBoardNavRoute.AddressScreen.screen_route)
                    })
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp),
                tint = blackColor
            )


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
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text16_700(
                    text = "Payment Methods",
                    modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp),
                tint = blackColor
            )


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
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp),
                    tint = blackColor
                )
                Text16_700(
                    text = "Privacy Policy",
                    modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp),
                tint = blackColor
            )


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