package com.grocery.groceryapp.features.Home.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text20_700
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.features.Home.ui.ui.theme.greyLightColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProfileViewModal
import com.squareup.moshi.Moshi

@Composable
fun orderDetil(data: String,viewModal: ProfileViewModal= hiltViewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(greyLightColor)
        .padding(start = 2.dp)) {
//val obj:AllOrdersHistoryList=data
//        Log.d("djdj",obj.message.toString())
      //  var testModel = Gson().fromJson(data, AllOrdersHistoryList.Order::class.java)
//        val moshi = Moshi.Builder().build()
//        val jsonAdapter = moshi.adapter(AllOrdersHistoryList.Order::class.java).lenient()
//        val userObject = jsonAdapter.fromJson(data)

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor,modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)) {
                Text20_700(text = "Order Details", modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))
                Text14_400(text = " item with this order")
                Row(Modifier.fillMaxWidth(),Arrangement.SpaceEvenly) {
                    Image(painter =  painterResource(id = R.drawable.ic_orders_icon), contentDescription = "", modifier = Modifier
                        .padding()
                        .width(20.dp)
                        .height(20.dp)
                        )
                    Text16_700(text = "Amul product")
                    Text16_700(text = "30")

                }
            }


        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor,modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)) {
                Text20_700(text = "Bill Details", modifier = Modifier
                    .fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween) {
                    Text14_400(text = "MRP", modifier = Modifier
                       )
                    Text16_700(text = "$5", modifier = Modifier
                      )


                }
                Row(modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween) {
                    Text14_400(text = "Delivery Charge", modifier = Modifier
                       )
                    Text16_700(text = "$5", modifier = Modifier
                      )


                }
                Row(modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween) {
                    Text14_400(text = "Grand Total", modifier = Modifier
                       )
                    Text16_700(text = "$10", modifier = Modifier
                        )


                }
            }


        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor,modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ){
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)) {
                Text20_700(text = "Order Details")

                Text14_400(text = "Order Id")
                Text16_700(text = "#kdnd")

                Text14_400(text = "Payment")
                Text16_700(text = "Paid online")

                Text14_400(text = "Delivery Address")
                Text16_700(text = "c810 habitat conscient")

                Text14_400(text = "Order Placed")
                Text16_700(text = "Placed on octo 23")
            }


        }



        
        
    }

}