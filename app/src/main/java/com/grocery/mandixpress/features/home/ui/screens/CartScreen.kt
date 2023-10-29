package com.grocery.mandixpress.features.home.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.material.Card
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp


import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.roomdatabase.CartItems
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.common.SwipeButton
import com.grocery.mandixpress.data.modal.OrderIdCreateRequest
import com.grocery.mandixpress.features.home.domain.modal.AddressItems
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.CartEvent
import com.grocery.mandixpress.features.home.ui.viewmodal.CartItemsViewModal
import com.grocery.mandixpress.features.splash.splashnavigation.ScreenRoute
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Box as Box1

private val headerHeight = 150.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 72.dp
private var sortType by mutableStateOf("")

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    context: Activity,
    sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: CartItemsViewModal = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    var passCoupon by remember { mutableStateOf(false) }

    var isDialog by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, // Initial state (e.g., Closed, Hidden)
        skipHalfExpanded = true
    )

    val choose: MutableState<Boolean> = remember { mutableStateOf(false) }
    var addressvalue: String? = null
    val order: ArrayList<OrderIdCreateRequest.Order> = ArrayList()

    viewModal.getCartItem()
    viewModal.getSavingAmount()
    if (isDialog)
        CommonProgressBar()


    val orderIDResponse by viewModal.createOrderIdState.collectAsState()
    if (orderIDResponse.data?.statusCode == 200) {
        isDialog = false
        LaunchedEffect(key1 = Unit) {
            navController.currentBackStackEntry?.arguments?.putParcelable(
                "orderstatus",
                orderIDResponse.data
            )
            viewModal.sendNotification(orderIDResponse.data?.productResponse?.fcm_token ?: "")
            navController.navigate(DashBoardNavRoute.OrderSuccessful.screen_route) {
//                popUpTo(DashBoardNavRoute.Home.screen_route) {
//                    inclusive = true
//                }
            }


        }
    } else if (orderIDResponse.error.isNotEmpty()) {
        isDialog = false
        Toast.makeText(context, orderIDResponse.error, Toast.LENGTH_SHORT).show()
    }
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )


    ModalBottomSheetLayout(
        sheetElevation = 0.dp,
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box1(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.close_button),
                        contentDescription = "Cross Button",
                        modifier = Modifier,
                        contentScale = ContentScale.Fit
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            ),
                        )
                        .padding(bottom = 10.dp)
                ) {
                    Box1(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                            .background(whiteColor)
                            .drawBehind {
                                drawRoundRect(color = headingColor, style = stroke)
                            },
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text14_h1(text = "Bill details", color = Color.Black)
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, top = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text10_h2(
                                    text = "Order Amount",
                                    color = headingColor,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                Text10_h2(
                                    text = "₹ ${viewModal.totalPriceState.value}",
                                    color = headingColor,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp, bottom = 5.dp)
                                    .padding(horizontal = 10.dp),
                            ) {
                                Text10_h2(
                                    text = "Delivery Charges",
                                    color = headingColor,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .weight(1f),

                                    )

                                Text12_with_strikethrough(
                                    text1 = if (viewModal.totalPriceState.value.toInt() < viewModal.getFreeDeliveryMinPrice()
                                            .toInt()
                                    ) "Free" else "₹ 30",
                                    text2 = if (viewModal.totalPriceState.value.toInt() < viewModal.getFreeDeliveryMinPrice()
                                            .toInt()
                                    ) "₹ 30" else "Free",
                                    color = headingColor,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }


                            var backBundleData =
                                navController.currentBackStackEntry?.savedStateHandle?.get<Bundle>("passCoupon")
                            val grandTotal =
                                if (viewModal.totalPriceState.value < viewModal.getFreeDeliveryMinPrice()
                                        .toInt()
                                ) {
                                    "₹ " + ((30) + (viewModal.totalPriceState.value.toDouble()))
                                } else {
                                    "₹ ${(viewModal.totalPriceState.value.toDouble())}"
                                }
                            if (passCoupon) {
                                if ((backBundleData?.get("CouponPercent") != null)) {
                                    val cuponDiscount =
                                        (
                                                (((backBundleData.get("CouponPercent") as Int) * grandTotal.replace(
                                                    "₹ ",
                                                    ""
                                                ).toDoubleOrNull()!!)) / 100)


                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text10_h2(
                                            text = "Coupon Applied(${backBundleData?.get("CouponName")})",
                                            color = headingColor,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )

                                        Text10_h2(
                                            text = (String.format("%.2f", cuponDiscount)

                                                    ).toString(),
                                            color = headingColor,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height((20.dp)))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, end = 10.dp, top = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text14_h1(
                                            text = "Grand Total",
                                            color = headingColor,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )

                                        Text14_h1(
                                            text = String.format(
                                                "%.2f",
                                                (grandTotal.replace("₹ ", "").toDoubleOrNull()
                                                    ?.minus(cuponDiscount.toInt()))
                                            ).toString(),
                                            color = headingColor,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text14_h1(
                                        text = "Grand Total",
                                        color = headingColor,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )

                                    Text14_h1(
                                        text = String.format(
                                            "%.2f",
                                            (grandTotal.replace("₹ ", "")).toDoubleOrNull()
                                        ).toString(),
                                        color = headingColor,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                            }
                            LaunchedEffect(key1 = Unit) {
                                if (backBundleData != null)
                                    passCoupon = true
                            }
                            // Bottom-curved shape
                            /*     Box(
                                     modifier = Modifier
                                         .fillMaxSize()
                                         .clip(
                                             shape = BottomCurvedShape()
                                         )
                                         .background(Color.White)
                                 )*/

                            if (passCoupon) {
                                if (backBundleData?.get("CouponPercent") != null) {
                                    val percent: Int = backBundleData.get("CouponPercent") as Int
                                    AppliedCupon(
                                        backBundleData.get("CouponName").toString(),
                                        percent
                                    ) {
                                        backBundleData = null
                                        passCoupon = false;
                                    }
                                }


                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(24.dp)
                                    .background(sec20timer),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text10_h2(text = "We are accepting upi also", color = whiteColor)

                            }

                        }
                    }
                    Card(
                        onClick = { navController.navigate(DashBoardNavRoute.ApplyCoupons.screen_route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
                            .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = whiteColor,
                                shape = RoundedCornerShape(30.dp) // Set the corner radius here
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.discount), // Replace with your image resource
                                contentDescription = "Discount",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 10.dp), // Adjust the size as needed

                            )
                            Text12_h1(
                                text = "Use  Coupons", color = blackColor,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Image(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = "back_arrow",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 20.dp),

                                )
                        }

                    }

                    if (choose.value) {
                        Text13_body1(
                            text = "Choose Address",
                            color = Color.Black,
                            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                    ) {

                        AddressComponent(viewModal, navController, { firstAddress ->
                            addressvalue = firstAddress

                        }, {
                            when (it) {
                                "containsData" -> {
                                    choose.value = true
                                }
                                "ProceedButton" -> {
                                    isDialog = true
                                    val request = OrderIdCreateRequest(
                                        orderList = order,
                                        address = addressvalue,
                                        paymentmode = "COD",
                                        totalOrderValue = viewModal.totalPriceState.value.toString(),
                                        mobilenumber = sharedpreferenceCommon.getMobileNumber(),
                                        pincode = sharedpreferenceCommon.getPostalCode(),


                                        )
                                    viewModal.onEvent(CartEvent.createOrderId(request))


                                }
                                "addressEmpty" -> {
                                    navController.navigate(ScreenRoute.AddressScreen.route)
                                }

                                else -> {
                                    choose.value = false
                                }
                            }
                        }, {
                            addressvalue = it
                        })
                    }

                }
            }
        },
        sheetBackgroundColor = Color.White.copy(alpha = 0.0f),
        sheetState = modalBottomSheetState,
    )

    {

        Column(modifier = Modifier.fillMaxSize()) {
            Box1(modifier = Modifier.fillMaxSize()) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(bottom = 40.dp)
                ) {
                    val scroll: ScrollState = rememberScrollState(0)

                    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
                    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

                    val (l1, l2, l3) = createRefs()
                    Box1(modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(l1) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
//                    bottom.linkTo(l2.top)

                        }) {
                        Header(scroll, headerHeightPx, viewModal)
                        Body(order, scroll, viewModal, context)
                        bottomButton(scroll, headerHeightPx, toolbarHeightPx)
                        //  Title1( "none", scroll, headerHeightPx, toolbarHeightPx)

                    }


                }

                //  if(viewModal.responseLiveData.value.isNotEmpty())
                Card(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    elevation = 0.dp,
                    shape = CutCornerShape(30.dp),
                ) {
                    CommonButton(
                        text = "Go to Checkout(₹ ${viewModal.totalPriceState.value})",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        backgroundColor = colorResource(id = R.color.green_700),
                        color = Color.White,
                        enabled = viewModal.totalPriceState.value != 0
                    ) {
                        if (it) {
                            viewModal.getAllAddressItems()

                            scope.launch { modalBottomSheetState.show() }
                        }

                    }

                }
            }
        }

    }
}
/*@Composable
fun BottomCurvedShape(): Shape = androidx.compose.ui.draw.clip(CurvedShape())

@Composable
fun CurvedShape() =CustomShape()*/

