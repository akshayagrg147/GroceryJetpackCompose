package com.grocery.groceryapp.features.Home.Modal

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.features.Home.ui.HomeActivity
import com.grocery.groceryapp.features.Home.ui.OrdersActivity
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.UserResponse
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProductByIdViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProfileViewModal

@Composable
fun profileScreen(navController: NavHostController, context: HomeActivity,viewModal: ProfileViewModal = hiltViewModel()){

    Column() {
        viewModal.callingUserDetails()
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp)) {
            Image(
                painter = rememberAsyncImagePainter(viewModal.responseLiveData.value.profileImage),

                contentDescription = "splash image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Column() {
                if(viewModal.responseLiveData.value.statusCode==200) {
                    Text16_700(text = "${viewModal.responseLiveData.value.name}")
                    Text14_400(text = "Valueable Customer")
                }
                else
                {
                    Toast.makeText(context, "${viewModal.responseLiveData.value.message}", Toast.LENGTH_SHORT).show()
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
                context.startActivity(
                    Intent(
                        context,
                        OrdersActivity::class.java
                    )
                )
            },Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_orders_icon)
                    ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Orders", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp)
                ,
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_my_details_icon) ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "My Details", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp)
                ,
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_delicery_address),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Add Address", modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp)
                    .clickable {
                        navController.navigate(BottomNavItem.AddressScreen.screen_route)
                    })
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp)
                ,
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_paymentmethod),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Payment Methods", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp)
                ,
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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_promocode),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 10.dp, top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Promo Code", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(top = 5.dp)
                ,
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