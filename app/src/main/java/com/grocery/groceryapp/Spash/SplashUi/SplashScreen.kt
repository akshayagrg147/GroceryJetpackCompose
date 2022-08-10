package com.grocery.groceryapp.Spash

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.ui.theme.availColor

@Composable
fun SplashScreen(navController: NavHostController, context: Context){
    Row(
        modifier = Modifier.fillMaxSize().background(availColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.white_carrot),

            contentDescription = "splash image",
            modifier = Modifier
                .padding()
                .width(50.dp).height(50.dp).align(Alignment.CenterVertically)

        )

        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Text24_700(text = "nectar", modifier = Modifier.padding(top = 5.dp),color = Color.White)
            Text14_400(text = "Online Groceries", modifier = Modifier.padding(), color = Color.White)

        }


    }
    Handler(Looper.getMainLooper()).postDelayed({
        navController.navigate(ScreenRoute.SignUpScreen.route)
    },500)



}