/*@Composable
fun CustomShape() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White
                        )
                    )
                )
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        }
    }
}*/




@Composable
private fun Header(scroll: ScrollState, headerHeightPx: Float, viewModal: CartItemsViewModal) {
    Box1(modifier = Modifier
        .fillMaxWidth()
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 2f // Parallax effect
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally


        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable {
                        //  navController.navigate(ScreenRoute.CartScreen.route)
                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text14_h1(text = "Checkout", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(5.dp))

            Card(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = Teal200, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            ) {


                Row(
                    modifier = Modifier, Arrangement.SpaceBetween

                ) {
                    Text12_body1(
                        text = "Your total savings", whiteColor, modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 10.dp)
                    )
                    Text12_body1(
                        text = "₹ ${viewModal.savingAmountState.value}",
                        color = whiteColor,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 20.dp)
                    )


                }


            }
        }


    }
}

@Composable
fun Body(
    order: ArrayList<OrderIdCreateRequest.Order>,
    scroll: ScrollState,
    viewModal: CartItemsViewModal,
    context: Activity
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 90.dp)
            .verticalScroll(scroll)
    ) {


        if (viewModal.allCartItemsState.value.isEmpty())
            noHistoryAvailable("No order available")
        else {

            for (item in viewModal.allCartItemsState.value) {
                order.add(
                    OrderIdCreateRequest.Order(
                        item.ProductIdNumber,
                        item.strProductName,
                        item.strProductPrice.toString(),
                        item.totalCount.toString()
                    )
                )
                ItemEachRow(item, viewModal, context)
            }

        }


    }


}

