package com.grocery.mandixpress.features.Home.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.common.Utils.Companion.vibrator
import com.grocery.mandixpress.data.modal.CategoryWiseDashboardResponse
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import com.grocery.mandixpress.features.Spash.ui.viewmodel.HomeEvent
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.simplegooglemapapp.ui.LocationUtils
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.LocationPermissionsAndSettingDialogs
import java.text.DecimalFormat


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun homescreen(
    navcontroller: NavHostController, sharedpreferenceCommon: sharedpreferenceCommon,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current.getActivity()
    var mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context!!)
    var requestLocationUpdate by remember { mutableStateOf(true) }
    val pager = rememberPagerState()
    val scroll: ScrollState = rememberScrollState(0)
    val placesClient: PlacesClient = Places.createClient(LocalContext.current)

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val address = remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit){
        viewModal.onEvent(
            HomeEvent.CategoryWiseEventFlow

        )
        viewModal.onEvent(
            HomeEvent.ExclusiveEventFlow

        )
        viewModal.onEvent(
            HomeEvent.BestSellingEventFlow
        )
        viewModal.onEvent(
            HomeEvent.ItemCategoryEventFlow
        )



    }
    var searchvisibility by remember { mutableStateOf(false) }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(400.dp)

            ) {

                // Curved background for the sheet
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                    ,
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.close_button), // Replace with your image resource
                        contentDescription = "Cross Button",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(bottom = 10.dp), // Adjust the size as needed
                        contentScale = ContentScale.Fit
                    )


                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )

                ) {
                    val predictions by viewModal.predictions.collectAsState()
                    Text16_h1(text = "Select Delivery Address", color = Purple500, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 20.dp, top = 20.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    // Text inside the curved background
                    CommonTextField(
                        trailingIcon= com.bumptech.glide.R.drawable.abc_ic_search_api_material,
                        iconColor=Color.LightGray,
                        text = address,
                        placeholder = stringResource(id = R.string.address),
                        modifier = Modifier.padding(start = 10.dp,end=10.dp)
                    ) {
                        viewModal.searchAddress( it,placesClient)
                    // namestate.value = it.length >= 3
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.homeicon), // Replace with your icon resource
                            contentDescription = "Location Icon",
                            modifier = Modifier
                                .size(24.dp)
                                .alignByBaseline(),
//                            colorFilter = ColorFilter.tint(seallcolor)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.clickable{
                            sharedpreferenceCommon.getCombinedAddress()
                            val intent = Intent(context, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }) {
                            Text12_h1(
                                text = "Use Your Current location",
                                color = seallcolor,

                            )
                            Text12_body1(text = sharedpreferenceCommon.getCombinedAddress())
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Image(
                            painter = painterResource(id = com.google.android.material.R.drawable.material_ic_keyboard_arrow_right_black_24dp), // Replace with your icon resource
                            contentDescription = "next",
                            modifier = Modifier
                                .size(24.dp)

                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()

                    LazyColumn {
                        items(predictions) {
                                prediction ->
                            Log.d("checkdata","${prediction.getFullText(null)}")

                            PredictionItem(prediction = prediction){
                                sharedpreferenceCommon.setSearchAddress(it)
                                val intent = Intent(context, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                            }
                        }



                    }
                }



            }
        }
    ){
        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {
            HeaderDeliveryTime(viewModal, navcontroller, scroll, lazyListState){
                coroutineScope.launch {
                    bottomSheetState.show()
                }

            }
            BodyDashboard(scroll, pager, viewModal, navcontroller, lazyListState, context) {

            }
            if (searchvisibility)
                SearchBar() {
                    navcontroller.navigate(DashBoardNavRoute.SearchProductItems.screen_route)
                }
            cardviewAddtoCart(
                viewModal,
                navcontroller,
                context!!,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

        }
    }

    if (sharedpreferenceCommon.getCombinedAddress() == "null")
        if (requestLocationUpdate)
            LocationPermissionsAndSettingDialogs(
                updateCurrentLocation = {
                    requestLocationUpdate = false
                    LocationUtils.requestLocationResultCallback(mFusedLocationClient) { locationResult ->
                        locationResult.lastLocation?.let { location ->
                            //   currentLocation=location
                            sharedpreferenceCommon.setCombineAddress("${location.latitude}, ${location.longitude}")
                        }

                    }
                }
            )






}

