package com.grocery.mandixpress.features.splash.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.grocery.mandixpress.features.home.dashboardnavigation.DashBoardNavRoute


import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.features.home.Navigator.gridItems
import com.grocery.mandixpress.features.home.domain.modal.getProductCategory

import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.CartEvent
import com.grocery.mandixpress.features.home.ui.viewmodal.CartItemsViewModal
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
import com.grocery.mandixpress.roomdatabase.CartItems
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column (modifier = Modifier.width(150.dp)){
                Spacer(
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .background(brush = brush)
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(150.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),Arrangement.SpaceBetween) {
                    Spacer(
                        modifier = Modifier

                            .width(50.dp)
                            .height(20.dp)
                            .background(brush = brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(50.dp)
                            .padding(start = 20.dp)
                            .height(20.dp)
                            .background(brush = brush)
                    )
                }
            }
            Column (modifier = Modifier
                .padding(start = 10.dp)
                .width(150.dp)){
                Spacer(
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .background(brush = brush)
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(150.dp)
                        .height(20.dp)
                        .background(brush = brush)
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),Arrangement.SpaceBetween) {
                    Spacer(
                        modifier = Modifier

                            .width(50.dp)
                            .height(20.dp)
                            .background(brush = brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(50.dp)
                            .padding(start = 20.dp)
                            .height(20.dp)
                            .background(brush = brush)
                    )
                }
            }



        }

    }
}


