package com.grocery.mandixpress.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.Text10_h2
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text13_body1
import com.grocery.mandixpress.Utils.showLog
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal

@Composable
fun AddToCartCardView(
    viewmodal: HomeAllProductsViewModal,
    navController: NavHostController,
    context: Context,
    modifier: Modifier
) {
     Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = seallcolor, modifier = modifier
             .fillMaxWidth()
             .height(85.dp)
             .padding(horizontal = 10.dp, vertical = 8.dp)

             .clickable {
                 navController.navigate(DashBoardNavRoute.CartScreen.screen_route)
             }
             .border(
                 width = 1.dp, // Border width
                 color = greyLightColor, // Border color
                 shape = RoundedCornerShape(10.dp) // Match the card's shape
             )
             .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(whiteColor)
        ) {
            if(viewmodal.getSellersMinDeliveryCharge()!="0.00") {

                if(viewmodal.withHigherCartItemTotal()<viewmodal.getFreeDeliveryMinPrice())

                Row(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.bike_delivery),
                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(30.dp)
                            .height(30.dp)
                            .padding(start = 10.dp)


                    )
                    Column() {
                        Text10_h2(
                            text = "Pay Delivery",
                            color = Purple700,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text10_h2(
                            text = if(viewmodal.withHigherCartItemTotal() < viewmodal.getFreeDeliveryMinPrice()) String.format("%.2f",30+viewmodal.getSellersMinDeliveryCharge().toDouble()) else String.format(
                                "%.2f",viewmodal.getSellersMinDeliveryCharge()),
                            color = headingColor,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
           else if (viewmodal.getitempriceState.value < viewmodal.getFreeDeliveryMinPrice()) {
                Row(modifier = Modifier) {
                    Image(
                        painter = painterResource(id = R.drawable.bike_delivery),
                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(30.dp)
                            .height(30.dp)
                            .padding(start = 10.dp)


                    )
                    Column() {
                        Text10_h2(
                            text = "Free Delivery",
                            color = Purple700,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text10_h2(
                            text = "Add item worth ${viewmodal.getFreeDeliveryMinPrice().toDouble() - viewmodal.getitempriceState.value}",
                            color = headingColor,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }

            } else {
                Row(modifier = Modifier.padding(start = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.unlocked),

                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(20.dp)
                            .height(20.dp)
                    )
                    Column() {
                        Text10_h2(
                            text = "whoo! got free delivery",
                            color = Purple700,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text10_h2(
                            text = "No coupons Required",
                            color = headingColor,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }


            }
            Box(
                modifier = Modifier.background(color = seallcolor)

            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    val (l0, l1, l2) = createRefs()
                    Image(
                        painter = painterResource(id = R.drawable.cart_icon),
                        contentDescription = "Carrot Icon",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .width(40.dp)
                            .padding(top = 10.dp)
                            .height(40.dp)
                            .constrainAs(l0) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }

                    )
                    Column(Modifier.constrainAs(l1) {
                        start.linkTo(l0.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                        Text12_body1(
                            text = "${viewmodal.getitemcountState.value.toString()} items",
                            color = Color.White
                        )
                        Text12_body1(
                            text = "₹ ${viewmodal.getitempriceState.value.toString()}",
                            color = Color.White
                        )


                    }

                    Text13_body1(
                        text = "view cart >",
                        color = Color.White,
                        modifier = Modifier.constrainAs(l2) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                        })

                }

            }

        }

    }
}