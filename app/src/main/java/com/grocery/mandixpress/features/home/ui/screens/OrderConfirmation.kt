package com.grocery.mandixpress.features.home.ui.screens

import androidx.compose.foundation.Canvas
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.grocery.mandixpress.R
//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.animateLottieCompositionAsState
//import com.airbnb.lottie.compose.rememberLottieComposition
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text12_bodyOneLine
import com.grocery.mandixpress.Utils.Text12_h1
import com.grocery.mandixpress.Utils.Text13_body1
import com.grocery.mandixpress.Utils.Text14_h2
import com.grocery.mandixpress.common.AppButtonComponent

import com.grocery.mandixpress.common.Utils.Companion.showNotification
import com.grocery.mandixpress.data.modal.OrderIdResponse
import com.grocery.mandixpress.features.home.ui.ui.theme.bodyTextColor
import com.grocery.mandixpress.features.home.ui.ui.theme.greyLightColor
import com.grocery.mandixpress.features.home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.home.ui.ui.theme.lightBlueColor
import com.grocery.mandixpress.features.home.ui.ui.theme.seallcolor
import com.grocery.mandixpress.features.home.ui.ui.theme.titleColor
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
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        showNotification(LocalContext.current, "Order Placed", "Thanku for shopping with us!")
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
                    .fillMaxWidth(),
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
                title = { Text14_h2("Order Placed", color = Color.Black) },
                backgroundColor = whiteColor,
                elevation = 1.dp
            )
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                viewModal.deleteCartItems()
              //  LottieAnimationComponent(animationFileName = "order.json")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .border(1.dp, titleColor)
                    ,
                    shape = RoundedCornerShape(8.dp)
                    ,
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
                        ) {Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.orderid),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding()
                                    .size(30.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                            )
                            Text13_body1(
                                text = "Order Id:",
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                        }
                            Text12_body1(
                                text = "${data.productResponse?.orderId}",
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )

                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.money_svgrepo_com),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding()
                                        .size(30.dp)
                                        .padding(start = 10.dp, end = 10.dp)
                                )
                                Text13_body1(
                                    text = "Order value",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                )
                            }
                            Text12_body1(
                                text = " ₹ ${data.productResponse?.totalOrderValue}",
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                            )

                        }


                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .border(1.dp, titleColor),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 10.dp
                ) {
                    Column (modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(10.dp),
                        verticalArrangement = Arrangement.Center

                        ){
                        Row {


                            Pulsating {
                                Surface(
                                    color = titleColor,
                                    shape = CircleShape,
                                    modifier = Modifier.size(10.dp),
                                    content = {}
                                )
                            }
                            Row(modifier = Modifier.padding(start = 10.dp)){
                                Icon(
                                    painter = painterResource(id = R.drawable.order_icon), // Replace with your icon resource
                                    contentDescription = "Location Icon",
                                    tint = Color.Unspecified,

                                    modifier = Modifier.size(24.dp)
                                )

                                Column( modifier = Modifier.padding(start = 5.dp))  {
                                    Text12_h1(
                                        text = "Ordered",
                                        color = headingColor
                                    )
                                    Text12_h1(
                                        text = "Packing your order",
                                        color = bodyTextColor
                                    )
                                }
                            }

                        }
                        Canvas(Modifier.padding(start = 8.dp).height(60.dp)
                           ) {

                            drawLine(
                                color = seallcolor,
                                start = Offset(0f, 0f),
                                end = Offset(0f, 120f),
                                strokeWidth = 2.dp.toPx(),
                                pathEffect = pathEffect
                            )
                        }

                        Row(modifier = Modifier.padding(top = 5.dp)) {


                            PulsatingGray()
                            Row(modifier = Modifier.padding(start = 10.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.bike_delivery), // Replace with your icon resource
                                    contentDescription = "Location Icon",
                                    tint = Color.Unspecified,

                                    modifier = Modifier.size(24.dp)
                                )
                                Column(modifier = Modifier.padding(start = 5.dp)) {
                                    Text12_h1(
                                        text = "On the way",
                                        color = headingColor,
                                    )
                                    Text12_h1(
                                        text = "Order is picked by delivery agent",
                                        color = bodyTextColor
                                    )
                                }
                            }
                        }
                        Canvas(Modifier.padding(start = 8.dp).height(60.dp)
                        ) {

                            drawLine(
                                color = greyLightColor,
                                start = Offset(0f, 0f),
                                end = Offset(0f, 120f),
                                strokeWidth = 2.dp.toPx(),
                                pathEffect = pathEffect
                            )
                        }
                        Row(modifier = Modifier.padding(top = 5.dp) ) {


                            PulsatingGray()
                            Row(modifier = Modifier.padding(start = 10.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.homeicon), // Replace with your icon resource
                                    contentDescription = "Location Icon",
                                    tint = Color.Unspecified,

                                    modifier = Modifier.size(24.dp)
                                )
                                Column(modifier = Modifier.padding(start = 5.dp)) {
                                    Text12_h1(
                                        text = "Delivered",
                                        color = headingColor,


                                        )
                                    Text12_h1(
                                        text = "Order is Delivered",
                                        color = bodyTextColor


                                    )
                                }
                            }

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
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.date),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding()
                                        .size(30.dp)
                                        .padding(start = 10.dp, end = 10.dp)
                                )
                                Text13_body1(
                                    text = "Order Date:",
                                    modifier = Modifier

                                )
                            }
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
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.bike_delivery),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding()
                                        .size(30.dp)
                                        .padding(start = 10.dp, end = 10.dp)
                                )
                                Text13_body1(
                                    text = "Contact Number:",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                )
                            }
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
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.address),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding()
                                        .size(30.dp)
                                        .padding(start = 10.dp, end = 10.dp)
                                )
                                Text13_body1(
                                    text = "Deliver Address:",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                )
                            }
                            Text12_bodyOneLine(
                                text = "${data.productResponse?.address}".replace("\n"," ").take(10),
                                modifier = Modifier
                                    .padding(top = 5.dp),

                            )

                        }



                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier  = Modifier
                    .height(50.dp)
                    .padding(horizontal = 10.dp)){
                    AppButtonComponent(text = "Go to Orders", background = lightBlueColor) {
                        navController.navigate(DashBoardNavRoute.AllOrderHistory.screen_route) {
                            popUpTo(DashBoardNavRoute.Home.screen_route) {
                                inclusive = true
                            }
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
                        .padding(20.dp)
                        .border(1.dp, titleColor),
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
//@Composable
//fun LottieAnimationComponent(
//    animationFileName: String,
//    loop: Boolean = true,
//    modifier: Modifier = Modifier
//) {
//    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset(animationFileName))
//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        iterations = if (loop) LottieConstants.IterateForever else 1,
//        restartOnPlay = true,
//
//    )
//
//    LottieAnimation(
//        composition = composition,
//        progress = progress,
//        modifier = modifier
//    )
//}



@Composable
fun Pulsating(pulseFraction: Float = 2.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}
@Composable
fun PulsatingGray() {
    Box(
        modifier = Modifier
            .size(12.dp) // Size of the dot
            .background(color = Color.Gray, shape = CircleShape)
    )
}