@Composable
fun cardViewAddtoCart(

    navController: NavHostController,
    context: Context,
    modifier: Modifier, viewmodal: CartItemsViewModal = hiltViewModel(),
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
          else  if (viewmodal.totalPriceState.value < (viewmodal.getFreeDeliveryMinPrice().toDouble())) {
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
    context: Context,

    viewModal: CartItemsViewModal = hiltViewModel()
) {
    val allItems by viewModal._allItemsCollection.collectAsState()
    val bundle =
        navController.previousBackStackEntry?.savedStateHandle?.get<getProductCategory.ItemData>("data")
            ?: getProductCategory.ItemData()
    val selectedIndex = remember { mutableStateOf("") }
    val indexValue = remember { mutableStateOf(0) }
    val passingvalue = bundle
    val productdetail = remember {
        mutableStateOf(ItemsCollectionsResponse.SubItems())
    }

    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(3)
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    viewModal.getCartItem()
    ModalBottomSheetLayout(
        sheetElevation = 0.dp, sheetBackgroundColor = Color.Transparent, sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.Unspecified),
                contentAlignment = Alignment.Center,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.close_button), // Replace with your image resource
                        contentDescription = "Cross Button",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(bottom = 10.dp)
                            .clickable {
                                scope.launch {
                                    modalBottomSheetState.hide()
                                }

                            }, // Adjust the size as needed
                        contentScale = ContentScale.Fit
                    )

                },


                )
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (l1, l2) = createRefs()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
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
                                .padding(top = 20.dp)

                        ) {
                            HorizontalPager(state = pager) { index ->
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
                                color = headingColor,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            IconButton(onClick = {

                            }) {

                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text13_body1(
                            text = "Product Detail", modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text12_body1(
                            text = productdetail.value.productDescription ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
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
        val search = rememberSaveable {
            mutableStateOf("")
        }
        var newSellerAddedDialog by remember { mutableStateOf(false) }


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
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            Utils.vibrator(context)
                        }
                    }

                })
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                refreshing = false

            }
        }
        LaunchedEffect(key1 = Unit ){
            if(passingvalue.category.equals("sellerId")) {
                 selectedIndex . value = passingvalue.sellerCategoryData?.sellerCatergoryList?.get(0)?.name
                     ?:""
                viewModal.onEvent(
                    CartEvent.dataFetchBasedOnMainCategory(
                        ProductIdIdModal(
                            sellerId = passingvalue.subCategoryList?.get(
                                0
                            )?.name,
                            combineCategory = passingvalue.sellerCategoryData?.sellerCatergoryList?.get(
                                0
                            )?.sellerSubCatergoryList?.filterNotNull()
                                ?.joinToString(separator = "_")
                        )
                    )
                )
            }else{
                selectedIndex.value = passingvalue.subCategoryList?.get(0)?.name ?: ""
                viewModal.setProductId(ProductIdIdModal(passingvalue.subCategoryList?.get(0)?.name))

            }

        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
//
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.back_arrow),
//                        contentDescription = "back_arrow",
//                        modifier = Modifier
//                            .size(15.dp)
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Text14_h1(
//                        text = if (passingvalue.category.equals("sellerId")) "Seller id ${passingvalue.subCategoryList?.get(0)?.name.toString()}" else "Sub cart menus",
//                        color = headingColor,
//                        modifier = Modifier
//                            .wrapContentSize(Alignment.Center)// This will make the text take up the available space and be centered
//                    )
//                    Spacer(modifier = Modifier.weight(1f))
//                    Image(
//                        painter = painterResource(id = R.drawable.search),
//                        contentDescription = "search_icon",
//                        modifier = Modifier
//                            .size(20.dp)
//                    )
//                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(end = 0.dp, top = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black // Customize the color as needed
                        )
                    }
                    if (!passingvalue.category.equals("sellerId"))
                    Text14_h1(
                        text =   "Sub cart menus",
                        color = headingColor,
                        modifier = Modifier.padding(start=90.dp).align(Alignment.CenterVertically)
                            // This will make the text take up the available space and be centered
                    )
                    else

                    TextField(
                        value = search.value,
                        shape = RoundedCornerShape(8.dp),
                        onValueChange = {
                            search.value = it
                           viewModal.searchCharcterWiseItem(search.value)
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search,
                            keyboardType = KeyboardType.Text // Set keyboard type to Text
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .padding(start = 30.dp, end = 10.dp, top = 10.dp)
                         ,
                        placeholder = {
                            Text12_body1(
                                text = "Search in ${passingvalue.subCategoryList?.get(0)?.name.toString()} category",
                                color = bodyTextColor,
                            )
                        },


                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.LightGray,
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
                            IconButton(onClick = {
                              //  responseData.value = HomeAllProductsResponse()
                            }) {
                                Icon(
                                    Icons.Default.Search, contentDescription = "",
                                )
                            }
                        },
                        singleLine = true,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)

                ) {

                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    ) {


                        if(passingvalue.category.equals("sellerId")){

                            items(passingvalue.sellerCategoryData?.sellerCatergoryList ?: emptyList()) { item ->

                                ItemEachRowSellerCategory(
                                    item,
                                    selectedIndex,
                                ) { selectedvalue,categoryName ->
                                    viewModal.onEvent(CartEvent.dataFetchBasedOnMainCategory(ProductIdIdModal(sellerId = passingvalue.subCategoryList?.get(0)?.name,combineCategory=selectedvalue.filterNotNull().joinToString(separator = "_"))))
                                    selectedIndex.value = categoryName


                                }
                            }}
                        else{

                            items(passingvalue.subCategoryList ?: emptyList()) { item ->

                                ItemEachRow(
                                    item,
                                    selectedIndex
                                ) { selectedvalue ->
                                    viewModal.setProductId(ProductIdIdModal(selectedvalue))
                                    selectedIndex.value = selectedvalue

                                }
                            }
                        }


                    }

                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = refreshing),
                        onRefresh = { refreshing = true },
                    )
                    {   if(passingvalue.category.equals("sellerId"))
                    { if(   allItems.data?.list?.isEmpty()==true) {

                        Image(
                            painter = painterResource(id = R.drawable.noitems),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                       return@SwipeRefresh

                    }
                    }
                    else{
                        if(viewModal._itemsCollection.value.list?.isEmpty()==true) {

                            Image(
                                painter = painterResource(id = R.drawable.noitems),
                                contentDescription = null,
                                alignment = Alignment.Center,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            return@SwipeRefresh

                        }

                    }
                        LazyColumn(
                            modifier = Modifier.background(MaterialTheme.colors.background),
                            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            if (viewModal._itemsCollection.value.statusCode == 200) {
                                if (viewModal._itemsCollection.value.list?.isNotEmpty() == true)
                                    gridItems(
                                        data = viewModal._itemsCollection.value.list?: emptyList(),
                                        columnCount = 2,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(horizontal = 1.dp)
                                    ) { itemData ->
                                        MenuItemGrid(itemData, context, { passvalue ->
                                            productdetail.value = passvalue
                                            scope.launch { modalBottomSheetState.show() }
                                        },
                                            {cartItem,accessTable,boolean->
                                            viewModal.tempStoreAdminCartTable(accessTable,cartItem)

                                            newSellerAddedDialog =boolean

                                        })
                                    }

                            }
                            else if (allItems.data?.statusCode == 200){
                                if (allItems.data?.list?.isNotEmpty() == true)
                                    gridItems(
                                        data = allItems.data?.list?: emptyList(),
                                        columnCount = 2,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(horizontal = 1.dp)
                                    ) { itemData ->
                                        MenuItemGrid(itemData, context, { passvalue ->
                                            productdetail.value = passvalue
                                            scope.launch { modalBottomSheetState.show() }
                                        },
                                            {cartItem,accessTable,boolean->
                                                viewModal.tempStoreAdminCartTable(accessTable,cartItem)

                                                newSellerAddedDialog =boolean

                                            })
                                    }

                            }
                            else {
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
            if (viewModal.totalPriceState.value >= 1 &&(viewModal.getFreeDeliveryMinPrice()>0.00))
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

                call(item.name)
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
fun ItemEachRowSellerCategory(
    item: getProductCategory.ItemData.CategoryImage?,
    selectedIndex: MutableState<String>,

    call: (item: List<String?>,String) -> Unit
) {

    Card(elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),

        backgroundColor = if (selectedIndex.value == item?.name) Color.LightGray else Color.White,
        modifier = Modifier


            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .width(90.dp)
            .padding(5.dp)
            .clickable {

                call(item?.sellerSubCatergoryList ?: emptyList(), item?.name.toString())
            }) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center


        ) {

            Image(
                painter = rememberImagePainter(item?.image),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Text11_body2(text = item?.name.toString(), modifier = Modifier.align(Alignment.CenterHorizontally))


        }

    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuItemGrid(
    data: ItemsCollectionsResponse.SubItems, context: Context,

    passItem: (productdetail: ItemsCollectionsResponse.SubItems) -> Unit,
    showExtraChargesPopUp:(CartItems, AdminAccessTable, Boolean)->Unit,
    viewModal: CartItemsViewModal = hiltViewModel(),
) {
    Box(
        modifier = Modifier.fillMaxSize()

    ){
        if (  data.quantity.isNotEmpty() && data.quantity.toInt()==0)
        Text11_body2(
            text = "out of stock" ,
            redColor,
            modifier = Modifier
                .padding(end = 5.dp, top = 15.dp)
                .align(alignment = Alignment.Center)

        )

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        enabled = false,
        onClick = {
            if (  data.quantity.isNotEmpty() && data.quantity.toInt()!=0)
                passItem(data)
        },
        modifier = Modifier
            .padding(horizontal = 4.dp)

            .width(150.dp)
            .alpha(if (data.quantity.isNotEmpty() && data.quantity.toInt() == 0) 0.7f else 1.0f)


    ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)

            ) {
                val originalPrice = data.orignal_price.toFloat() ?: 0.0f
                val sellingPrice = data.selling_price.toFloat() ?: 0.0f

                val offPercentage = ((originalPrice - sellingPrice) / originalPrice) * 100
                val formattedPercentage = DecimalFormat("#.##").format(offPercentage)

            Text10_h2(
                text = "${formattedPercentage}% off", color = sec20timer,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(
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
                    text = data.productName, color = headingColor,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text10_h2(
                    text = data.quantityInstructionController, color = bodyTextColor,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row( horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 1.dp, bottom = 10.dp),
//
                ) {
Row {
    Text10_h2(
        text = "₹ ${data.selling_price}",
        color = headingColor,
        //  modifier= Modifier.weight(0.5F)
    )

    Text(
        text = "₹${data.orignal_price ?: "0.00"}",
        fontSize = 11.sp,
        color = bodyTextColor,
        modifier = Modifier.padding(start = 5.dp),
        style = TextStyle(textDecoration = TextDecoration.LineThrough)
    )
}


                        Card(
                            border = BorderStroke(1.dp,   titleColor),
                            modifier = Modifier

                                .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                                .padding(start = 5.dp)

                                .background(color = whiteColor)
                                .clickable {
                                    // response="called"
                                    if (data.quantity.isNotEmpty() && data.quantity.toInt() != 0) {
                                        viewModal.insertCartItem(
                                            data.productId ?: "",
                                            data.productImage1 ?: "",
                                            data.selling_price.toInt() ?: 0,
                                            data.productName ?: "",
                                            data.orignal_price ?: "",
                                            data.sellerId.toString()
                                        ) { adminData, cartItem ->
                                            showExtraChargesPopUp(cartItem, adminData, true)
                                        }
                                        viewModal.getCartItem()
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
                                },

                            ) {
                            Text11_body2(
                                text = if(data.quantity.isNotEmpty() && data.quantity.toInt()==0) "Notify" else "ADD",
                                availColor,
                                modifier = Modifier.padding(vertical = 3.dp, horizontal = 8.dp)
                            )
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

