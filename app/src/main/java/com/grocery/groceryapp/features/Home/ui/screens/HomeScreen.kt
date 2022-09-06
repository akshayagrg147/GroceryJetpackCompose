package com.grocery.groceryapp.features.Home.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.grocery.groceryapp.features.Home.Navigator.gridItems

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.Utils.launchActivity
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.screens.OrderDetailScreen
import com.grocery.groceryapp.features.Home.ui.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun homescreen(context: Context) {
    var search = remember {
        mutableStateOf("")
    }
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
                            .padding(top = 5.dp, start = 20.dp).clickable {  context.startActivity(
                                Intent(
                                    context,
                                    ProductItemsActivity::class.java
                                )
                            ) }
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .height(260.dp)
                ) {
                    val list = listOf(
                        "A", "B", "C", "D"
                    )
                    items(list,
                        key = {
                            it }
                    ) { data ->
                        // Column(modifier = Modifier.padding(10.dp)) {
                        offerItems(context)
                        //}


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
                            .padding(top = 5.dp, start = 20.dp).clickable {  context.startActivity(
                                Intent(
                                    context,
                                    ProductItemsActivity::class.java
                                )
                            ) }
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .height(260.dp)
                ) {
                    val list = listOf("A", "B", "C", "D")
                    items(list) { data ->
                        // Column(modifier = Modifier.padding(10.dp)) {
                        SellingItems()
                        //}


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
                    Text14_400(
                        "See all", color = seallcolor, modifier = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, start = 20.dp)
                    )
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
                //recycler view items
                LazyRow(
                    modifier = Modifier.background(MaterialTheme.colors.background),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    val list = mutableListOf<String>("A", "B", "C", "D")
                    gridItems(
                        data = list,
                        columnCount = 2,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) { itemData ->
                        SellingItems()
                    }
                }


                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                    // .height(260.dp)
                ) {
                    val list = listOf("A", "B", "C", "D")
                    items(list) { data ->
                        // Column(modifier = Modifier.padding(10.dp)) {
                        SellingItems()
                        //}


                    }
                }


            }
        }

    }


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
fun offerItems(context: Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable {
            context.launchActivity<OrderDetailScreen>(){
                putExtra("name","Hey")
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.banana),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = "Orange Carror", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "7 pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "$4.99",
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
fun SellingItems() {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bell_peeper),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = "Orange Carror", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text14_400(
                text = "7 pcs,Price", color = headingColor,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text16_700(
                    text = "$4.99",
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