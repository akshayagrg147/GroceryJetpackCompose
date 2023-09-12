package com.grocery.mandixpress.features.Home.ui

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

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.RoomDatabase.CartItems
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.data.modal.OrderIdCreateRequest
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.CartEvent
import com.grocery.mandixpress.features.Home.ui.viewmodal.CartItemsViewModal
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import kotlinx.coroutines.launch

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
        confirmStateChange = {
            // Provide an anchor for the initial state (e.g., 0f, 0.5f, 1f)
            it == ModalBottomSheetValue.Hidden || it == ModalBottomSheetValue.Expanded
        }
    )

    val choose: MutableState<Boolean> = remember { mutableStateOf(false) }
    var addressvalue: String? = null
    val order: ArrayList<OrderIdCreateRequest.Order> = ArrayList()

    viewModal.getCartItem()
    viewModal.getSavingAmount()
    if (isDialog)
        CommonProgressBar()



    val orderIDResponse by viewModal.sharedFlow.collectAsState()
    if(orderIDResponse.data?.statusCode==200){
        Log.d("ordercreation","called ${orderIDResponse.data} ")
        isDialog=false
        LaunchedEffect(key1 = Unit){
            navController.currentBackStackEntry?.arguments?.putParcelable(
                "orderstatus",
                orderIDResponse.data
            )
            navController.navigate(DashBoardNavRoute.OrderSuccessful.screen_route) {
//                popUpTo(DashBoardNavRoute.Home.screen_route) {
//                    inclusive = true
//                }
        }


        }
    }
    else if (orderIDResponse.error.isNotEmpty()) {
        isDialog=false
        Toast.makeText(context, orderIDResponse.error, Toast.LENGTH_SHORT).show()
    }


    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()
              ){

                Box(
                    modifier = Modifier.fillMaxWidth()
                    ,
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.close_button), // Replace with your image resource
                        contentDescription = "Cross Button",
                        modifier = Modifier, // Adjust the size as needed
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
                            )
                        )

                ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text14_h1(text = "Order Summary", color = Color.Black)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
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
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text10_h2(
                                text = "Service Charges",
                                color = headingColor,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text10_h2(
                                text = "₹ ${String.format("%.2f",viewModal.totalPriceState.value * 0.1)}",
                                color = headingColor,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                text1 = if (viewModal.totalPriceState.value.toInt() > 100) "₹ 30" else "₹ 30 ",
                                text2 = "FREE",
                                color = headingColor,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }




                        var backBundleData =
                            navController.currentBackStackEntry?.savedStateHandle?.let {
                                it.get<Bundle>("passCoupon")
                            }
                        val grandTotal = if (viewModal.totalPriceState.value.toInt() < 100) {
                            "₹ " + ((30) + (viewModal.totalPriceState.value.toDouble()))
                        } else {
                            "₹ ${viewModal.totalPriceState.value .toDouble() * 0.9}"
                        }
                        if (passCoupon) {
                            if ((backBundleData?.get("CouponPercent") != null))

                            { val cuponDiscount =
                                (
                                        (((backBundleData?.get("CouponPercent") as Int) * grandTotal.replace(
                                            "₹ ",
                                            ""
                                        ).toDoubleOrNull()!!)) / 100)
                            Log.d("cupondiscount", cuponDiscount.toString())


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
                                    text = (String.format("%.2f",cuponDiscount)

                                            ).toString(),
                                    color = headingColor,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            Spacer(modifier = Modifier.height((20.dp)))

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
                                    text = String.format("%.2f",(grandTotal.replace("₹ ", "").toDoubleOrNull()
                                        ?.minus(cuponDiscount!!.toInt()))).toString(),
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
                                    text = String.format("%.2f",(grandTotal.replace("₹ ", "")).toDoubleOrNull()).toString(),
                                    color = headingColor,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                        LaunchedEffect(key1 = Unit){
                            if (backBundleData != null)
                                passCoupon = true
                        }

                        if (passCoupon) {
                            if(backBundleData?.get("CouponPercent")!=null){
                                val percent: Int = backBundleData?.get("CouponPercent") as Int
                                AppliedCupon(backBundleData.get("CouponName").toString(), percent) {
                                    backBundleData=null
                                    passCoupon = false;
                                }
                            }


                        }
                        else {
                            Button(
                                onClick = {
                                    navController.navigate(DashBoardNavRoute.ApplyCoupons.screen_route)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            ) {
                                Text(text = "Apply Coupon")
                            }
                        }
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
                    AddressComponent(viewModal, navController, {
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
                                    mobilenumber = sharedpreferenceCommon.getMobileNumber()
                                )
                                Log.d("messagepassing", "${Gson().toJson(request)}")
                                viewModal.onEvent(CartEvent.createOrderId(request))


                            }
                            else -> {
                                choose.value = false
                            }
                        }
                    }, {
                        addressvalue = it
                    })
                }

            }}
        },
        sheetBackgroundColor = Color.White.copy(alpha = 0.0f),
        sheetState = modalBottomSheetState,

    )
    {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
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
                    Box(modifier = Modifier
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

        @Composable
        fun TransparentCrossButton(onClick: () -> Unit) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = Color.White.copy(alpha = 0.33f))
            ) {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
        }