@Composable
private fun bottomButton(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
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
            title = { Text14_h2("Checkout", color = Color.Black) },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}


@Composable
fun AddressComponent(
    viewModal: CartItemsViewModal,
    navController: NavHostController,
    firstAddress: (String) -> Unit,
    call: (String) -> Unit, passingaddress: (String) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val (isComplete, setIsComplete) = remember {
        mutableStateOf(false)
    }
    val selectedIndex = remember { mutableStateOf(1) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
            // .height(260.dp)
        ) {
            if (viewModal.addresslistState.value.size > 1) {
                firstAddress(
                    " ${viewModal.addresslistState.value[0].customer_name}\n${viewModal.addresslistState.value[0].Address1}\n${viewModal.addresslistState.value[0].Address2}\n${viewModal.addresslistState.value[0].PinCode}\n${viewModal.addresslistState.value[0].LandMark}"
                )
                selectedIndex.value = viewModal.addresslistState.value.first().id.toInt()
                call("containsData")
                items(viewModal.addresslistState.value) { data ->
                    if (data.PinCode == 1) {
                        AddAddress() {
                            navController.navigate(ScreenRoute.AddressScreen.route)
                        }
                    } else {
                        val bool =
                            selectedIndex.value == data.id.toInt()// Define the text color conditionally

                        AddressFiled(data, bool) {
                            selectedIndex.value = data.id.toInt()
                            passingaddress(it)
                        }
                    }
                }
            } else {
                call("empty")
            }

        }
        Spacer(modifier = Modifier.height((2.dp)))



        if (viewModal.addresslistState.value.size > 1)
            SwipeButton(
                text = "Swipe with cod",
                isComplete = isComplete,
                backgroundColor = seallcolor,
                onSwipe = {
                    call("ProceedButton")
                },
            )
        else
            CommonButton(
                text = "Proceed to address",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            {
                if (it)
                    call("addressEmpty")
            }


    }


}

