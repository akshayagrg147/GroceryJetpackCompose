package com.grocery.groceryapp.features.Spash

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.Utils
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProductByIdViewModal
import kotlinx.coroutines.launch
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor as bodyTextColor1

@Composable
fun ItemScreenNavigation(
    context: Context,
    productId: String?,
    category: String?,
    navController: NavHostController,
    viewModal: ProductByIdViewModal = hiltViewModel()
) {


    when (category) {
        "Best" -> {
            viewModal.setData(ProductIdIdModal(productId = productId))
            viewModal.calllingBestProductById()
        }
        "exclusive" -> {
            viewModal.setData(ProductIdIdModal(productId = productId))
            viewModal.calllingExclsuiveProductById()
        }
        else -> {

            viewModal.setData(ProductIdIdModal(productId = productId))
            viewModal.calllingAllProductById()

        }
    }
    var response = viewModal.responseLiveData
    if (response.value.statusCode == 200) {
        ItemDetailsScreen(response.value, navController, context, viewModal)
    }


}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ItemDetailsScreen(
    value: ProductByIdResponseModal,
    navController: NavHostController,
    context: Context,
    viewModal: ProductByIdViewModal
) {
    val cartcount = remember { mutableStateOf(0) }

    val pager = rememberPagerState()
    viewModal.getItemBaseOnProductId(value)
    cartcount.value = viewModal.productIdCount.value
    Box(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (l1, l2) = createRefs()
            Box(modifier = Modifier
                .constrainAs(l1) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)

                }
            ) {
                Column(
                    //  verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
//            .padding(bottom = 50.dp)
                    // .verticalScroll(state = scrollState)

                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            HorizontalPager(count = 3, state = pager) { index ->
                                if (index == 0)
                                    Banner(
                                        pagerState = pager,
                                        value.homeproducts?.productImage1 ?: ""
                                    )
                                if (index == 1)
                                    Banner(
                                        pagerState = pager,
                                        value.homeproducts?.productImage2 ?: ""
                                    )
                                if (index == 2)
                                    Banner(
                                        pagerState = pager,
                                        value.homeproducts?.productImage3 ?: ""
                                    )
                            }

                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = value.homeproducts?.productName ?: "", color = titleColor, style = loginTypography.h1, fontSize = 18.sp,
                            )
                            Text14_400(
                                text = value.homeproducts?.quantity ?: "", modifier = Modifier
                                    .padding(top = 5.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(2.dp))


                        Row(
                            modifier = Modifier.padding(start = 20.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Text14_400(
                                text = "₹ ${value.homeproducts?.price}",
                                color = headingColor,
                                //  modifier= Modifier.weight(0.5F)
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {


                            viewModal.getCartItem()

                            if (cartcount.value > 0)
                                Row {
                                    CommonMathButton(icon = R.drawable.minus) {
                                        viewModal.deleteCartItems(value)
                                    }

                                    Text14_400(
                                        text = cartcount.value.toString(),
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(horizontal = 20.dp),
                                        color = Color.Black
                                    )
                                    CommonMathButton(icon = R.drawable.add) {
                                        viewModal.insertCartItem(value)


                                    }
                                }
                            else {
                                Card(
                                    border = BorderStroke(1.dp, titleColor),
                                    modifier = Modifier

                                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                                        .padding(start = 20.dp)

                                        .background(color = whiteColor)
                                        .clickable {
                                            // response="called"
                                            viewModal.insertCartItem(value)
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Added to cart",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()

                                            Utils.vibrator(context)

                                        },

                                    ) {
                                    Text13_700(
                                        text = "ADD",
                                        availColor,
                                        modifier = Modifier.padding(
                                            vertical = 5.dp,
                                            horizontal = 10.dp
                                        )
                                    )
                                }
                            }


                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        TabLayout(value)
                        Spacer(modifier = Modifier.height(45.dp))


                    }
                }


            }


        }
        cardviewAddtoCart(
            viewModal, navController,
            context,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }


}

@Composable
fun cardviewAddtoCart(
    viewModal: ProductByIdViewModal,
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
            .clickable { navController.navigate(DashBoardNavRoute.CartScreen.screen_route) }
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
                        text = "${viewModal.getitemcount.value.totalcount.toString()} items",
                        color = Color.White
                    )
                    Text14_400(
                        text = "₹ ${viewModal.getitemcount.value.totalprice.toString()}",
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    pagerState: PagerState,
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

// on below line we are creating a
// composable function for our tab layout
@OptIn(ExperimentalUnitApi::class)
@ExperimentalPagerApi
@Composable
fun TabLayout(value: ProductByIdResponseModal) {

    // on below line we are creating variable for pager state.
    val pagerState = rememberPagerState()

    // on below line we are creating a column for our widgets.
    Column(
        // for column we are specifying modifier on below line.
        modifier = Modifier.background(Color.White)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, value)
    }
}

// on below line we are
// creating a function for tabs
@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    // in this function we are creating a list
    // in this list we are specifying data as
    // name of the tab and the icon for it.


    val list = listOf(
        "Description",
        "Reviews"
    )

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.Black
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
//                icon = {
//                    Icon(imageVector = list[index].second, contentDescription = null)
//                }

                text = {
                    Text(
                        list[index],
                        color = if (pagerState.currentPage == index) Color.Black else Color.DarkGray,
                        fontSize = 11.sp
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, value: ProductByIdResponseModal) {
    // on below line we are creating
    // horizontal pager for our tab layout.
    HorizontalPager(state = pagerState, count = 2) {
        // on below line we are specifying
        // the different pages.
            page ->
        when (page) {
            // on below line we are calling tab content screen
            // and specifying data as Home Screen.
            0 -> TabContentScreen(data = value.homeproducts?.ProductDescription ?: "null")
            // on below line we are calling tab content screen
            // and specifying data as Shopping Screen.

            1 -> ReviewsCollection(value)
//            // on below line we are calling tab content screen
//            // and specifying data as Settings Screen.
//            2 -> TabContentScreen(data = value.homeproducts?.productName ?: "null")
        }
    }
}

// on below line we are creating a Tab Content
// Screen for displaying a simple text message.
@Composable
fun TabContentScreen(data: String) {
    // on below line we are creating a column
    Column(
        // in this column we are specifying modifier
        // and aligning it center of the screen on below lines.
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text16_700(text = data, color = greyLightColor)

    }
}

@Composable
fun ReviewsCollection(value: ProductByIdResponseModal) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(value.homeproducts?.rating!!) { data ->
            // Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon),

                    contentDescription = "splash image",
                    modifier = Modifier
                        .width(50.dp)
                        .height(40.dp)
                )
                Column() {
                    Text16_700(text = data?.name ?: "", modifier = Modifier.width(200.dp))
                    Text14_400(text = data?.remark ?: "", modifier = Modifier.width(200.dp))

                }
                Text14_400(text = "21 April 2020")


            }
            //}


        }

    }

}

