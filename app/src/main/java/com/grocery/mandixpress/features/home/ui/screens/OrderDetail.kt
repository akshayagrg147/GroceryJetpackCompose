package com.grocery.mandixpress.features.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.data.modal.AllOrdersHistoryList
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.ProfileViewModal

@Composable
fun orderDetil(
    data: AllOrdersHistoryList.Orders,
    navcontroller: NavHostController,
    viewModal: ProfileViewModal = hiltViewModel()
) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(greycolor)
            .padding(start = 2.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(start = 2.dp), Arrangement.Center

            ) {
                Text16_h1(
                    text = "Summary", color = headingColor, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = whiteColor, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
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
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                        // Display headers only once
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text12_h1(
                                    text = "Product Id", color = headingColor,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column {
                                Text12_h1(
                                    text = "Product Name", color = headingColor,
                                    modifier = Modifier.width(130.dp)
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column {
                                Text12_h1(
                                    text = "Product Price", color = headingColor
                                )
                            }
                        }

// Display data for each item in orderList
                        if (data.orderList?.isNotEmpty() == true)
                            for (i in 0 until data.orderList.size) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp)
                                )
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    Arrangement.SpaceBetween
                                ) {
                                    if (data.orderList.isNotEmpty()) {
                                        Text10_h2(
                                            text = data.orderList.get(i)?.productId ?: "",
                                            color = greyLightColor,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        Text10_h2(
                                            text = data.orderList.get(i)?.productName ?: "",
                                            color = greyLightColor,
                                            modifier = Modifier.width(130.dp),
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        Text10_h2(
                                            text = "₹ ${data.orderList.get(i)?.productprice}",
                                            color = greyLightColor
                                        )
                                    }
                                }
                            }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )

                    }


                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)

                )
                Box(

                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                        .background(whiteColor)
                        .drawBehind {
                            drawRoundRect(color = Color.Red, style = stroke)
                        },


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
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text10_h2(
                                text = "Total", modifier = Modifier
                            )
                            if (data.orderList?.isNotEmpty() == true)
                                Text13_body1(text = "  ₹ ${data.totalOrderValue}")


                        }
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            Text10_h2(
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
                            Text10_h2(
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

                        .padding(horizontal = 5.dp)
                        .clip(RoundedCornerShape(2.dp, 2.dp, 0.dp, 0.dp))


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
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )
                        Text12_body1(text = "Order Id")
                        if (data.orderList?.isNotEmpty() == true)
                            Text10_h2(text = "#${data.orderId}")
                        Spacer(modifier = Modifier.height(5.dp))

                        Text12_body1(text = "Payment")
                        if (data.orderList?.isNotEmpty() == true)
                            Text10_h2(text = "${data.paymentmode}")
                        Spacer(modifier = Modifier.height(5.dp))
                        Text12_body1(text = "Delivery Address")
                        if (data.orderList?.isNotEmpty() == true)
                            Text10_h2(text = "${data.address}")
                        Spacer(modifier = Modifier.height(5.dp))
                        Text12_body1(text = "Order Placed")
                        if (data.orderList?.isNotEmpty() == true)
                            Text10_h2(text = "${data.createdDate}")
                    }


                }


            }
        }
    }


}