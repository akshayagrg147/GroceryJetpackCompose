package com.grocery.mandixpress.features.Home.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.data.modal.AllOrdersHistoryList
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProfileViewModal

@Composable
fun orderDetil(
    data: AllOrdersHistoryList.Orders,
    navcontroller: NavHostController,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(greycolor)
            .padding(start = 2.dp),Arrangement.Center

    ) {
        Text16_h1(
            text = "Summary",color= sec20timer, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            textAlign= TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            elevation = 2.dp,
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
                Text12_h1(
                    text = "Ordered Items", modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center, color = iconColor
                )
                Row(modifier=Modifier.fillMaxWidth(),Arrangement.SpaceEvenly) {

                    Text12_body1(text = " Product Id", textAlign = TextAlign.Center)
                    Text12_body1(text = " Product Name", textAlign = TextAlign.Center)
                    Text12_body1(text = " Product Price", textAlign = TextAlign.Center)

                }
                if (data.orderList?.isNotEmpty() == true)
                    for (i in 0 until data.orderList.size!!) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(5.dp), Arrangement.SpaceEvenly
                        ) {
//                            Image(
//                                painter = painterResource(id = com.grocery.mandixpress.R.drawable.order_icon),
//                                contentDescription = "",
//                                modifier = Modifier
//                                    .padding()
//                                    .width(20.dp)
//                                    .height(20.dp)
//                            )

                            if (data.orderList.isNotEmpty()) {
                                Text13_body1(
                                    text = data.orderList.get(i)?.productId ?: ""
                                )
                                Text13_body1(
                                    text = data.orderList.get(i)?.productName ?: ""
                                )
                                Text13_body1(text = "  ₹ ${data.orderList.get(i)?.productName}")

                            }
                            if (data.orderList.isNotEmpty()) {
                                Text13_body1(
                                    text = data.orderList.get(i)?.productName ?: ""
                                )
                                Text13_body1(text = "  ₹ ${data.orderList.get(i)?.productprice}")

                            }
                        }
                    }

            }


        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)

        )
        Card(
            elevation = 2.dp,
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
                Text12_h1(
                    text = "Bill Details", modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center, color = iconColor
                )
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text12_body1(
                        text = "Total", modifier = Modifier
                    )
                    if (data.orderList?.isNotEmpty() == true)
                        Text13_body1(text = "  ₹ ${data.totalOrderValue}")


                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text12_body1(
                        text = "Delivery Charge", modifier = Modifier
                    )

                    Text(
                        text = "  ₹ 30",
                        fontSize = 12.sp,
                        color = bodyTextColor,
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )


                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text12_body1(
                        text = "Grand Total", modifier = Modifier
                    )
                    if (data.orderList?.isNotEmpty() == true)
                        Text13_body1(
                            text = "  ₹ ${data.totalOrderValue}", modifier = Modifier
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
            elevation = 2.dp,
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
                Text12_h1(
                    text = "Order Details", modifier = Modifier
                        .fillMaxWidth(), textAlign = TextAlign.Center, color = iconColor
                )
                Text12_body1(text = "Order Id")
                if (data.orderList?.isNotEmpty() == true)
                    Text12_body1(text = "#${data.orderId}")
                Spacer(modifier = Modifier.height(5.dp))

                Text12_body1(text = "Payment")
                if (data.orderList?.isNotEmpty() == true)
                    Text12_body1(text = "${data.paymentmode}")
                Spacer(modifier = Modifier.height(5.dp))
                Text12_body1(text = "Delivery Address")
                if (data.orderList?.isNotEmpty() == true)
                    Text12_body1(text = "${data.address}")
                Spacer(modifier = Modifier.height(5.dp))
                Text12_body1(text = "Order Placed")
                if (data.orderList?.isNotEmpty() == true)
                    Text12_body1(text = "${data.createdDate}")
            }


        }


    }

}