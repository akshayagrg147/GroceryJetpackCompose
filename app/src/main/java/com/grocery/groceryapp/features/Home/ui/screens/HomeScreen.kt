package com.grocery.groceryapp.features.Home.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.Utils.launchActivity
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.screens.OrderDetailScreen
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal


@OptIn(ExperimentalPagerApi::class)
@Composable
fun homescreen(context: Context,  viewModal: HomeAllProductsViewModal = hiltViewModel()) {
    var search = remember {
        mutableStateOf("")
    }
    //calling api

    viewModal.callingBestSelling()
    viewModal.callingExcusiveProducts()
    viewModal.callingHomeAllProducts()
    var res= viewModal.exclusiveProductsResponse1.value
    var all= viewModal.homeAllProductsResponse1.value
    var best= viewModal.bestsellingProductsResponse1.value
        val pager = rememberPagerState()
    LazyColumn(
        //  verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
        // .verticalScroll(state = scrollState)

    ) {
        item {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ornge_carrot),
                    contentDescription = "Carrot Icon",
                    alignment = Alignment.Center,


                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp),
                        tint = redColor
                    )
                    Text16_700(
                        text = "kaithal,Haryana",
                        color = headingColor,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = search.value,
                    shape = RoundedCornerShape(8.dp),


                    onValueChange = {
                        search.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
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

                    trailingIcon = {
                        if (search.value != "") {
                            IconButton(onClick = {
                                search.value = ""
                            }) {
                                Icon(
                                    Icons.Default.Close, contentDescription = "",
                                )
                            }
                        }
                    },
                    leadingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.Search, contentDescription = "",
                            )
                        }
                    },
                    singleLine = true,
                )

                HorizontalPager(count = 3, state = pager) { index ->
                    Banner(pagerState = pager)
                }

//exclusive offers
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text16_700(
                        text = "Exclusive Offers", color = Color.Black,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 10.dp),
                    )
                    Text14_400(
                        "See all", color = seallcolor, modifier = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, start = 20.dp)
                            .clickable {
                                if (res.statusCode == 200) {
                                    val list1 = res.list
                                    context.launchActivity<ListItemsActivity>() {
                                        putExtra("parced", HomeAllProductsResponse(list1, "Exclusive Offers", 200))

                                    }
                                    }
                            }
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .height(260.dp)
                ) {

                    if(res.statusCode==200){
                        val list1=res.list
                        Log.d("listofdata", "homescreen: $list1")
                        items(list1!!) { data ->
                            ExclusiveOffers(data,context)
                        }


                    }




                }
//Best selling
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text16_700(
                        text = "Best Selling", color = Color.Black,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 10.dp),
                    )
                    Text14_400(
                        "See all", color = seallcolor, modifier = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, start = 20.dp)
                            .clickable {
                                if (best.statusCode == 200) {
                                    val list1 = best.list
                                    context.launchActivity<ListItemsActivity>() {
                                        putExtra("parced", HomeAllProductsResponse(list1, "Best Selling", 200))

                                    }
                                }
                            }
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .height(260.dp)
                ) {

                    if(best.statusCode==200){
                        val list1=best.list
                        items(list1!!) { data ->
                            // Column(modifier = Modifier.padding(10.dp)) {
                            BestOffers(data,context)
                            //}


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
//                    Text14_400(
//                        "See all", color = seallcolor, modifier = Modifier
//                            .weight(1f)
//                            .padding(top = 5.dp, start = 20.dp).clickable {
//                                if (all.statusCode == 200) {
//                                    val list1 = all.list
//                                    context.launchActivity<ListItemsActivity>() {
//                                        putExtra("parced", HomeAllProductsResponse(list1, "", 200))
//
//                                    }
//                                }
//                            }
//                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .height(260.dp)
                ) {
                    val ls:MutableList<MainProducts> = ArrayList()
                    ls.add(MainProducts("Pulses",R.drawable.bolt,Purple700))
                    ls.add(MainProducts("Rice",R.drawable.bolt,borderColor))
                    ls.add(MainProducts("Flour",R.drawable.bolt,disableColor))
                    ls.add(MainProducts("Oils",R.drawable.oils,darkFadedColor))

                    items(ls) { data ->
                        // Column(modifier = Modifier.padding(10.dp)) {
                        GroceriesItems(data.color,data.image,data.name)
                        //}


                    }
                }
//                LazyColumn(
//                    modifier = Modifier.background(MaterialTheme.colors.background),
//                    contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//
//                    val ls: MutableList<MainProducts> = ArrayList()
//                    ls.add(MainProducts("Fruit Basket", R.drawable.fruitbasket, Purple700))
//                    ls.add(MainProducts("Snacks", R.drawable.snacks, borderColor))
//                    ls.add(MainProducts("Non Veg", R.drawable.nonveg, disableColor))
//                    ls.add(MainProducts("Oils", R.drawable.oils, darkFadedColor))
//
//
//                    gridItems(
//                        data = ls,
//                        columnCount = 2,
//                        horizontalArrangement = Arrangement.spacedBy(8.dp),
//                        modifier = Modifier.padding(horizontal = 16.dp)
//                    ) { itemData ->
//                        SearchItems(itemData)
//                    }
//                }



                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                    // .height(260.dp)
                ) {

                    if(all.statusCode==200){
                        items(all.list!!) { data ->
                            // Column(modifier = Modifier.padding(10.dp)) {
                            AllItems(data,context)
                            //}


                        }
                    }


                }


            }
        }


    }


}
@Composable
fun LazyVerticalGridDemo(){
    val list = (1..10).map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(list.size) { index ->
                Card(
                    backgroundColor = Color.Red,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                ) {
                    Text(
                        text = list[index],
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
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
fun ExclusiveOffers(data: HomeAllProductsResponse.HomeResponse, context: Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable {
            context.launchActivity<OrderDetailScreen>(){
                putExtra("productId",data.ProductId)
                putExtra("ProductCategory","exclusive")
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "${data.quantity} pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )

                Icon(
                    painter = rememberAsyncImagePainter(data.productImage1),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 100.dp),
                    tint = redColor
                )
            }


        }
    }
}
@Composable
fun AllItems(data: HomeAllProductsResponse.HomeResponse, context: Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable {
            context.launchActivity<OrderDetailScreen>(){
                putExtra("productId",data.ProductId)
                putExtra("ProductCategory","all")
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(

                painter = rememberAsyncImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)



            )
            Text24_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "${data.quantity} pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )

                Icon(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 100.dp),
                    tint = redColor
                )
            }


        }
    }
}

@Composable
fun BestOffers(data: HomeAllProductsResponse.HomeResponse, context: Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable {
            context.launchActivity<OrderDetailScreen>(){
                putExtra("productId",data.ProductId)
                putExtra("ProductCategory","Best")
            }
        }

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "${data.quantity} pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text =  "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )

                Icon(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 100.dp),
                    tint = redColor
                )
            }


        }
    }
}

@Composable
fun GroceriesItems(color: Color,drawable: Int,item:String) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = color,modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        ) {
        Box(

        ) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(248.dp)
                    .height(105.dp)
            )
            Text14_400(
                text = item, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .align(Alignment.CenterEnd)
            )



        }
    }
}