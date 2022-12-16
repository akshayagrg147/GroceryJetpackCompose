package com.grocery.groceryapp.features.Home.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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

private val headerHeight = 154.dp
private val toolbarHeight = 56.dp
private const val titleFontScaleStart = 0.0f
private const val titleFontScaleEnd = 1.25f
private val titlePaddingStart = 1.dp
private val titlePaddingEnd = 7.dp
private val paddingMedium = 16.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun homescreen(
    navcontroller: NavHostController, sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val context = LocalContext.current.getActivity()


    var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context!!)

    var requestLocationUpdate by remember { mutableStateOf(true) }
    val pager = rememberPagerState()
    viewModal.getItemCount()
    viewModal.getItemPrice()
    viewModal.callingDashboardCategoryWiseList()

    val scroll: ScrollState = rememberScrollState(0)
    var searchvisibility by remember { mutableStateOf(false) }
    var serviceavalibilitycheck by remember { mutableStateOf(true) }

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {

        HeaderDeliveryTime(viewModal, navcontroller, scroll, headerHeightPx) {
            searchvisibility = it

        }
        if (serviceavalibilitycheck) {
            BodyDashboard(scroll, pager, viewModal, navcontroller, context, serviceavalibilitycheck)
            if (searchvisibility)
                SearchBar(navcontroller, headerHeightPx, toolbarHeightPx, scroll)
            cardviewAddtoCart(
                viewModal,
                navcontroller,
                context!!,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        } else {
            NoAvaibiltyScreen()
        }

    }

    if (sharedpreferenceCommon.getCombinedAddress() == "null")
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

@Composable
fun NoAvaibiltyScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Not Available in your area",
            style = loginTypography.body1,
            color = greyLightColor,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BodyDashboard(
    scroll: ScrollState,
    pager: PagerState,
    viewModal: HomeAllProductsViewModal,
    navcontroller: NavHostController,
    context: Activity,
    serviceavalibilitycheck: Boolean
) {
    var avalibiltycheck = serviceavalibilitycheck
    var best = viewModal.bestsellingProductsResponse1.value
    var res = viewModal.exclusiveProductsResponse1.value
    Column(
        //  verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
            .verticalScroll(scroll)
//            .padding(bottom = 50.dp)
        // .verticalScroll(state = scrollState)

    ) {
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

        Column(modifier = Modifier.fillMaxSize()) {


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
                    if (list1?.isNotEmpty() == true)
                        items(list1) { data ->
                            // Column(modifier = Modifier.padding(10.dp)) {

                            BestOffers(navcontroller, data, context!!, viewModal)
                            avalibiltycheck=true
                            //}


                        }
                    else {
                        avalibiltycheck = false
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
            ls.add(MainProducts("vegetables", R.drawable.vegetable, borderColor, null))
            ls.add(MainProducts("diary", R.drawable.diary, borderColor, null))
            ls.add(MainProducts("drink", R.drawable.cold_drink, disableColor, null))
            ls.add(MainProducts("personal care", R.drawable.personal_care, borderColor, null))
            ls.add(MainProducts("Pet Care", R.drawable.pet_care, lightBlueColor, null))
            ls.add(MainProducts("cleaning essentials", R.drawable.cleaning, borderColor, null))
            ls.add(MainProducts("Baby care", R.drawable.ginger, lightBlueColor, null))
            ls.add(MainProducts("Sauces", R.drawable.saucesspreads, darkFadedColor, null))

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

                        Spacer(modifier = Modifier.height(5.dp))
                        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2.1f)
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
                        Spacer(modifier = Modifier.height(20.dp))
                    }


                }


            }
            Spacer(modifier = Modifier.height(30.dp))
        }


    }

}

@Composable
fun SearchBar(
    navcontroller: NavHostController, headerHeightPx: Float,
    toolbarHeightPx: Float, scroll: ScrollState
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }
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
            .padding(start = 0.dp, end = 2.dp)
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = androidx.compose.ui.unit.lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleYFirstInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    headerHeight - titleHeightPx.toDp() - paddingMedium,
                    headerHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val titleY = androidx.compose.ui.unit.lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                val titleX = androidx.compose.ui.unit.lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            },
        placeholder = {
            Text12Sp_600(
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
}

@Composable
fun HeaderDeliveryTime(
    viewModal: HomeAllProductsViewModal,
    navcontroller: NavHostController,
    scroll: ScrollState,
    headerHeightPx: Float,
    call: (Boolean) -> Unit
) {
    Column(modifier = Modifier.graphicsLayer {
        translationY = -scroll.value.toFloat() / 2f // Parallax effect
        alpha = (-1f / headerHeightPx) * scroll.value + 1
        if (alpha >= 0.95f)
            call(false)
        else
            call(true)

        Log.d("alphavalue", alpha.toString())
    }) {
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
                        //    viewModal.getCartItem()
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
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp, bottom = 1.dp)
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
                        //    viewModal.getCartItem()
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
                       //     viewModal.getCartItem()
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
        shape = RoundedCornerShape(10.dp),

        backgroundColor = color, modifier = Modifier
            .size(itemSize)
            .padding(5.dp)
            .clickable {
                navController.navigate(DashBoardNavRoute.MenuItems.senddata(item))


            }

    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Text12Sp_600(
                text = item, color = whiteColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .padding(top = 2.dp)
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