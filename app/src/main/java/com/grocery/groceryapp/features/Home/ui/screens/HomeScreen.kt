package com.grocery.groceryapp.features.Home.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.Utils
import com.grocery.groceryapp.common.Utils.Companion.vibrator
import com.grocery.groceryapp.data.modal.CategoryWiseDashboardResponse
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.screens.ListItemsActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import vtsen.hashnode.dev.simplegooglemapapp.ui.LocationUtils
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.LocationPermissionsAndSettingDialogs
import java.text.DecimalFormat


@OptIn(ExperimentalPagerApi::class)
@Composable
fun homescreen(
    navcontroller: NavHostController, sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val context = LocalContext.current.getActivity()

    var res = viewModal.exclusiveProductsResponse1.value
    var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context!!)
    var best = viewModal.bestsellingProductsResponse1.value
    var requestLocationUpdate by remember { mutableStateOf(true) }
    val pager = rememberPagerState()
    viewModal.getCartItem()
    viewModal.callingDashboardCategoryWiseList()



    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Spacer(modifier = Modifier.width(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), Arrangement.SpaceBetween
            ) {
                Column {
                    Text18_600(text = "Delivery in 10 minutes")
                    Spacer(modifier = Modifier.height(4.dp))
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.home_icon),
                            contentDescription = ""
                        )
                        Text(
                            text = viewModal.gettingAddres(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = greyLightColor,

                            textAlign = TextAlign.Start,

                            maxLines = 1,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .width(200.dp)
                                .clickable { navcontroller.navigate(DashBoardNavRoute.MapScreen.screen_route) }
                        )
                    }

                }



                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "ProfileImage",
                    alignment = Alignment.Center,
                    modifier = Modifier

                        .padding(vertical = 10.dp)
                        .width(45.dp)
                        .height(45.dp)
                        .clickable {
                            navcontroller.navigate(DashBoardNavRoute.Profile.screen_route)
                        }
                )
            }


        }
        LazyColumn(
            //  verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
//            .padding(bottom = 50.dp)
            // .verticalScroll(state = scrollState)

        ) {
            item {


                Column(modifier = Modifier.fillMaxSize()) {

                    TextField(
                        value = "",
                        shape = RoundedCornerShape(8.dp),
                        enabled = false,
                        onValueChange = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .clickable {
                                navcontroller.navigate(DashBoardNavRoute.SearchProductItems.screen_route)
                            }
                            .padding(start = 10.dp, end = 10.dp),
                        placeholder = {
                            Text14_400(
                                text = "Search Store",
                                color = bodyTextColor,
                            )
                        },

                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Transparent,
                            trailingIconColor = titleColor,
                            backgroundColor = greycolor,
                            disabledIndicatorColor = Color.Transparent
                        ),


                        leadingIcon = {
                            IconButton(onClick = {}) {
                                Icon(
                                    Icons.Default.Search, contentDescription = "",
                                )
                            }
                        },
                        singleLine = true,
                    )

                    HorizontalPager(count = 3, state = pager) { index ->
                        Banner(pagerState = pager)
                    }

//exclusive offers
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), Arrangement.SpaceBetween
                    ) {
                        Text16_700(
                            text = "Exclusive Offers", color = Color.Black,
                            modifier = Modifier
                                .padding(start = 10.dp),
                        )
                        Text14_400(
                            "See all", color = seallcolor, modifier = Modifier

                                .padding(top = 5.dp, end = 20.dp)
                                .clickable {
                                    if (res.statusCode == 200) {
                                        val list1 = res.list
                                        context?.launchActivity<ListItemsActivity>() {
                                            putExtra(
                                                "parced",
                                                HomeAllProductsResponse(
                                                    list1,
                                                    "Exclusive Offers",
                                                    200
                                                )
                                            )

                                        }
                                    }
                                }
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                        // .height(260.dp)
                    ) {

                        if (res.statusCode == 200) {
                            val list1 = res.list
                            items(list1!!) { data ->
                                ExclusiveOffers(data, context!!, navcontroller, viewModal)
                            }


                        } else {
                            repeat(5) {
                                item {
                                    ShimmerAnimation()

                                }
                            }
                        }

                    }
//Best selling
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), Arrangement.SpaceBetween
                    ) {
                        Text16_700(
                            text = "Best Selling", color = Color.Black,
                            modifier = Modifier
                                .padding(start = 10.dp),
                        )
                        Text14_400(
                            "See all", color = seallcolor, modifier = Modifier
                                .padding(top = 5.dp, end = 20.dp)
                                .clickable {
                                    if (best.statusCode == 200) {
                                        val list1 = best.list
                                        context?.launchActivity<ListItemsActivity>() {
                                            putExtra(
                                                "parced",
                                                HomeAllProductsResponse(list1, "Best Selling", 200)
                                            )

                                        }
                                    }

                                }
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                        // .height(260.dp)
                    ) {

                        if (best.statusCode == 200) {
                            val list1 = best.list
                            items(list1!!) { data ->
                                // Column(modifier = Modifier.padding(10.dp)) {
                                BestOffers(navcontroller, data, context!!, viewModal)
                                //}


                            }

                        } else {
                            repeat(5) {
                                item {
                                    ShimmerAnimation()

                                }
                            }
                        }


                    }

                    //groceries
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Text16_700(
                            text = "Groceries", color = Color.Black,
                            modifier = Modifier
                                .weight(3f)
                                .padding(start = 10.dp),
                        )

                    }


                    val ls: MutableList<MainProducts> = ArrayList()
                    ls.add(MainProducts("vegetables", R.drawable.nonveg, Purple700, null))
                    ls.add(MainProducts("diary", R.drawable.diary, borderColor, null))
                    ls.add(MainProducts("grocery", R.drawable.grocery, disableColor, null))
                    ls.add(MainProducts("Oils", R.drawable.bell_peeper, darkFadedColor, null))
                    ls.add(MainProducts("Oils", R.drawable.ginger, darkFadedColor, null))
                    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 4)
                    FlowRow(
                        mainAxisSize = SizeMode.Expand,
                        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                    ) {
                        for (i in 0 until ls.size) {
                            GroceriesItems(
                                ls[i].color,
                                ls[i].image,
                                ls[i].name,
                                navcontroller,
                                viewModal,
                                itemSize
                            )


                        }

                    }


                }
            }


            //list.snapshot().items
            item {


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    repeat(viewModal.CategoryWiseDashboardRespon.value.list?.size ?: 0) {

                        Card(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(2.dp),
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 10.dp)


                        ) {
                            Column() {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = null,
                                            tint = Color.LightGray,


                                            )
                                        Column() {
                                            Text24_700(
                                                text = viewModal.CategoryWiseDashboardRespon.value.list?.get(
                                                    it
                                                )?.categoryTitle ?: "", color = headingColor,
                                                modifier = Modifier


                                            )
                                            Text16_700(
                                                text = viewModal.CategoryWiseDashboardRespon.value.list?.get(
                                                    it
                                                )?.category ?: "", color = greyLightColor,
                                                modifier = Modifier


                                            )
                                        }
                                    }


                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        tint = Color.LightGray,
                                        modifier = Modifier.clickable {
                                            viewModal.setcategory(
                                                viewModal.CategoryWiseDashboardRespon.value.list?.get(
                                                    it
                                                )?.category ?: ""
                                            )
                                            navcontroller.navigate(DashBoardNavRoute.DashBoardCategoryWisePagination.screen_route)
                                        }
                                    )

                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
                                FlowRow(
                                    mainAxisSize = SizeMode.Expand,
                                    mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                                ) {
                                    for (i in 0 until viewModal.CategoryWiseDashboardRespon.value.list?.get(
                                        it
                                    )!!.ls?.size!!) {
                                        AllItems(
                                            viewModal.CategoryWiseDashboardRespon.value.list?.get(it)!!.ls?.get(
                                                i
                                            )!!,
                                            context, navcontroller, viewModal, itemSize
                                        )
                                    }

                                }
                            }


                        }


                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

        }


        cardviewAddtoCart(
            viewModal,
            navcontroller,
            context!!,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (sharedpreferenceCommon.getCombinedAddress().equals("null"))
        if (requestLocationUpdate)
            LocationPermissionsAndSettingDialogs(
                updateCurrentLocation = {
                    requestLocationUpdate = false
                    LocationUtils.requestLocationResultCallback(mFusedLocationClient) { locationResult ->
                        locationResult.lastLocation?.let { location ->
                            //   currentLocation=location
                            sharedpreferenceCommon.setCombineAddress("${location.latitude}, ${location.longitude}")
                        }

                    }
                }
            )


}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    pagerState: PagerState
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
        )

        HorizontalPagerIndicator(
            pagerState = pagerState, pageCount = 3,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 10.dp),
            indicatorWidth = 8.dp,
            indicatorShape = RectangleShape
        )
    }

}

