package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor

@Composable
fun ProfileScreenNavigation(context: Activity) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.OrderHistory.route) {
        composable(ScreenRoute.OrderHistory.route) {
            OrderHistoryScreen(navController, context)
        }
    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OrderHistoryScreen(
    navHostController: NavHostController,

    context: Activity
) {

    val scope = rememberCoroutineScope()
    val sheet = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CommonHeader(text = "Order History") {
            context.finish()
        }

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
            // .height(400.dp)
        ) {
            LazyColumn(
                //  verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize().padding(bottom = 15.dp)
                // .verticalScroll(state = scrollState)

            ){

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
                items(5){
                    ItemEachRow()
                }

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