package com.grocery.groceryapp.Home.Modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text20_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.ui.theme.blackColor
import com.grocery.groceryapp.ui.theme.redColor

@Composable
fun profileScreen(){
    Column() {
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, top = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ornge_carrot),

                contentDescription = "splash image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Column() {
                Text20_700(text = "Akshay Garg")
                Text24_700(text = "Address line")

            }

            
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_orders_icon)
                    ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp).padding(start = 10.dp , top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Orders", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp).padding(top = 5.dp)
                ,
                tint = blackColor
            )

            
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_my_details_icon) ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp).padding(start = 10.dp , top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "My Details", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp).padding(top = 5.dp)
                ,
                tint = blackColor
            )


        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_delicery_address),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp).padding(start = 10.dp , top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Delivery Address", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp).padding(top = 5.dp)
                ,
                tint = blackColor
            )


        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_paymentmethod),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp).padding(start = 10.dp , top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Payment Methods", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp).padding(top = 5.dp)
                ,
                tint = blackColor
            )


        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),Arrangement.SpaceBetween) {
            Row(){
                Icon(
                    painter = painterResource(id =R.drawable.ic_promocode),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp).padding(start = 10.dp , top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text16_700(text = "Promo Code", modifier = Modifier.padding(start = 15.dp, top = 5.dp))
            }
            Icon(
                painter = painterResource(id =R.drawable.ic_rightarrow),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp).padding(top = 5.dp)
                ,
                tint = blackColor
            )


        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray).padding(start = 5.dp, end = 20.dp)
        )
        
    }
    
}