package com.grocery.mandixpress.connectionState.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grocery.mandixpress.LoginActivity
import com.grocery.mandixpress.Utils.CommonButton
import com.grocery.mandixpress.Utils.Text10_h2
import com.grocery.mandixpress.Utils.launchActivity
import com.grocery.mandixpress.features.home.ui.ui.theme.seallcolor
import com.grocery.mandixpress.features.home.ui.ui.theme.whiteColor

@Composable
fun onlineconnection(call: (Boolean) -> Unit) {
    val retryclicked = remember {
        mutableStateOf(false)
    }
    if (retryclicked.value) {
        call(true)
//        LocalContext.current.launchActivity<LoginActivity>() {
//        }

    }
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(whiteColor),

            ) {

            Image(
                painter = painterResource(id = com.grocery.mandixpress.R.drawable.ic_baseline_wifi_off_24),

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
            ) {
                retryclicked.value = true
            }

            Text10_h2(
                text = "Please check your internet connection",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(CenterHorizontally)
            )
        }
    }


}