@Composable
private fun Header(
    scroll: ScrollState,
    headerHeightPx: Float,
    viewModal: CartItemsViewModal,

    ) {
    Box(modifier = Modifier
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
                backgroundColor = titleColor, modifier = Modifier
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
            noHistoryAvailable()
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
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                )
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Checkout",
                        tint = Color.Black
                    )
                }
            },
            title = { "Checkout" },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}

@Composable
private fun Title1(
    str: String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text(
        text = str,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleYFirstInterpolatedPoint = lerp(
                    headerHeight - titleHeightPx.toDp() - paddingMedium,
                    headerHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                val titleX = lerp(
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
            }
    )
}


@Composable
fun Header(scroll: Unit, headerHeightPx: Unit) {

}

@Composable
fun AddressComponent(
    viewModal: CartItemsViewModal,
    navController: NavHostController,
    call: (String) -> Unit, passingaddress: (String) -> Unit
) {


    var selectedIndex = remember { mutableStateOf(1) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
            // .height(260.dp)
        ) {
            if (viewModal.addresslistState.value.isNotEmpty()) {
                call("containsData")
                items(viewModal.addresslistState.value) { data ->
                    AddressFiled(data, selectedIndex) {
                        passingaddress(it)
                    }
                }
            } else {
                call("empty")
            }

        }
        Spacer(modifier = Modifier.height((2.dp)))
        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .clickable {
                navController.navigate(ScreenRoute.AddressScreen.route)
            }) {
            Text13_body1(
                text = "Add Address+", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
                    .align(Alignment.Center), color = navdrawerColor
            )

        }


        Spacer(modifier = Modifier.height((20.dp)))
        CommonButton(
            enabled = viewModal.addresslistState.value.isNotEmpty(),
            text = "Proceed to payment",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        {
            if (it)
                call("ProceedButton")
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
                Text(
                    text = title ?: "null",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = " Applied $percenage",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Remove",
                fontSize = 14.sp,
                color = Color.Magenta,
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
fun noHistoryAvailable() {
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(250.dp))
        Text12_body1(text = "No order available", modifier = Modifier.align(Alignment.Center))


    }

}

@Composable
fun AddressFiled(data: AddressItems, selectedIndex: MutableState<Int>, address: (String) -> Unit) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        backgroundColor = if (selectedIndex.value == data.id.toInt()) greyLightColor else Color.White,
        modifier = Modifier
            .fillMaxWidth()


            .padding(start = 5.dp)
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .clickable {
                selectedIndex.value = data.id.toInt()
                address("${data.customer_name}\n ${data.Address1}\n ${data.Address2} \n${data.PinCode} \n${data.LandMark}")


            }

    ) {
        Box(
            modifier = Modifier

        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text12_h1(text = data.customer_name,color= if (selectedIndex.value == data.id.toInt()) whiteColor else blackColor,)
                Text12_h1(text = data.Address1,color= if (selectedIndex.value == data.id.toInt()) whiteColor else blackColor)
                Text12_h1(text = "${data.Address2}, ${data.PinCode},",color= if (selectedIndex.value == data.id.toInt()) whiteColor else blackColor)
                Text12_h1(text = data.LandMark,color= if (selectedIndex.value == data.id.toInt()) whiteColor else blackColor)

            }
        }
    }


}


@Composable
fun SimpleRadioButtonComponent() {
    val radioOptions = listOf("DSA", "Java", "C++")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) })
                        .padding(horizontal = 16.dp)
                ) {
                    val context = LocalContext.current
                    RadioButton(

                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {

                            onOptionSelected(text)
                            context.showMsg(text)
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
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
                        modifier = Modifier.fillMaxWidth().weight(1f).align(Alignment.CenterVertically)
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

                Text10_h2(
                    text = "₹ ${data.strProductPrice}",
                    color = headingColor,
                )





                Text(
                    text = "₹${data.actualprice ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

    }

}


