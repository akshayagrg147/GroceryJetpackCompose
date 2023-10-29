package com.grocery.mandixpress.features.home.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text12_h1
import com.grocery.mandixpress.Utils.Text13_body1

import com.grocery.mandixpress.common.Utils.Companion.showNotification
import com.grocery.mandixpress.data.modal.OrderIdResponse
import com.grocery.mandixpress.features.home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.home.ui.ui.theme.whiteColor
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal


@Composable
fun OrderConfirmation(
    data: OrderIdResponse,
    navController: NavHostController, viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder(LocalContext.current))
            } else {
                add(GifDecoder())
            }
        }
        .build()

    BackHandler {
        navController.popBackStack(DashBoardNavRoute.Home.screen_route, false)
        //   navController.popBackStack(0, false)

    }
    if (data.statusCode == 200) {
        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        showNotification(LocalContext.current, "Order Placed", "")
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                viewModal.deleteCartItems()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .background(whiteColor)
                            .padding(20.dp)
                            .clickable { }) {
                        Box(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                            Image(
                                painter = rememberImagePainter(
                                    imageLoader = imageLoader,
                                    data = com.grocery.mandixpress.R.drawable.success,
                                    builder = {
                                    },
                                ),
                                alignment = Alignment.TopCenter,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        Text12_h1(
                            text = "Order Placed!",
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text12_body1(
                            text = "Your will receive a confirmation email with order details",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp)

                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Order Id:",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Text12_body1(
                                text = "${data.productResponse?.orderId}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Order value",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Text12_body1(
                                text = " ₹ ${data.productResponse?.totalOrderValue}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }


                    }
                }

                Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .background(whiteColor)
                        .drawBehind {
                            drawRoundRect(color = headingColor, style = stroke)
                        },
                ) {
                    Column(
                        modifier = Modifier
                            .background(whiteColor)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text13_body1(
                            text = "Delivery by today by 10:00 pm",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Order Date:",
                                modifier = Modifier

                            )
                            Text12_body1(
                                text = "${data.productResponse?.createdDate}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Contact Number:",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Text12_body1(
                                text = "${data.productResponse?.mobilenumber}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Deliver Address:",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Text12_body1(
                                text = "${data.productResponse?.address}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }


                    }
                }

            }
        }
    } else if (data.statusCode == 401)
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(8.dp), elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier
                            .background(whiteColor)
                            .clickable { }
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                            Image(
                                painter = rememberImagePainter(
                                    imageLoader = imageLoader,
                                    data = com.grocery.mandixpress.R.drawable.failed,
                                    builder = {
                                    },
                                ),
                                alignment = Alignment.TopCenter,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Text12_h1(
                            text = "Order Failed!",
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text12_body1(
                            text = "Some missing information",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 10.dp)

                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Text13_body1(
                                text = "Order Id:",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )
                            Text12_body1(
                                text = "OD${System.currentTimeMillis()}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }
                    }
                }

            }
        }


}