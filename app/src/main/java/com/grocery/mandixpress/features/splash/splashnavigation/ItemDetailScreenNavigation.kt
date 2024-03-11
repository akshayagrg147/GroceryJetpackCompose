package com.grocery.mandixpress.features.splash.splashnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.CustomDialog
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ProductByIdResponseModal
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.data.modal.RelatedSearchRequest
import com.grocery.mandixpress.features.home.ui.screens.ShimmerAnimation
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.ProductByIdViewModal
import com.grocery.mandixpress.features.home.ui.viewmodal.ProductEvents
import com.grocery.mandixpress.features.home.ui.viewmodal.ProductEvents.RelatedSearchEvents
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import com.grocery.mandixpress.features.home.ui.ui.theme.bodyTextColor as bodyTextColor1

@Composable
fun ItemScreenNavigation(
    context: Context,
    productId: String?,

    navController: NavHostController,
    viewModal: ProductByIdViewModal = hiltViewModel()
) {
    val _itemDetailFlow by viewModal.itemDetailFlow.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        viewModal.onEvents(
            RelatedSearchEvents(RelatedSearchRequest(Price = "50", category = "grocery"))

        )
        viewModal.onEvents(
            ProductEvents.ItemDetailEvent(ProductIdIdModal(productId))

        )
    }

    var response = _itemDetailFlow
    if (_itemDetailFlow.isLoading) {
        CircularProgressBar(
            progress = 0.75f, // 75% progress
            isLoading = isLoading, // Show CircularProgressIndicator when loading
            progressColor = Color.Blue,
            backgroundColor = Color.LightGray,
            strokeWidth = 8f
        )

    } else if (_itemDetailFlow.data != null) {
        isLoading = false
        ItemDetailsScreen(response.data?: ProductByIdResponseModal(), navController, context)
    } else if (_itemDetailFlow.error.isNotEmpty()) {
        isLoading = false
        context.showMsg(_itemDetailFlow.error)

    }


}

