package com.grocery.groceryapp.Home.RecyclerViewItems

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.grocery.groceryapp.Home.ui.MainProducts
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.ui.theme.headingColor
import com.grocery.groceryapp.ui.theme.seallcolor

@Composable
fun SearchItems(data: MainProducts) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = data.color

    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
            Image(
                painter = painterResource(id = data.image),

                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally),


            )
            Text24_700(
                text = data.name, color = headingColor,
                modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally)
            )


        }
    }
}