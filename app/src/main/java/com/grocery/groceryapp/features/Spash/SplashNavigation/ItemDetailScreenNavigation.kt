package com.grocery.groceryapp.features.Spash

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.ui.CartActivity
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.redColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProductByIdViewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ItemScreenNavigation(
    context: Context,
    productId: String?,
    category: String?,
    viewModal: ProductByIdViewModal = hiltViewModel()
) {


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.ItemDetailScreen.route) {
        composable(ScreenRoute.ItemDetailScreen.route) {
            when (category) {
                "Best" -> viewModal.calllingBestProductById(ProductIdIdModal(productId = productId))
                "exclusive" -> viewModal.calllingExclsuiveProductById(ProductIdIdModal(productId = productId))
                else -> {
                    viewModal.calllingAllProductById(ProductIdIdModal(productId = productId))
                }
            }
            var response = viewModal.responseLiveData
            if (response.value.statusCode == 200) {
                ItemDetailsScreen(response.value, context, viewModal)
            } else {
                Toast.makeText(context, response.value.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ItemDetailsScreen(
    value: ProductByIdResponseModal,
    context: Context,
    viewModal: ProductByIdViewModal
) {
    val cartcount = remember { mutableStateOf(0) }

    val pager = rememberPagerState()
    viewModal.getItemBaseOnProductId(value)
    cartcount.value= viewModal.productIdCount.value
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (l1, l2) = createRefs()
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .constrainAs(l1) {
                top.linkTo(parent.top)

            }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    HorizontalPager(count = 3, state = pager) { index ->
                        if (index == 0)
                            Banner(pagerState = pager, value.homeproducts?.productImage1 ?: "")
                        if (index == 1)
                            Banner(pagerState = pager, value.homeproducts?.productImage2 ?: "")
                        if (index == 2)
                            Banner(pagerState = pager, value.homeproducts?.productImage3 ?: "")
                    }

                }


                val itemcart: MutableState<Int> = remember { mutableStateOf(1) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text20_700(
                        text = value.homeproducts?.productName ?: "",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.heart_icon),
                            contentDescription = "",
                            tint = redColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text14_400(
                    text = value.homeproducts?.quantity ?: "", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                        viewModal.getCartItem()


                    Row {
                        CommonButton(icon = R.drawable.minus) {

                            if(cartcount.value>1) {
                                cartcount.value -= 1
                                viewModal.deleteCartItems(value)
                                viewModal.getCartItem()
                            }
                            else
                                Toast.makeText(context, "can not be negative", Toast.LENGTH_SHORT).show()
                        }

                            Text16_700(
                                text =  if(cartcount.value==0) "1" else cartcount.value.toString(),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(horizontal = 20.dp),
                                color = Color.Black
                            )
                        CommonButton(icon = R.drawable.add) {
                            viewModal.insertCartItem(value)
                                viewModal.getCartItem()
                            cartcount.value += 1


                        }
                    }

                    Text18_600(
                        text = if(cartcount.value==0) "₹ ${(Integer.parseInt(value.homeproducts?.price))}" else "₹ ${(java.lang.Integer.parseInt(value.homeproducts?.price)*cartcount.value)} ",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                TabLayout(value)

            }
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                var createguidlinefromtop = createGuidelineFromBottom(70.dp)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .constrainAs(l2) {
                        top.linkTo(createguidlinefromtop)
                        bottom.linkTo(createguidlinefromtop)
                        end.linkTo((parent.end))

                    }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text16_700(text = viewModal.getitemcount.value.toString())
                            Image(painter = painterResource(id = R.drawable.cart_symbol),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(50.dp)
                                    .clickable {
                                        context.launchActivity<CartActivity>() {

                                        }
                                    })

                        }
                    }


                }


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
            .padding(20.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(productImage1),
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
        "Manufacture",
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
                        color = if (pagerState.currentPage == index) Color.Black else Color.DarkGray
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
    HorizontalPager(state = pagerState, count = 3) {
        // on below line we are specifying
        // the different pages.
            page ->
        when (page) {
            // on below line we are calling tab content screen
            // and specifying data as Home Screen.
            0 -> TabContentScreen(data = value.homeproducts?.ProductDescription ?: "null")
            // on below line we are calling tab content screen
            // and specifying data as Shopping Screen.
            1 -> TabContentScreen(data = value.homeproducts?.productName ?: "null")
            // on below line we are calling tab content screen
            // and specifying data as Settings Screen.
            2 -> ReviewsCollection(value)
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
        Text18_600(text = data)

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

