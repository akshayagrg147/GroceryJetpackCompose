package com.grocery.mandixpress.features.Spash.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute


import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.features.Home.Navigator.gridItems
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory

import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.CartItemsViewModal
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .size(200.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .size(200.dp)
                    .padding(start = 8.dp)
                    .background(brush = brush)
            )

        }

    }
}

@Composable
fun cardViewAddtoCart(

    navController: NavHostController,
    context: Context,
    modifier: Modifier,viewmodal: CartItemsViewModal= hiltViewModel(),
) {
    Card(
        elevation = 2.dp,
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
                    Text12_body1(
                        text = "${viewmodal.totalCountState.value} items",
                        color = Color.White
                    )
                    Text12_body1(text = "₹ ${viewmodal.totalPriceState.value}", color = Color.White)


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

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun     menuitems(
    navController: NavHostController,
    context: Context,

    viewModal: CartItemsViewModal = hiltViewModel()
) {
    val bundle = navController.previousBackStackEntry?.savedStateHandle?.get<getProductCategory.ItemData>("data") ?: getProductCategory.ItemData()
    val selectedIndex = remember { mutableStateOf("") }
    val passingvalue = bundle
    val productdetail = remember {
        mutableStateOf(ItemsCollectionsResponse.SubItems())
    }

    val scope = rememberCoroutineScope()
    val pager = rememberPagerState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    viewModal.getCartItem()
    ModalBottomSheetLayout(
        sheetContent = {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (l1, l2) = createRefs()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(l1) {
                        top.linkTo(parent.top)

                    }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            HorizontalPager(count = 3, state = pager) { index ->
                                if (index == 0)
                                    Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage1 ?: ""
                                    )
                                if (index == 1)
                                    Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage2 ?: ""
                                    )
                                if (index == 2)
                                    Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage3 ?: ""
                                    )
                            }

                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text12_h1(
                                text = productdetail.value.productName ?: "",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            IconButton(onClick = {

                            }) {

                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text13_body1(text = "Product Detail", modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(5.dp))
                        Text12_body1(
                            text = productdetail.value.productDescription ?: "",
                            modifier = Modifier.fillMaxWidth(),
                        )


                    }


                }


            }

        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text14_h1(
                    text = "Sub cart menus",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    ) {
                        selectedIndex.value = passingvalue.subCategoryList?.get(0)?.name ?: ""
                        viewModal.setProductId(ProductIdIdModal(passingvalue.subCategoryList?.get(0)?.name))

                        items(passingvalue.subCategoryList?: emptyList()) { item ->

                            ItemEachRow(
                                item,
                                selectedIndex
                            ) {  selectedvalue ->
                                Log.d("productidgetting", "$selectedvalue")
                                viewModal.setProductId(ProductIdIdModal(selectedvalue))
                                selectedIndex.value = selectedvalue

                            }
                        }

                    }
                    LazyColumn(
                        modifier = Modifier.background(MaterialTheme.colors.background),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        if (viewModal._itemsCollection.value.statusCode == 200) {
                            if(viewModal._itemsCollection.value.list?.isNotEmpty()==true)
                            gridItems(
                                data = viewModal._itemsCollection.value.list!!,
                                columnCount = 2,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 1.dp)
                            ) { itemData ->
                                MenuItemGrid(itemData, context) { passvalue ->
                                    productdetail.value = passvalue
                                    scope.launch { modalBottomSheetState.show() }
                                }
                            }
                            else{

                            }
                        } else {
                            repeat(5) {
                                item {
                                    ShimmerAnimation()

                                }
                            }
                        }
                    }


                }
            }
            cardViewAddtoCart(
                navController,
                context,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }


}


@Composable
fun ItemEachRow(
    item: getProductCategory.ItemData.SubCategory,
    selectedIndex: MutableState<String>,

    call: (item: String) -> Unit
) {
    Card(elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),

        backgroundColor = if (selectedIndex.value == item.name) Color.LightGray else Color.White,
        modifier = Modifier


            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .width(90.dp)
            .padding(5.dp)
            .clickable {

                call(item.name ?: "")
            }) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center


        ) {

            Image(
                painter = rememberImagePainter(item.subCategoryUrl),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Text11_body2(text = item.name, modifier = Modifier.align(Alignment.CenterHorizontally))


        }

    }


}


@Composable
fun MenuItemGrid(
    data: ItemsCollectionsResponse.SubItems, context: Context,
    viewModal: CartItemsViewModal= hiltViewModel(),
    passItem: (productdetail: ItemsCollectionsResponse.SubItems) -> Unit
) {


    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)

            .width(150.dp)
            .clickable {
                passItem(data)
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.orignal_price?.toFloat() ?: 0.0f) / (data.selling_price?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()

            Text(
                text = "${offpercentage}% off", color = sec20timer,
                modifier = Modifier.align(
                    Alignment.End
                ),
                fontSize = 10.sp,
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
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text10_h2(
                text = "${data.quantityInstructionController}", color = bodyTextColor,
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
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 10.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.productId ?: "",
                                data.productImage1 ?: "",
                                data.selling_price.toInt() ?: 0,
                                data.productName ?: "",
                                data.orignal_price ?: ""
                            )
                            viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            Utils.vibrator(context)

                        },

                    ) {
                    Text11_body2(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
                    )
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

