package com.grocery.groceryapp.features.Home.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text20_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.greycolor
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProfileViewModal

@Composable
fun orderDetil(
    data: AllOrdersHistoryList,
    navcontroller: NavHostController,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(greycolor)
            .padding(start = 2.dp)
    ) {
        Text24_700(
            text = "Order Details", modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor, modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Text14_400(text = " item with this order")
if(data.list?.isNotEmpty()==true)
                for (i in 0 until data.list[0].orderList?.size!!) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(5.dp), Arrangement.spacedBy(10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_orders_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .padding()
                                .width(20.dp)
                                .height(20.dp)
                        )
                        if(data.list.isNotEmpty()) {
                        Text16_700(text = data.list[0].orderList?.get(i)?.productName ?: "")
                        Text16_700(text = "  ₹ ${data.list[0].orderList?.get(i)?.productprice}")

                    } }
                }

            }


        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor, modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text20_700(
                    text = "Bill Details", modifier = Modifier
                        .fillMaxWidth()
                )
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text14_400(
                        text = "Total", modifier = Modifier
                    )
                    if(data.list?.isNotEmpty()==true)
                    Text16_700(text = "  ₹ ${data.list[0].totalOrderValue}")


                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text14_400(
                        text = "Delivery Charge", modifier = Modifier
                    )

                    Text(
                        text = "  ₹ 30",
                        fontSize = 13.sp,
                        color = bodyTextColor,
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )


                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text14_400(
                        text = "Grand Total", modifier = Modifier
                    )
                    if(data.list?.isNotEmpty()==true)
                    Text16_700(
                        text = "  ₹ ${data.list[0].totalOrderValue}", modifier = Modifier
                    )


                }
            }


        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = whiteColor, modifier = Modifier
                .fillMaxWidth()


                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text20_700(text = "Order Details")

                Text14_400(text = "Order Id")
                if(data.list?.isNotEmpty()==true)
                Text16_700(text = "#${data.list[0].orderId}")

                Text14_400(text = "Payment")
                if(data.list?.isNotEmpty()==true)
                    Text16_700(text = "${data.list[0].paymentmode}")

                Text14_400(text = "Delivery Address")
                if(data.list?.isNotEmpty()==true)
                Text16_700(text = "${data.list[0].address}")

                Text14_400(text = "Order Placed")
                if(data.list?.isNotEmpty()==true)
                Text16_700(text = "${data.list[0].createdDate}")
            }


        }


    }

}