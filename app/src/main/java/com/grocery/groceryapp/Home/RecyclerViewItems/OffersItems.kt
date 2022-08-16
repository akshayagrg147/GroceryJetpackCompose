package com.grocery.groceryapp.Home.RecyclerViewItems

import android.content.Context
import android.content.Intent
import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.Home.ui.OrderDetailScreen
import com.grocery.groceryapp.Home.ui.OrdersActivity
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.ui.theme.headingColor
import com.grocery.groceryapp.ui.theme.redColor
import com.grocery.groceryapp.ui.theme.seallcolor

@Composable
fun offerItems(context:Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable {
            context.startActivity(
                Intent(
                    context,
                    OrderDetailScreen::class.java
                )
            )
        }

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.banana),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = "Orange Carror", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "7 pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "$4.99",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )

                Icon(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                      modifier = Modifier.padding(start = 100.dp),
                    tint = redColor
                )
            }


        }
    }
}
@Composable
fun SellingItems() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bell_peeper),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = "Orange Carror", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "7 pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "$4.99",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )

                Icon(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 100.dp),
                    tint = redColor
                )
            }


        }
    }
}