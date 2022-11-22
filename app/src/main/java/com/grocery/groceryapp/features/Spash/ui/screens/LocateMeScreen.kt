package com.grocery.groceryapp.features.Spash

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.CommonButton
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.features.Home.ui.screens.HomeActivity

@Composable
fun locateMeScreen(navController: NavHostController, context: Context) {

    Column( verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()) {

        Column( modifier = Modifier.padding(horizontal = 20.dp).fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.location_icon),
                    contentDescription = "splash image",
                    modifier = Modifier.padding(vertical = 10.dp).align(Alignment.CenterHorizontally)
                )
                Text24_700(
                        text = "Get our service in nearby of your locations",
                        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
                    )
            Column( modifier = Modifier.padding(bottom = 10.dp).fillMaxSize(), verticalArrangement = Arrangement.Bottom)
            {
                CommonButton(
                    text = "Locate Me",
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    context.startActivity(Intent(context, HomeActivity::class.java))

                }
            }

            }




    }

}