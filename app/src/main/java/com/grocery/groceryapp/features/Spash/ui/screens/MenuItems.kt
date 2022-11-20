package com.grocery.groceryapp.features.Spash.ui.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.google.gson.Gson
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.Utils
import com.grocery.groceryapp.data.modal.ItemsCollectionsResponse
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.Navigator.gridItems
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.AddressComponent

import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Spash.ui.viewmodel.CartItemsViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
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
fun cardviewAddtoCart(viewmodal: CartItemsViewModal, navController: NavHostController, context: Context, modifier: Modifier){
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = seallcolor,modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(5.dp)
            .clickable {
                navController.navigate(BottomNavItem.CartScreen.screen_route)
            }
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))


    ){
        Box(modifier = Modifier

        ){
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                var (l0,l1,l2) = createRefs()

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
                Column(Modifier.constrainAs(l1){
                    start.linkTo(l0.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) {
                    Text14_400(text = "${viewmodal.totalCountState.value} items", color = Color.White)
                    Text14_400(text = "₹ ${viewmodal.totalPriceState.value}",color = Color.White)


                }

                Text16_700(text = "view cart >",color = Color.White, modifier = Modifier.constrainAs(l2){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                })

            }

        }
    }}
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
fun menuitems(
    navController: NavHostController,
    context:Context,
    value: String,
    viewModal: CartItemsViewModal = hiltViewModel()
) {
    var selectedIndex = remember { mutableStateOf(1) }
    val passingvalue = value
    var productdetail= remember {
        mutableStateOf(ItemsCollectionsResponse.SubItems())
    }

    val scope = rememberCoroutineScope()
    val pager = rememberPagerState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        viewModal.getCartItem()


    val ls: MutableList<MainProducts> = ArrayList()
    if (passingvalue.equals("vegetables")) {
        ls.add(MainProducts("vegetable", R.drawable.fruitbasket, Purple700, "1001"))
        ls.add(MainProducts("Fruits", R.drawable.snacks, borderColor, "1002"))
        ls.add(MainProducts("Exotics", R.drawable.nonveg, disableColor, "1003"))
        ls.add(MainProducts("Orgain", R.drawable.oils, darkFadedColor, "1004"))
        ls.add(MainProducts("Recepies", R.drawable.oils, darkFadedColor, "1005"))
        ls.add(MainProducts("Flowers", R.drawable.oils, darkFadedColor, "1006"))
    } else if (passingvalue.equals("diary")) {
        ls.add(MainProducts("Milk", R.drawable.fruitbasket, Purple700, "2001"))
        ls.add(MainProducts("Breads", R.drawable.snacks, borderColor, "2002"))
        ls.add(MainProducts("Eggs", R.drawable.nonveg, disableColor, "2003"))
        ls.add(MainProducts("Poha", R.drawable.oils, darkFadedColor, "2004"))
        ls.add(MainProducts("Panner", R.drawable.oils, darkFadedColor, "2005"))
        ls.add(MainProducts("Curd", R.drawable.oils, darkFadedColor, "2006"))
    } else {
        ls.add(MainProducts("Drinks", R.drawable.fruitbasket, Purple700, "3001"))
        ls.add(MainProducts("Juics", R.drawable.snacks, borderColor, "3002"))
        ls.add(MainProducts("Coldpress", R.drawable.nonveg, disableColor, "3003"))
        ls.add(MainProducts("Drinks", R.drawable.oils, darkFadedColor, "3004"))
        ls.add(MainProducts("Water", R.drawable.oils, darkFadedColor, "3005"))
        ls.add(MainProducts("Soda", R.drawable.oils, darkFadedColor, "3006"))
    }

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
                            elevation = 4.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            HorizontalPager(count = 3, state = pager) { index ->
                                if (index == 0)
                                    Banner(pagerState = pager, productdetail.value.productImage1?: "")
                                if (index == 1)
                                    Banner(pagerState = pager, productdetail.value.productImage2 ?: "")
                                if (index == 2)
                                    Banner(pagerState = pager, productdetail.value.productImage3 ?: "")
                            }

                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text20_700(
                                text = productdetail.value.productName ?: "",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            IconButton(onClick = {

                            }) {

                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text16_700(text = "Product Detail", modifier = Modifier.fillMaxWidth(),)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text14_400(text = productdetail.value.productDescription?:"", modifier = Modifier.fillMaxWidth(),)





                    }



                }


            }

        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ){

        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize()) {
                Text20_700(text = "Sub cart menus", modifier = Modifier.align(Alignment.CenterHorizontally))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    ) {
                        selectedIndex.value = ls[0].productId?.toInt()?:0
                        viewModal.calllingItemsCollectionsId(ProductIdIdModal(ls[0].productId))
                        items(ls) { item ->

                            ItemEachRow(item, selectedIndex, viewModal) { productid, selectedvalue ->
                                viewModal.calllingItemsCollectionsId(ProductIdIdModal(productid))
                                selectedIndex.value = selectedvalue

                            }
                        }

                    }
                    LazyColumn(
                        modifier = Modifier.background(MaterialTheme.colors.background),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        if (viewModal.itemcollections1.value.statusCode == 200) {
                            gridItems(
                                data = viewModal.itemcollections1.value.list!!,
                                columnCount = 2,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 1.dp)
                            ) { itemData ->
                                MenuItemGrid(itemData,context, viewModal){passvalue->
                                   productdetail.value=passvalue
                                    scope.launch { modalBottomSheetState.show() }
                                }
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
            cardviewAddtoCart(viewModal,navController,context,modifier=Modifier.align(Alignment.BottomCenter))
        }

    }



}


@Composable
fun ItemEachRow(
    item: MainProducts,
    selectedIndex: MutableState<Int>,
    viewModal: CartItemsViewModal,
    call: (item: String, selectedvalue: Int) -> Unit
) {
    Card(elevation = 1.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.Black),
        backgroundColor = if (selectedIndex.value == item.productId?.toInt()) Color.LightGray else Color.White,
        modifier = Modifier

            .width(120.dp)
            .height(120.dp)

            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .padding(5.dp)
            .clickable {

                call(item.productId ?: "", item.productId?.toInt()!!)
            }) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center


        ) {

            Image(
                painter = rememberImagePainter(item.image),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Text13_700(text = item.name, modifier = Modifier.align(Alignment.CenterHorizontally))


        }

    }


}


@Composable
fun MenuItemGrid(
    data: ItemsCollectionsResponse.SubItems,context:Context,
    viewModal: CartItemsViewModal,
    passItem:(productdetail:ItemsCollectionsResponse.SubItems)->Unit
) {


    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding( horizontal = 4.dp)

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
            val offpercentage:String =(DecimalFormat("#.##").format(100.0- ((data.price?.toFloat() ?: 0.0f) /(data.actualPrice?.toFloat()?:0.0f))*100)).toString()

            Text(
                text = "${offpercentage}% off", color = titleColor, modifier = Modifier.align(
                    Alignment.End
                ),fontSize = 10.sp,
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
                Card( border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp)).padding(start=10.dp)

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
                    Text13_700(text = "ADD", availColor, modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp))
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

