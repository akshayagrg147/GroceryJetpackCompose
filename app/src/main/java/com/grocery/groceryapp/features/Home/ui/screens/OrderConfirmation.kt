package com.grocery.groceryapp.features.Home.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.AddressViewModal
import com.wajahatkarim3.compose.books.ui.model.PassingOrderResponse

@Composable
fun OrderConfirmation(
    data: PassingOrderResponse,
) {
    val success = rememberSaveable { data }
    if(data.totalOrderValue!=null)
    Column(modifier = Modifier.fillMaxSize()) {
        Text24_700(text = "Order Confirmed",  modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.Black)

        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Center

        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp),
                shape = RoundedCornerShape(8.dp), elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(whiteColor)
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tick_circle),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        contentDescription = ""
                    )
                    Text16_700(
                        text = "Your order is complete",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 5.dp)
                    )
                    Text14_400(
                        text = "Your will receive a confirmation email with order details",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)

                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order Id:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "Bk12H2121",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }
                }
            }

        }


}
    else
    Column(modifier = Modifier.fillMaxSize()) {
        Text24_700(text = "Order Failed",  modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.Black)

        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Center

        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp),
                shape = RoundedCornerShape(8.dp), elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(whiteColor)
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.failed),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        contentDescription = ""
                    )
                    Text16_700(
                        text = "Failed Order",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 5.dp)
                    )
                    Text14_400(
                        text = "Some missing information",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)

                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order Id:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "Bk12H1121",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }
                }
            }

        }





    }


}