@Composable
fun RelatedSearchItem(
    data: HomeAllProductsResponse.HomeResponse,
    context: Context,
    navcontroller: NavHostController
) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)

            .width(150.dp)
            .clickable {
                navcontroller.navigate(
                    DashBoardNavRoute.ProductDetail.senddata(
                        data.ProductId ?: ""
                    )
                )
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {


            val originalPrice = data.orignal_price?.toFloat() ?: 0.0f
            val sellingPrice = data.selling_price?.toFloat() ?: 0.0f

            val offPercentage = ((originalPrice - sellingPrice) / originalPrice) * 100

            val formattedPercentage = DecimalFormat("#").format(offPercentage)

            Text10_h2(
                text = "${formattedPercentage.toInt()}% off", color = sec20timer,
                modifier = Modifier.align(
                    Alignment.End
                ),

                )

            Image(

                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text12_h1(
                text = data.productName?:"", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text10_h2(
                text = "${data.quantityInstructionController}", color = bodyTextColor1,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text10_h2(
                    text = "₹ ${data.selling_price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.orignal_price ?: "0.00"}",
                    fontSize = 11.sp,
                    color = com.grocery.mandixpress.features.home.ui.ui.theme.bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 10.dp)

                        .background(color = whiteColor),

                    ) {
                    Text11_body2(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }


            }

        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun ItemDetailsScreen(
    value: ProductByIdResponseModal,
    navController: NavHostController,
    context: Context,
    viewModal: ProductByIdViewModal = hiltViewModel()
) {
    val cartcount = remember { mutableStateOf(0) }
    val relatedSearch by viewModal.eventRelatedSearchFlow.collectAsState()
    var newSellerAddedDialog by remember { mutableStateOf(false) }

    val pager = rememberPagerState(3)
    viewModal.getItemBaseOnProductId(value.homeproducts?.productId ?: "")
    viewModal.getTotalProductItemsPrice()
    viewModal.getTotalProductItems()

    cartcount.value = viewModal.productIdCount.value
    Box(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (l1, l2) = createRefs()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(l1) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }

                        .padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
                        ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Text16_h1(
                        text = "Product Screen",
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(start = 10.dp),
                        color = navdrawerColor
                    )
                }
                LazyColumn(
                    //  verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .constrainAs(l2) {
                            top.linkTo(l1.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
//            .padding(bottom = 50.dp)
                    // .verticalScroll(state = scrollState)

                ) {
                    item {
                        //tab view for image
                        Column(modifier = Modifier.fillMaxWidth()) {

                            Spacer(modifier = Modifier.height(10.dp))
                            Card(
                                elevation = 1.dp,
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.padding(horizontal = 10.dp)

                            ) {
                                Column (modifier = Modifier.padding(10.dp)
                                    ){
                                    HorizontalPager(state = pager) { index ->
                                        if (index == 0)
                                            Banner(

                                                value.homeproducts?.productImage1 ?: ""
                                            )
                                        if (index == 1)
                                            Banner(

                                                value.homeproducts?.productImage2 ?: ""
                                            )
                                        if (index == 2)
                                            Banner(

                                                value.homeproducts?.productImage3 ?: ""
                                            )
                                    }
                                    PagerDots(
                                        currentPage = pager.currentPage,
                                        pageCount = pager.pageCount,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally)
                                            .then(Modifier.horizontalScroll(rememberScrollState()))
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp, top = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text12_h1(
                                            text = value.homeproducts?.productName ?: "",
                                            color = headingColor,

                                            )

                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(
                                        modifier = Modifier.padding(start = 10.dp),
                                    ) {

                                        Text12_body1(
                                            text = "₹ ${value.homeproducts?.selling_price}",
                                            color = headingColor
                                        )
                                        Text(
                                            text = "₹${value.homeproducts?.orignalprice ?: "0.00"}",
                                            fontSize = 12.sp,
                                            color = bodyTextColor1,
                                            modifier = Modifier.padding(start = 5.dp),
                                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    //add button
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {


                                        if (cartcount.value > 0)
                                            Row {
                                                CommonMathButton(icon = R.drawable.minus) {
                                                    viewModal.deleteCartItems(value)
                                                }

                                                Text12_body1(
                                                    text = cartcount.value.toString(),
                                                    modifier = Modifier
                                                        .align(Alignment.CenterVertically)
                                                        .padding(horizontal = 20.dp),
                                                    color = Color.Black
                                                )

                                                if(newSellerAddedDialog)
                                                    com.grocery.mandixpress.common.CustomDialog(
                                                        title = "Mandi Express",
                                                        message = "Delivery charges may change as you are adding to other seller",
                                                        onShowDialog = {
                                                            newSellerAddedDialog=false


                                                        }
                                                        , onYesClick = {
                                                            newSellerAddedDialog=false

                                                            viewModal.updateDeliveryCharges(viewModal.getStoreAdminCartTable().first, viewModal.getStoreAdminCartTable().second)
                                                            { it ->
                                                                if (it != 0) {
                                                                    MainScope().launch {
                                                                        Toast
                                                                            .makeText(
                                                                                context,
                                                                                "Added to cart",
                                                                                Toast.LENGTH_SHORT
                                                                            )
                                                                            .show()

                                                                        Utils.vibrator(context)
                                                                    }
                                                                }
                                                            }

                                                        })
                                                CommonMathButton(icon = R.drawable.add) {
                                                    viewModal.insertCartItem(value){access,cart->
                                                        viewModal.tempStoreAdminCartTable(access,cart)


                                                        newSellerAddedDialog=true



                                                    }


                                                }
                                            }
                                        else {
                                            if(newSellerAddedDialog)
                                                com.grocery.mandixpress.common.CustomDialog(
                                                    title = "Mandi Express",
                                                    message = "Delivery charges may change as you are adding to other seller",
                                                    onShowDialog = {
                                                        newSellerAddedDialog=false


                                                    }
                                                    , onYesClick = {
                                                        newSellerAddedDialog=false
                                                        viewModal.updateDeliveryCharges(viewModal.getStoreAdminCartTable().first, viewModal.getStoreAdminCartTable().second)
                                                        { it ->
                                                            if (it != 0) {
                                                                MainScope().launch {
                                                                    Toast
                                                                        .makeText(
                                                                            context,
                                                                            "Added to cart",
                                                                            Toast.LENGTH_SHORT
                                                                        )
                                                                        .show()

                                                                    Utils.vibrator(context)
                                                                }
                                                            }
                                                        }

                                                    })
                                            Card(
                                                border = BorderStroke(1.dp, titleColor),
                                                modifier = Modifier

                                                    .clip(
                                                        RoundedCornerShape(
                                                            5.dp,
                                                            5.dp,
                                                            5.dp,
                                                            5.dp
                                                        )
                                                    )
                                                    .padding(start = 10.dp)

                                                    .background(color = whiteColor)
                                                    .clickable {
                                                        viewModal.insertCartItem(value) { access, cart ->
                                                            viewModal.tempStoreAdminCartTable(
                                                                access,
                                                                cart
                                                            )


                                                            newSellerAddedDialog = true


                                                        }


                                                    },

                                                ) {
                                                Text11_body2(
                                                    text = "ADD",
                                                    availColor,
                                                    modifier = Modifier.padding(
                                                        vertical = 5.dp,
                                                        horizontal = 5.dp
                                                    )
                                                )
                                            }
                                        }


                                    }

                                }

                            }



                            Spacer(modifier = Modifier.height(10.dp))
                            TabContentScreen(
                                data = value.homeproducts?.ProductDescription ?: "null"
                            )


                            //    TabLayout(value)


                        }
                    }

                    //related search list
                    item {

                        if (relatedSearch.isLoading) {
                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth()
                                // .height(260.dp)
                            ) {
                                repeat(5) {
                                    item {
                                        ShimmerAnimation()

                                    }
                                }
                            }

                        } else if (relatedSearch.data != null) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp), Arrangement.SpaceBetween
                            ) {
                                Text13_body1(
                                    text = "Related Search", color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 10.dp),
                                )

                            }
                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth()
                                // .height(260.dp)
                            ) {
                                val relatedresponse = relatedSearch.data
                                if (relatedresponse?.statusCode == 200) {
                                    val list1 = relatedresponse.list
                                    items(list1 ?: emptyList(), key = { it.hashCode() }) { data ->
                                        RelatedSearchItem(data, context, navController)
                                    }


                                }

                            }
                            Spacer(modifier = Modifier.height(150.dp))
                        }


                    }
                }

            }



        //view cart
        if (viewModal.totalPriceState.value >= 1 &&(viewModal.getFreeDeliveryMinPrice()>0.0))
            CardviewAddtoCart(
                navController,
                context,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
    }


}

