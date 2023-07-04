package com.grocery.groceryapp.connectionState.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.CommonButton
import com.grocery.groceryapp.Utils.Text10_h2
import com.grocery.groceryapp.features.Home.ui.ui.theme.seallcolor
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun onlineconnection(call:(Boolean)->Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = 8.dp
    ){
        Column(
            modifier = Modifier.padding(20.dp)
                .background(whiteColor),

        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),

                contentDescription = "",
                modifier = Modifier
                    .padding()
                    .width(20.dp)
                    .height(20.dp)
                    .align(CenterHorizontally)


            )
            CommonButton(
                text = "Retry",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                backgroundColor = seallcolor
            ){
                call(true)
            }

            Text10_h2(
                text = "Please check your internet connection",
                modifier = Modifier.padding(top = 20.dp).align(CenterHorizontally)
            )
        }
    }



}