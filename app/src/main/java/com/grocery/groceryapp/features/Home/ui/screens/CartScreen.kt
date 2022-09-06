package com.grocery.groceryapp.features.Home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen() {


    var search = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text24_700(text = "MY Cart", color = Color.Black)
        }


        LazyColumn(
            //  verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .padding(top = 60.dp)
            // .verticalScroll(state = scrollState)

        ) {

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
            items(5) {
                ItemEachRow()
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            elevation = 0.dp,
            shape = CutCornerShape(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .background(colorResource(id = R.color.green_700)),
                contentAlignment = Alignment.Center
            ) {
                Text24_700(
                    text = "Go to Checkout", color = Color.White, modifier = Modifier
                        .padding(vertical = 20.dp)
                )

            }
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
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text20_700(text = "Bell Peper Red", modifier = Modifier.align(Alignment.CenterVertically))
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
                        CommonButton(icon = R.drawable.minus){

                        }
                        Text16_700(
                            text = "1",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 20.dp),
                            color = Color.Black
                        )
                        CommonButton(icon = R.drawable.add){

                        }
                    }
                    Text14_400(
                        text = "$4.99",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

    }

}