@Composable
fun PagerDots(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (page in 0 until pageCount) {
            val isActive = (page == currentPage)
            val scale by animateFloatAsState(targetValue = if (isActive) 1.2f else 1.0f)
            val color = if (isActive) seallcolor else greyLightColor

            Box(
                modifier = Modifier
                    .size(13.dp)
                    .padding(horizontal = 3.dp)
                    .background(color = color, shape = CircleShape)
                    .graphicsLayer(scaleX = scale, scaleY = scale)
            )
        }
    }
}



@Composable
fun CardviewAddtoCart(

    navController: NavHostController,
    context: Context,
    modifier: Modifier, viewmodal: ProductByIdViewModal = hiltViewModel(),
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
                                text = if(viewmodal.withHigherCartItemTotal() < viewmodal.getFreeDeliveryMinPrice()) (30+viewmodal.getSellersMinDeliveryCharge().toDouble()).toString() else viewmodal.getSellersMinDeliveryCharge(),
                                color = headingColor,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
            }
            else if(viewmodal.getFreeDeliveryMinPrice()>0.00) {
                if (viewmodal.totalPriceState.value < (viewmodal.getFreeDeliveryMinPrice()
                        .toInt())
                ) {
                    Row(modifier = Modifier) {
                        Image(
                            painter = painterResource(id = com.grocery.mandixpress.R.drawable.bike_delivery),

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
                                text = "Add item worth ${
                                    viewmodal.getFreeDeliveryMinPrice()
                                        .toInt() - viewmodal.totalPriceState.value
                                }",
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
                                text = "${viewmodal.totalCountState.value.toString()} items",
                                color = Color.White
                            )
                            Text12_body1(
                                text = "₹ ${viewmodal.totalPriceState.value.toString()}",
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
}


@Composable
fun Banner(
    productImage1: String?
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Image(
            painter = rememberImagePainter(productImage1),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()

        )

    }

}


@Composable
fun TabContentScreen(data: String) {
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ExpandableText(initialText = data,)


    }
}
@Composable
fun ExpandableText(initialText: String, maxLines: Int = 3) {
    var isExpanded by remember { mutableStateOf(false) }

    // Limit the number of lines to 'maxLines' when not expanded
    val textToShow = if (isExpanded) initialText else initialText.split("\n")
        .take(maxLines)
        .joinToString("\n")

    Column {
        Text(
            text = textToShow,
            fontSize = 12.sp,
            color = greyLightColor,

            modifier = Modifier.clickable { isExpanded = !isExpanded }
        )
        if (!isExpanded) {
            Text(
                text = "Show more",
                color = iconColor,
                fontSize = 11.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}






