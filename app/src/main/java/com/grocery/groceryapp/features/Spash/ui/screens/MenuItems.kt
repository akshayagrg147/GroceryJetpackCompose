package com.grocery.groceryapp.features.Spash.ui.screens

import android.util.Log
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.google.gson.Gson
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.ItemsCollectionsResponse
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.Navigator.gridItems
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.AddressComponent
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Spash.ui.viewmodel.CartItemsViewModal
import kotlinx.coroutines.launch

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
    value: String,
    viewModal: CartItemsViewModal = hiltViewModel()
) {
    val totalamount = remember { mutableStateOf(0) }
    var selectedIndex = remember { mutableStateOf(1) }
    val passingvalue = value
    var productdetail= remember {
        mutableStateOf(ItemsCollectionsResponse.SubItems())
    }

    val scope = rememberCoroutineScope()
    val pager = rememberPagerState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val ls: MutableList<MainProducts> = ArrayList()
    if (passingvalue.equals("vegetables")) {
        ls.add(MainProducts("Fresh vegetables", R.drawable.fruitbasket, Purple700, "1001"))
        ls.add(MainProducts("Fresh Fruits", R.drawable.snacks, borderColor, "1002"))
        ls.add(MainProducts("Exotics", R.drawable.nonveg, disableColor, "1003"))
        ls.add(MainProducts("Orgain", R.drawable.oils, darkFadedColor, "1004"))
        ls.add(MainProducts("Combo Recepies", R.drawable.oils, darkFadedColor, "1005"))
        ls.add(MainProducts("Flowers", R.drawable.oils, darkFadedColor, "1006"))
    } else if (passingvalue.equals("diary")) {
        ls.add(MainProducts("Milk", R.drawable.fruitbasket, Purple700, "2001"))
        ls.add(MainProducts("Breads", R.drawable.snacks, borderColor, "2002"))
        ls.add(MainProducts("Eggs", R.drawable.nonveg, disableColor, "2003"))
        ls.add(MainProducts("Poha", R.drawable.oils, darkFadedColor, "2004"))
        ls.add(MainProducts("Panner", R.drawable.oils, darkFadedColor, "2005"))
        ls.add(MainProducts("Curd", R.drawable.oils, darkFadedColor, "2006"))
    } else {
        ls.add(MainProducts("Soft Drinks", R.drawable.fruitbasket, Purple700, "3001"))
        ls.add(MainProducts("Fruit Juics", R.drawable.snacks, borderColor, "3002"))
        ls.add(MainProducts("Coldpress", R.drawable.nonveg, disableColor, "3003"))
        ls.add(MainProducts("Energy Drinks", R.drawable.oils, darkFadedColor, "3004"))
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


                        val itemcart: MutableState<Int> = remember { mutableStateOf(1) }
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
        Column(modifier = Modifier.fillMaxSize()) {
            Text20_700(text = "Sub cart menus", modifier = Modifier.align(Alignment.CenterHorizontally))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
                // .height(400.dp)
            ) {
                LazyColumn(
                    //  verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                    // .verticalScroll(state = scrollState)

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
                            SearchItems(itemData, viewModal, totalamount){passvalue->
                                Log.d("passingdataclass", passvalue.productDescription!!)
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

            .width(100.dp)
            .height(100.dp)
            .padding(start = 5.dp)
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .padding(5.dp)
            .clickable {

                call(item.productId ?: "", item.productId?.toInt()!!)
            }) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center


        ) {

            Image(
                painter = rememberImagePainter(item.image),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Text14_400(text = item.name, modifier = Modifier.align(Alignment.CenterHorizontally))


        }

    }


}


@Composable
fun SearchItems(
    data: ItemsCollectionsResponse.SubItems,
    viewModal: CartItemsViewModal,
    totalamount: MutableState<Int>,passItem:(productdetail:ItemsCollectionsResponse.SubItems)->Unit
) {
//    rememberCoroutineScope().launch {  viewModal.getItemBaseOnProductId(data.productId) }
//

    var cartcount = remember { mutableStateOf(0) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            val offpercentage= data.actualPrice?.toInt()?.minus((data.price?.toInt()?:0))
            Text(text ="${offpercentage}% off" , color = bodyTextColor, modifier = Modifier.align(Alignment.End))

            Image(
                painter = rememberImagePainter(data.productImage1),

                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {
                        passItem(data)
                    },


                )
            Text24_700(
                text = data.productName ?: "", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ){

                Text(text ="₹${data.actualPrice ?: "0.00"}" , color = bodyTextColor, modifier = Modifier.padding(start = 10.dp),style= TextStyle(textDecoration = TextDecoration.LineThrough))
                Text14_400(
                    text = "₹${data.price ?: "0.00"}", color = headingColor,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    CommonMathButton(icon = R.drawable.minus) {
                        cartcount.value -= 1
                         viewModal.deleteCartItems(data.productId)
                      //  each_item_count.value -= 1
                        //    totalamount.value = totalamount.value - (data.strProductPrice!!)

                    }
                    Text14_400(
                        text = "${viewModal.getItemBaseOnProductId(data.productId)}",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 10.dp),
                        color = Color.Black
                    )
                    CommonMathButton(icon = R.drawable.add) {
                        cartcount.value += 1
                      //  each_item_count.value += 1
                        viewModal.insertCartItem(data.productId?:"",data.productImage1?:"",data.price.toInt()?:0,data.productName?:"",data.actualPrice?:"")




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

