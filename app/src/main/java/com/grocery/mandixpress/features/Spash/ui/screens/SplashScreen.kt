package com.grocery.mandixpress.features.Spash

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text14_h1
import com.grocery.mandixpress.features.Home.ui.screens.HomeActivity
import com.grocery.mandixpress.features.Home.ui.ui.theme.availColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    context: Context,
    sharedpreferenceCommon: sharedpreferenceCommon
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = com.grocery.mandixpress.R.drawable.logo),

            contentDescription = "splash image",
            modifier = Modifier
                .padding()

                .align(Alignment.CenterVertically)

        )

//        Column(verticalArrangement = Arrangement.SpaceBetween) {
//            Text14_h1(text = "Mandi", modifier = Modifier.padding(top = 5.dp), color = Color.White)
//            Text12_body1(
//                text = "Online Groceries",
//                modifier = Modifier.padding(),
//                color = Color.White
//            )
//
//        }


    }

    LaunchedEffect(key1 = Unit){
        delay(500)
        Log.d("groceryApp", "SplashScreen:${sharedpreferenceCommon.getJwtToken()} ")
        if (sharedpreferenceCommon.getJwtToken().isNotEmpty()) {
            if (sharedpreferenceCommon.getCombinedAddress().isNotEmpty())
                context.startActivity(Intent(context, HomeActivity::class.java))
            else {

                navController.navigate(ScreenRoute.LocateMeScreen.route)
            }
        }
        else{

            navController.navigate(ScreenRoute.LoginScreen.route)
        }

    }


}