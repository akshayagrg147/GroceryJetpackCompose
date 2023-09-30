package com.grocery.mandixpress.features.Spash

import android.app.Activity
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.AppButtonComponent
import com.grocery.mandixpress.common.AppCustomChips
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.common.LoadingBar
import com.grocery.mandixpress.data.modal.AllOrdersHistoryList
import com.grocery.mandixpress.features.Home.ui.ui.theme.ShimmerColorShades
import com.grocery.mandixpress.features.Home.ui.ui.theme.faqColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProfileEvent
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProfileViewModal
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreenNavigation(navController: NavHostController, context: Activity) {
    OrderHistoryScreen(context, navController)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OrderHistoryScreen(
    context: Activity,
    navController: NavHostController,
    viewModal: ProfileViewModal = hiltViewModel()
) {
   val scope= rememberCoroutineScope()
    var selected by remember { mutableStateOf(0) }

    val orderListResponse by viewModal.orderhistorydata.collectAsState()

LaunchedEffect(key1 = selected ){
    if(selected==0)
        viewModal.onEvent(ProfileEvent.OrderEvent("Ordered"))
    else if(selected==1)
        viewModal.onEvent(ProfileEvent.DeliverEvent("Delivered"))
    else  if(selected==2)
        viewModal.onEvent(ProfileEvent.CancelEvent("Cancelled"))

}




    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        CommonHeader(text = "Order History", color = Color.Black) {
         navController.popBackStack()
        }
        FlowRow {
            listOf<String>("Ordered","Delivered","Cancelled").forEachIndexed { index, s ->
                AppCustomChips(
                    selected = index == selected,
                    index = index,
                    title = s,
                    unSelectedBackgroundColor = Color.LightGray,
                    borderStroke = BorderStroke(0.dp, Color.Transparent)
                ) { data ->
                    selected = data
                }
            }
        }


        // Observe the order history data state
        if(orderListResponse.isLoading){
            repeat(5){
                ShimmerLayout()
            }

        }
        if(orderListResponse.data != null)
            if(orderListResponse.data?.list?.isNotEmpty() == true)
            {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp)
            ) {
                items(orderListResponse.data?.list ?: emptyList(), key = {it.orderId!!}){
                            OrderHistoryRow(it) {data->
                                navController.currentBackStackEntry?.arguments?.putParcelable(
                                    "orderDetail",
                                    data
                                )
                                navController.navigate(DashBoardNavRoute.OrderDetail.screen_route)
                            }
                        }
                    }
            }
        else{
                noItemound()
            }


    }
}


@Composable
fun OrderHistoryRow(
    data: AllOrdersHistoryList.Orders,
    call: (AllOrdersHistoryList.Orders) -> Unit
) {
    Log.d("datafromorderhistory","${data.createdDate}")
    Box(modifier = Modifier
        .padding(top = 7.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))

        .fillMaxWidth()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween
            ) {
                Text12_body1(
                    text = data.createdDate?.split(" ")?.get(0) ?: "",

                )
                Text12_body1(
                    text = data.createdDate?.split(" ")?.get(1) ?: "nn" ,
                )
            }
            Spacer(Modifier.height(5.dp))
            Divider()
            Row(
                modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween
            ) {
                Text14_h2(
                        text = "${data.orderId}",color= headingColor
                    )
                Image(
                    painter = painterResource(id = com.grocery.mandixpress.R.drawable.order_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )
            }


            Spacer(modifier = Modifier.height(2.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {Spacer(modifier = Modifier.height(8.dp))
                    Text12_body1(text = "Total Amount", modifier = Modifier, color = Color.Black)
                    Text12_body1(text = "₹ ${data.totalOrderValue}", modifier = Modifier)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(end = 8.dp).weight(1f)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.homeicon), // Replace with your icon resource
                        contentDescription = "Location Icon",
                        tint = Color.Unspecified,

                        modifier = Modifier.size(24.dp)
                    )
                    Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {


                        Text12_h1(
                            text = "Delivery Address",
                            color = Color.Black,
                        )
                        Text12_body1( text = "   ${data.address?.take(20)}..", modifier = Modifier.padding(start = 10.dp))

                    }

                    }


                }
            Spacer(Modifier.height(5.dp))
            Text12_body1(
                text = "items \n ${data.orderList?.size} "
            )

            Spacer(modifier = Modifier.height(5.dp))

            AppButtonComponent(text="View Details"){

                call(data)

            }
            }

    }

}

fun formatDate(inputDate: String): String {
    val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)

    // Set the inputDateFormat's time zone to IST
    inputDateFormat.timeZone = TimeZone.getTimeZone("IST")

    try {
        val date: Date = inputDateFormat.parse(inputDate) ?: Date()
        return outputDateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        return "Aug 23,2023"
    }
}

@Composable
fun noItemound() {
    Column(modifier = Modifier.fillMaxSize(),Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_orders),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }
        Text12_h1(
            text = "No Orders Available", color = headingColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )


    }

}
@Composable
fun ShimmerLayout() {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))

            .fillMaxWidth()
    ) {
        val transition = rememberInfiniteTransition()
        val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                RepeatMode.Reverse
            )
        )

        val brush = Brush.linearGradient(
            colors = ShimmerColorShades,
            start = Offset(10f, 10f),
            end = Offset(translateAnim, translateAnim)
        )
        // Shimmer effect for the entire content


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Shimmer effect for Text12_body1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            brush = brush
                        )
                )

                // Shimmer effect for Text12_body1 with formatted date
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = brush
                        )
                )
            }

            Spacer(Modifier.height(15.dp))
            Divider()

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Shimmer effect for Text16_h1 (order ID)
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(10.dp)
                            .background(
                                brush = brush
                            )
                    )

                    Spacer(Modifier.height(15.dp))

                    // Shimmer effect for Text12_body1 (item count)
                    Box(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                            .background(
                                brush = brush
                            )
                    )
                }

                Spacer(Modifier.width(20.dp))

                // Shimmer effect for Image
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                        .background(
                            brush = brush
                        )
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Shimmer effect for Text12_body1 (Total Amount label)
                    Box(
                        modifier = Modifier
                            .width(25.dp)
                            .height(15.dp)
                            .background(
                                brush = brush
                            )
                    )

                    // Shimmer effect for Text12_body1 (Total Amount value)
                    Box(
                        modifier = Modifier
                            .width(25.dp)
                            .height(15.dp)
                            .background(
                                brush = brush
                            )
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        // Shimmer effect for Icon
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    brush = brush
                                )
                        )

                        // Shimmer effect for Text (Location label)
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = brush
                                )
                        )
                    }

                    // Shimmer effect for Text12_body1 (Address)
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .background(
                                brush = brush
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Shimmer effect for AppButtonComponent
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(10.dp)
                    .background(
                        brush = brush
                    )
            )
        }
    }
}

// Usage





