package com.grocery.mandixpress.common

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.grocery.mandixpress.Utils.Constants.Companion.distanceInKm
import com.grocery.mandixpress.Utils.Text10_h2
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text13_body1
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.roomdatabase.CartItemPriceBySeller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.ArrayList
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

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
             val distanceInkM= haversine(viewmodal.getSvedLatLng().first?.toDouble()?:0.00,viewmodal.getSvedLatLng().second?.toDouble()?:0.00,viewmodal.firsSellerLatLngValue.value.first,viewmodal.firsSellerLatLngValue.value.second,)


             if (viewmodal.getSellersMinDeliveryCharge() != 0.00f) {
                 Log.d("getSellersMinDeliveryCh","--"+viewmodal.getSellersMinDeliveryCharge())
                 var extraChargesShouldIncludeState = remember {
                     mutableStateOf(-1)
                 }
                 val amountMoreThan3Km = remember {
                     mutableStateOf(0.00)
                 }

                 LaunchedEffect(Unit) {
                     val resultList = withContext(Dispatchers.IO) {
                         viewmodal.withHigherCartItemTotal()
                     }
                   getAfterCalculation (resultList) { shouldBeCharged, chargeAmount ->
                       extraChargesShouldIncludeState.value = shouldBeCharged
                       amountMoreThan3Km.value = chargeAmount
                   }

                     // Update the state with the result of the coroutine

                 }

                 val textToShow = if (extraChargesShouldIncludeState.value==0) {
                     val deliveryCharge =   30 + viewmodal.getSellersMinDeliveryCharge()
                     String.format("%.2f", deliveryCharge)

                 }
                 else if (extraChargesShouldIncludeState.value==1)  {
                     val deliveryCharge =   viewmodal.getSellersMinDeliveryCharge()
                     String.format("%.2f", deliveryCharge)

                 }
                 else if (extraChargesShouldIncludeState.value==2)  {
                     val deliveryCharge =   amountMoreThan3Km.value
                     String.format("%.2f", deliveryCharge)

                 }
                 else{
                     0.00f
                 }
if(textToShow!=0.00f)
                 Row(modifier = Modifier) {
                     Image(
                         painter = painterResource(id = R.drawable.bike_delivery),
                         contentDescription = "",
                         modifier = Modifier
                             .padding()
                             .size(30.dp)
                             .padding(start = 10.dp)
                     )
                     Column() {
                         Text10_h2(
                             text = "Pay Delivery",
                             color = Purple700,
                             modifier = Modifier.padding(start = 10.dp)
                         )

                         Text10_h2(
                             text = textToShow.toString().replace(",","."),
                             color = headingColor,
                             modifier = Modifier.padding(start = 10.dp)
                         )
                     }
                 }
             }
             else if(distanceInkM>3)
             {

                 Row(modifier = Modifier) {
                     Image(
                         painter = painterResource(id = R.drawable.bike_delivery),
                         contentDescription = "",
                         modifier = Modifier
                             .padding()
                             .size(30.dp)
                             .padding(start = 10.dp)
                     )
                     Column() {
                         Text10_h2(
                             text = "Pay Delivery",
                             color = Purple700,
                             modifier = Modifier.padding(start = 10.dp)
                         )

                         Text10_h2(
                             text = (distanceInkM.toInt()*5).toString(),
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

fun getAfterCalculation(resultList: ArrayList<CartItemPriceBySeller>, callback: (Int,Double) -> Unit) {
    if(distanceInKm==0.00){
        distanceInKm= haversine(resultList.get(0).customerLat!!,resultList.get(0).customerLng!!,resultList.get(0).sellerLat!!,resultList.get(0).sellerLng!!)

    }
    if(distanceInKm!! >= 3.00){
        callback(2,distanceInKm!!*5)
    }
    else{
        var extraChargesShouldInclude = false
        resultList.forEach { item ->
            if ((item.totalItemPrice ?: -1) >= (item.freedeliveryPrice ?: -1)) {
                extraChargesShouldInclude = true
                return@forEach
            }
        }
        // Callback invoked after forEach loop completes
        if (extraChargesShouldInclude) {
            callback(1,0.00) // Condition met
        } else {
            callback(0,0.00) // Condition not met
        }
    }

}
fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Earth radius in kilometers

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c
}