@Composable
fun NoAvaibiltyScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Not Available in your area",
            style = loginTypography.body1,
            color = greyLightColor,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BodyDashboard(
    scroll: ScrollState,
    pager: PagerState,
    viewModal: HomeAllProductsViewModal,
    navcontroller: NavHostController, lazyListState: LazyListState,
    context: Activity, availibilty: (Boolean) -> Unit
) {


   val padding by animateDpAsState(
        targetValue = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )
    val exlusiveResponse by viewModal.exclusive.collectAsState()
    val categoryWiseResponse by viewModal.categoryWiseResponse.collectAsState()
    val getProductCategory by viewModal.getProductCategory.collectAsState()
    val bestSelling by viewModal.bestSelling.collectAsState()
    Column(
        //  verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding)
            .verticalScroll(scroll)
    ) {
        TextField(
            value = "",
            shape = RoundedCornerShape(8.dp),
            enabled = false,
            onValueChange = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    navcontroller.navigate(DashBoardNavRoute.SearchProductItems.screen_route)
                }
                .padding(start = 10.dp, end = 10.dp),
            placeholder = {
                Text12_body1(
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

        //exclusive
        if(exlusiveResponse.isLoading)
            LazyRow(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                // .height(260.dp)
            ){
                repeat(5) {
                    item {
                        ShimmerAnimation()

                    }
                }
            }
        else if(exlusiveResponse.data != null)
        {
//            if(exlusiveResponse.data!!.message=="no"){
//                NoAvaibiltyScreen()
//                return
//            }
            //exclusive offers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), Arrangement.SpaceBetween
            ) {
                Text13_body1(
                    text = "Exclusive Offers", color = Color.Black,
                    modifier = Modifier
                        .padding(start = 10.dp),
                )
                Text11_body2(
                    "See all", color = seallcolor, modifier = Modifier
                        .padding(top = 5.dp, end = 20.dp)
                        .clickable {
                            if (exlusiveResponse.data?.statusCode == 200) {
                                val list1 = exlusiveResponse.data?.list
                                context.launchActivity<ListItemsActivity>() {
                                    putExtra(
                                        "parced",
                                        HomeAllProductsResponse(
                                            list1,
                                            "Exclusive Offers",
                                            200
                                        )
                                    )

                                }
                            }
                        }
                )
            }
            LazyRow(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                // .height(260.dp)
            ) {

                if (exlusiveResponse.data?.statusCode == 200) {
                    val list1 = exlusiveResponse.data?.list
                    if (list1?.isEmpty() == true) {
                        // availibilty(true)
                        return@LazyRow
                    }
                    items(list1!!) { data ->
                        ExclusiveOffers(data, context!!, navcontroller, viewModal)
                    }
                } else {

                }
            }

        }



        //Best selling
        if(bestSelling.isLoading)
            LazyRow(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                // .height(260.dp)
            ){
                repeat(5) {
                    item {
                        ShimmerAnimation()

                    }
                }
            }
        else  if(bestSelling.data != null){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), Arrangement.SpaceBetween
            ) {
                Text13_body1(
                    text = "Best Selling", color = Color.Black,
                    modifier = Modifier
                        .padding(start = 10.dp),
                )
                Text11_body2(
                    "See all", color = seallcolor, modifier = Modifier
                        .padding(top = 5.dp, end = 20.dp)
                        .clickable {
                            if (bestSelling.data?.statusCode == 200) {
                                val list1 = bestSelling.data?.list
                                context.launchActivity<ListItemsActivity>() {
                                    putExtra(
                                        "parced",
                                        HomeAllProductsResponse(list1, "Best Selling", 200)
                                    )
                                }
                            }

                        }
                )
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                // .height(260.dp)
            ) {

                if (bestSelling.data?.statusCode == 200) {
                    val list1 = bestSelling.data?.list
                    if (list1?.isNotEmpty() == true)
                        items(list1) { data ->
                            BestOffers(navcontroller, data, context, viewModal)
                        }
                }
            }
        }


//Cateory Wise
        if(getProductCategory.isLoading)
            LazyRow(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                // .height(260.dp)
            ){
                repeat(5) {
                    item {
                        ShimmerAnimation()

                    }
                }
            }
        else
            Column(modifier = Modifier.fillMaxSize()) {

                //groceries
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text13_body1(
                        text = "Groceries", color = Color.Black,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 10.dp),
                    )

                }
                val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 4)
                getProductCategory.data?.itemData=  getProductCategory.data?.itemData?.filter { it.subCategoryList?.isNotEmpty()==true }
                FlowRow(
                    mainAxisSize = SizeMode.Expand,

                    ) {
                    if(getProductCategory.data?.itemData?.isNotEmpty()==true)
                        for (i in 0 until getProductCategory.data?.itemData?.size!!) {
                            GroceriesItems(
                                whiteColor,
                                getProductCategory.data?.itemData!![i].imageUrl!!,
                                getProductCategory.data?.itemData!![i],
                                navcontroller,
                                itemSize
                            )
                        }
                }
            }



        //last list items
        if(getProductCategory.isLoading)
            LazyRow(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                // .height(260.dp)
            ){
                repeat(5) {
                    item {
                        ShimmerAnimation()

                    }
                }
            }
        else
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                repeat(categoryWiseResponse.data?.list?.size ?: 0) {

                    Card(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(2.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 10.dp)

                    ) {
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp), Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null,
                                        tint = Color.LightGray,
                                    )
                                    Column() {
                                        Text14_h1(
                                            text = categoryWiseResponse.data?.list?.get(
                                                it
                                            )?.categoryTitle ?: "", color = headingColor,
                                            modifier = Modifier

                                        )
                                        Text13_body1(
                                            text = categoryWiseResponse.data?.list?.get(
                                                it
                                            )?.category ?: "", color = greyLightColor,
                                            modifier = Modifier
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.clickable {
                                         viewModal.setcategory(
                                            categoryWiseResponse.data?.list?.get(
                                                it
                                            )?.category ?: ""
                                        )
                                        navcontroller.navigate(DashBoardNavRoute.DashBoardCategoryWisePagination.screen_route)
                                    }
                                )

                            }

                            Spacer(modifier = Modifier.height(5.dp))
                            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2.1f)
                            FlowRow(
                                mainAxisSize = SizeMode.Expand,
                                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
                            ) {
                                for (i in 0 until categoryWiseResponse.data?.list?.get(
                                    it
                                )!!.ls?.size!!) {
                                    CateoryWiseItems(
                                        categoryWiseResponse.data?.list?.get(it)!!.ls?.get(
                                            i
                                        )!!,
                                        context, navcontroller, viewModal, itemSize
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }


                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }

    }

}