@Composable
fun ExclusiveOffers(
    data: HomeAllProductsResponse.HomeResponse,
    context: Context,
    navcontroller: NavHostController,
    viewModal: HomeAllProductsViewModal
) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)

            .width(150.dp)
            .clickable {
                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.price?.toFloat() ?: 0.0f) / (data.orignalprice?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()
            Text(
                text = "${offpercentage}% off", color = titleColor,
                modifier = Modifier.align(
                    Alignment.End
                ),
                fontSize = 10.sp,
            )

            Image(

                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(100.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.CenterHorizontally)


            )

            Text20_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text12Sp_600(
                text = "${data.quantity} pcs,Price", color = availColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text12Sp_600(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.orignalprice ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.ProductId ?: "",
                                data.productImage1 ?: "",
                                data.price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.orignalprice ?: ""
                            )
                            viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            vibrator(context)

                        },

                    ) {
                    Text13_700(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }


            }

        }
    }
}


@Composable
fun AllItems(
    data: CategoryWiseDashboardResponse.cat.L,
    context: Context,
    navcontroller: NavHostController, viewModal: HomeAllProductsViewModal, itemSize: Dp
) {


    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .size(itemSize)
            .clickable {
//                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier

                .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.price?.toFloat() ?: 0.0f) / (data.actualPrice?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()
            Text(
                text = "${offpercentage}% off", color = titleColor,
                modifier = Modifier.align(
                    Alignment.End
                ),
                fontSize = 10.sp,
            )

            Image(

                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(100.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.CenterHorizontally)


            )

            Text20_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text12Sp_600(
                text = "${data.quantity} pcs,Price", color = availColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text12Sp_600(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.actualPrice ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.productId ?: "",
                                data.productImage1 ?: "",
                                data.price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.actualPrice ?: ""
                            )
                            viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            Utils.vibrator(context)


                        },

                    ) {
                    Text13_700(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }


            }

        }
    }
}

