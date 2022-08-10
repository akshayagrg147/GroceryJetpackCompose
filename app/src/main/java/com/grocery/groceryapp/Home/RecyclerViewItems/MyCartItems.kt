package com.grocery.groceryapp.Home.RecyclerViewItems

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text20_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.ui.theme.blackColor
import com.grocery.groceryapp.ui.theme.headingColor
import com.grocery.groceryapp.ui.theme.redColor
import com.grocery.groceryapp.ui.theme.strokeColor

@Composable
fun cartitems() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text24_700(
                text = "Bell pepper Red", color = headingColor,
                modifier = Modifier
                    .weight(1f)
                    .background(Blue)
            )
            Icon(
                painter = painterResource(id = R.drawable.heart_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .weight(0.14F),
                tint = redColor
            )
        }

        Text14_400(
            text = "1 kg,price", color = headingColor,
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.heart_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .weight(0.1F),
                tint = redColor
            )
            Text16_700(
                text = "1",
                color = headingColor,
                modifier = Modifier.weight(0.1F)
            )

            Icon(
                painter = painterResource(id = R.drawable.heart_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .weight(0.1F),
                tint = redColor
            )

        }

    }


}

@Composable
fun ItemEachRow() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ornge_carrot),
                contentDescription = "",
                modifier = Modifier
                    .size(70.dp)
                    .align(CenterVertically)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text20_700(text = "Bell Peper Red", modifier = Modifier.align(CenterVertically))
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text14_400(text = "1 kg")
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        CommonButton(icon = R.drawable.minus)
                        Text16_700(
                            text = "1",
                            modifier = Modifier
                                .align(CenterVertically)
                                .padding(horizontal = 20.dp),
                            color = Black
                        )
                        CommonButton(icon = R.drawable.add)
                    }
                    Text14_400(
                        text = "$4.99",
                        color = blackColor,
                        modifier = Modifier.align(CenterVertically)
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray)
        )

    }

}

@Composable
fun CommonButton(
    icon: Int
) {

    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(17.dp),
        border = BorderStroke(1.dp, strokeColor)
    ) {
        Box(modifier = Modifier.background(White), contentAlignment = Center) {
            androidx.compose.material.Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = Unspecified,
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}