@Composable
fun SearchBar(
    searchclick: () -> Unit
) {
    Log.d("repetationcall", "call20")
    TextField(
        value = "",
        shape = RoundedCornerShape(8.dp),
        enabled = false,
        onValueChange = {
        },
        modifier = Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(24.dp))
            .clickable {
                searchclick()
            }
            .padding(start = 0.dp, end = 2.dp),
        placeholder = {
            Text10_h2(
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


        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Search, contentDescription = "",
                )
            }
        },
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeaderDeliveryTime(
    viewModal: HomeAllProductsViewModal,
    navcontroller: NavHostController,
    scroll: ScrollState, lazyListState: LazyListState, call:()->Unit
) {

    Column(modifier = Modifier.height(height = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT)) {
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), Arrangement.SpaceBetween
        ) {
            Column {
                Text14_h2(text = "Delivery in 10 minutes", color = Purple500)
                Spacer(modifier = Modifier.height(4.dp))
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.homeicon),
                        contentDescription = ""
                    )
                    Text(
                        text = viewModal.gettingAddres(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = greyLightColor,

                        textAlign = TextAlign.Start,

                        maxLines = 1,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(200.dp)
                            .clickable(
                                indication = null,
                                interactionSource = MutableInteractionSource()
                            ) {

                                call()
                            }
                    )
                }

            }
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "ProfileImage",
                alignment = Alignment.Center,

                modifier = Modifier

                    .padding(vertical = 10.dp)
                    .width(45.dp)
                    .height(45.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        navcontroller.navigate(DashBoardNavRoute.Profile.screen_route)
                    }

            )
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
fun ExclusiveOffers(
    data: HomeAllProductsResponse.HomeResponse,
    context: Context,
    navcontroller: NavHostController,
    viewModal: HomeAllProductsViewModal
) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(150.dp)
            .clickable {
                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!}"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.selling_price?.toFloat() ?: 0.0f) / (data.orignal_price?.toFloat()
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
                painter = rememberImagePainter(data.productImage2),
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
                Text(
                    text = "₹${data.orignal_price ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)
                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.ProductId ?: "",
                                data.productImage1 ?: "",
                                data.selling_price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.selling_price ?: ""
                            )
                            //    viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            vibrator(context)

                        },

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