@Composable
fun BestOffers(
    navcontroller: NavHostController,
    data: HomeAllProductsResponse.HomeResponse,
    context: Context,
    viewModal: HomeAllProductsViewModal
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)

            .width(150.dp)
            .clickable {
                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.price?.toFloat() ?: 0.0f) / (data.orignalprice?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()
            Text(
                text = "${offpercentage}% off", color = titleColor,
                modifier = Modifier.align(
                    Alignment.End
                ),
                fontSize = 10.sp,
            )

            Image(

                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(100.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.CenterHorizontally)


            )

            Text20_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text12Sp_600(
                text = "${data.quantity} pcs,Price", color = availColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text12Sp_600(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.orignalprice ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.ProductId ?: "",
                                data.productImage1 ?: "",
                                data.price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.orignalprice ?: ""
                            )
                            viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            vibrator(context)

                        },

                    ) {
                    Text13_700(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }


            }

        }
    }

}

@Composable
fun GroceriesItems(
    color: Color,
    drawable: Int,
    item: String,
    navController: NavHostController,
    viewModal: HomeAllProductsViewModal,
    itemSize: Dp
) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),

        backgroundColor = whiteColor, modifier = Modifier
            .size(itemSize)
            .padding(10.dp)
            .clickable {
                navController.navigate(DashBoardNavRoute.MenuItems.senddata(item))


            }

    ) {
        Column(

        ) {

            Text12Sp_600(
                text = item, color = blackColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
            )


        }
    }
}

//shimming
@Composable
fun ShimmerAnimation(
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

    ShimmerItem(brush = brush)

}


@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}

@Composable
fun cardviewAddtoCart(
    viewmodal: HomeAllProductsViewModal,
    navController: NavHostController,
    context: Context,
    modifier: Modifier
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = seallcolor, modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(5.dp)
            .clickable {
                navController.navigate(DashBoardNavRoute.CartScreen.screen_route)
            }
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


    ) {
        Box(
            modifier = Modifier

        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                var (l0, l1, l2) = createRefs()

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
                    Text14_400(
                        text = "${viewmodal.getitemcountState.value.toString()} items",
                        color = Color.White
                    )
                    Text14_400(
                        text = "₹ ${viewmodal.getitempriceState.value.toString()}",
                        color = Color.White
                    )


                }

                Text16_700(
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