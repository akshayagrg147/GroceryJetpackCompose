package com.grocery.groceryapp.features.Home.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
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
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.CommonProgressBar
import com.grocery.groceryapp.data.modal.OrderIdCreateRequest
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Home.ui.viewmodal.CartItemsViewModal
import com.grocery.groceryapp.features.Spash.SplashNavigation.ScreenRoute
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
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isDialog by remember { mutableStateOf(false) }


    val choose: MutableState<Boolean> = remember { mutableStateOf(false) }
    var addressvalue: String? = null
    val order: ArrayList<OrderIdCreateRequest.Order> = ArrayList()

    viewModal.getCartItem()
    viewModal.getSavingAmount()
    if (isDialog)
        CommonProgressBar()
    Log.d("messagecoming", "call cart screen")



        when (viewModal.orderConfirmedStatusState.value.statusCode) {
            200 -> {

                LaunchedEffect(key1 = true ){
                    viewModal.orderConfirmedStatusState.value.apply {

                        navController.currentBackStackEntry?.arguments?.putParcelable(
                            "orderstatus",
                            viewModal.orderConfirmedStatusState.value
                        )
                        navController.navigate(DashBoardNavRoute.OrderSuccessful.screen_route)
                    }
                }
                //  scope.launch { modalBottomSheetState.hide() }


            }
            401 -> {
                isDialog = false
                //  scope.launch { modalBottomSheetState.hide() }

            }
            else -> {
                // scope.launch { modalBottomSheetState.hide() }
                Log.d("messagecoming", "someting went wrong")
            }
        }

    ModalBottomSheetLayout(
        sheetContent = {

            Column(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text14_h1(text = "Order Summary", color = Color.Black)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text12_body1(
                        text = "Order Amount",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text12_body1(
                        text = "₹ ${viewModal.totalPriceState.value}",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text12_body1(
                        text = "Service Charges",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text12_body1(
                        text = "₹ ${viewModal.totalPriceState.value * 0.1}",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

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
                                Log.d("messagepassing","${Gson().toJson(request)}")
                                viewModal.passingOrderIdGenerateRequest(request)
                                viewModal.callingBookingOrder()


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
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )


    ) {

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
                    Header1(scroll, headerHeightPx, viewModal)
                    Body1(order, scroll, viewModal, context)
                    Toolbar1(scroll, headerHeightPx, toolbarHeightPx)
                    //  Title1( "none", scroll, headerHeightPx, toolbarHeightPx)

                }


            }
            //  if(viewModal.responseLiveData.value.isNotEmpty())
            Card(
                modifier = Modifier.align(Alignment.BottomCenter),
                elevation = 0.dp,
                shape = CutCornerShape(30.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(colorResource(id = R.color.green_700))
                        .clickable {
                            viewModal.getAllAddressItems()

                            scope.launch { modalBottomSheetState.show() }
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text14_h1(
                        text = "Go to Checkout(₹ ${viewModal.totalPriceState.value})",
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                    )

                }
            }
        }


    }
}

@Composable
private fun Header1(
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
fun Body1(
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
private fun Toolbar1(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
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
            text = "Proceed to payment",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        {

            call("ProceedButton")
        }


    }


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
        elevation = 1.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White),
        backgroundColor = if (selectedIndex.value == data.id.toInt()) greycolor else Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(120.dp)
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
                Text12_body1(text = data.customer_name)
                Text12_body1(text = data.Address1)
                Text12_body1(text = "${data.Address2}, ${data.PinCode},")
                Text12_body1(text = data.LandMark)

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
                    Text13_body1(
                        text = "${data.strProductName}",
                        modifier = Modifier.align(Alignment.CenterVertically)
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

                Text12_body1(
                    text = "₹ ${data.strProductPrice}",
                    color = blackColor,
                )
                Text(
                    text = "₹${data.actualprice ?: "0.00"}",
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