@Composable
fun CateoryWiseItems(
    data: CategoryWiseDashboardResponse.CategoryItem.ItemData,
    context: Context,
    navcontroller: NavHostController, viewModal: HomeAllProductsViewModal, itemSize: Dp
) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp, bottom = 1.dp)
            .width(itemSize)
            .clickable {
                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.productId!!}"))
            }

    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.selling_price?.toFloat() ?: 0.0f) / (data.orignalPrice?.toFloat()
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
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text10_h2(
                    text = "₹ ${data.selling_price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.orignalPrice ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.productId ?: "",
                                data.productImage1 ?: "",
                                data.selling_price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.orignalPrice ?: ""
                            )
                            //    viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            Utils.vibrator(context)


                        },

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

@Composable
fun BestOffers(
    navcontroller: NavHostController,
    data: HomeAllProductsResponse.HomeResponse,
    context: Context,
    viewModal: HomeAllProductsViewModal
) {
    Log.d("repetationcall", "call15")
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(150.dp)
            .clickable {
                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!}"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.selling_price?.toFloat() ?: 0.0f) / (data.orignal_price?.toFloat()
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
                Text(
                    text = "₹${data.orignal_price ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            // response="called"
                            viewModal.insertCartItem(
                                data.ProductId ?: "",
                                data.productImage1 ?: "",
                                data.selling_price?.toInt() ?: 0,
                                data.productName ?: "",
                                data.orignal_price ?: ""
                            )
                            //     viewModal.getCartItem()
                            Toast
                                .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                                .show()

                            vibrator(context)

                        },

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

@Composable
fun GroceriesItems(
    color: Color,
    drawable: String,
    item: getProductCategory.ItemData,
    navController: NavHostController,
    itemSize: Dp
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),

        backgroundColor = color, modifier = Modifier
            .size(itemSize)
            .padding(5.dp)
            .clickable {
                val data: String = Gson().toJson(item)
                navController.currentBackStackEntry?.savedStateHandle?.set("data", item)
                navController.navigate(DashBoardNavRoute.MenuItems.screen_route)


            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {



            Image(
                painter = rememberImagePainter(drawable),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .padding(top = 2.dp)
            )
            item.category?.let {
                Text10_h2(
                    text = it, color = faqColor,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),


                )
            }



        }
    }
}

//shimming
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


@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}

@Composable
fun cardviewAddtoCart(
    viewmodal: HomeAllProductsViewModal,
    navController: NavHostController,
    context: Context,
    modifier: Modifier
) {
    Log.d("repetationcall", "call11")
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
                        text = "${viewmodal.getitemcountState.value.toString()} items",
                        color = Color.White
                    )
                    Text12_body1(
                        text = "₹ ${viewmodal.getitempriceState.value.toString()}",
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

val TOP_BAR_HEIGHT = 76.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0