@Composable
fun AppliedCupon(title: String?, percenage: Int?, clickOnRemove: (Boolean) -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Image
            Image(
                painter = painterResource(id = R.drawable.tick),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            // Space between Image and Text
            Spacer(modifier = Modifier.width(8.dp))

            // Text Column
            Column {
                Text14_h2(
                    text = title ?: "null",
                    color = Color.Black
                )
                Text11_body2(
                    text = " Applied $percenage",
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text14_h2(
                text = "Remove",
                color = lightred,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        clickOnRemove(true)
                    }
            )
        }

    }

    // Description Text

}


@Composable
fun noHistoryAvailable(text: String) {
    Box1(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(250.dp))
        Text12_body1(text = text, modifier = Modifier.align(Alignment.Center))


    }

}


@Composable
fun AddressFiled(
    data: AddressItems,
    isSelected: Boolean,
    onAddressClick: (String) -> Unit
) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
            .clip(RoundedCornerShape(1.dp))
            .clickable {
                onAddressClick(
                    "${data.customer_name}\n ${data.Address1}\n ${data.Address2} \n${data.PinCode} \n${data.LandMark}"
                )
            }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(130.dp)
            ) {
                Text12_h1(
                    text = data.customer_name,
                    color = headingColor, modifier = Modifier.fillMaxWidth()
                )
                if (isSelected)
                    Text12_h1(
                        text = "you're here", modifier = Modifier.fillMaxWidth(),
                        color = Purple700
                    )
            }
            Text11_body2(text = data.Address1, greyLightColor)
            Text11_body2(text = "${data.Address2}, ${data.PinCode},", greyLightColor)
            Text11_body2(text = data.LandMark, greyLightColor)

        }
    }
}


@Composable
fun AddAddress(call: (Boolean) -> Unit) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    androidx.compose.foundation.layout.Box(

        modifier = Modifier
            .padding(horizontal = 5.dp)
            .width(140.dp)
            .height(80.dp)
            .background(whiteColor)
            .drawBehind {

                drawRoundRect(color = greyLightColor, style = stroke)

            }
            .clickable {
                call(true)

            },


        ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .width(150.dp)
                .height(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text11_body2(text = "Add new Address+", color = headingColor)

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ItemEachRow(
    data: CartItems,
    viewModal: CartItemsViewModal,
    context: Activity,


    ) {
    //order.add(OrderIdCreateRequest.Order(productId=data.ProductIdNumber, productName = data.strProductName, productprice = data.strProductPrice.toString(), quantity = viewModal.productIdCountState.value.toString()))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Image(
                painter = rememberImagePainter(data.strCategoryThumb),
                contentDescription = "",
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text10_h2(
                        text = "${data.strProductName}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    IconButton(modifier = Modifier
                        .height(20.dp)
                        .width(20.dp), onClick = {
                        viewModal.deleteProduct(data.ProductIdNumber)
                        context.showMsg("1 item deleted")

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))





                Text12_with_strikethrough(
                    text1 = "₹ ${data.strProductPrice}",
                    text2 = "₹${data.actualprice ?: "0.00"}",
                    color = headingColor,

                    )


//                Text14_400(text = data.strProductName.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        CommonMathButton(icon = R.drawable.minus) {
                            viewModal.deleteCartItems(data.ProductIdNumber)


                        }
                        Text12_body1(
                            text = data.totalCount.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 20.dp),
                            color = Color.Black
                        )
                        CommonMathButton(icon = R.drawable.add) {
                            viewModal.insertCartItem(
                                data.ProductIdNumber ?: "",
                                data.strCategoryThumb ?: "",
                                data.strProductPrice ?: 0,
                                data.strProductName ?: "",
                                data.actualprice ?: ""
                            )


                        }
                    }


                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box1(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

    }

}


