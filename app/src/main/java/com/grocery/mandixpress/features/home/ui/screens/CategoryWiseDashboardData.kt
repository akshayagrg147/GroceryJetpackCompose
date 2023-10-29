package com.grocery.mandixpress.features.home.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.AddToCartCardView
import com.grocery.mandixpress.common.LoadingBar
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.BannerImageResponse
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat


@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun CategoryWiseDashboardAllData(

    context: Activity,
    navController: NavHostController,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val bundle =
        navController.previousBackStackEntry?.savedStateHandle?.get<PassParceableBanner>("data")
            ?: PassParceableBanner()

    val itemBasedCategory by viewModal._bannerCategoryResponse.collectAsState()
    LaunchedEffect(key1 = Unit) {
        if (bundle.second?.subCategoryList?.isNotEmpty() == true)
            viewModal.onEvent(
                HomeEvent.BannerCategoryEventFlow(
                    bundle.second.subCategoryList[0].name
                )
            )
    }
    val productdetail = remember {
        mutableStateOf(ItemsCollectionsResponse.SubItems())
    }
    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(3)
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    ModalBottomSheetLayout(
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
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
                val (l1, _) = createRefs()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .constrainAs(l1) {
                        top.linkTo(parent.top)

                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color =
                                Color.White, shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                    ) {
                        Card(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)

                        ) {
                            HorizontalPager(state = pager) { index ->
                                if (index == 0)
                                    com.grocery.mandixpress.features.splash.ui.screens.Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage1
                                    )
                                if (index == 1)
                                    com.grocery.mandixpress.features.splash.ui.screens.Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage2
                                    )
                                if (index == 2)
                                    com.grocery.mandixpress.features.splash.ui.screens.Banner(
                                        pagerState = pager,
                                        productdetail.value.productImage3
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
                                text = productdetail.value.productName,
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
                            text = productdetail.value.productDescription,
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
        var refreshing by remember { mutableStateOf(false) }
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(3000)
                refreshing = false
            }
        }
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = { refreshing = true },
        )
        {
            if (bundle.second?.bannercategory1?.isNotEmpty() == true) {
                if (bundle.index == 0)
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            CommonHeader(
                                text = viewModal.getcategory().split("__")[0],
                                color = headingColor
                            ) {
                                navController.popBackStack()
                            }
                            ScrollingImageRow(
                                size = 70.dp,
                                bundle.second.subCategoryList,
                                value = 0
                            ) {
                                viewModal.onEvent(HomeEvent.BannerCategoryEventFlow(it))
                            }
                            Image(
                                painter = rememberImagePainter(bundle.second.imageUrl1),
                                null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )


                            if (itemBasedCategory.data?.statusCode == 200) {
                                BodyDashboard(
                                    itemBasedCategory.data!!.list,
                                    context,
                                    viewModal,
                                    productdetail,
                                    modalBottomSheetState
                                )
                            } else {
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

                        }
                        if (viewModal.getitemcountState.value >= 1)
                            AddToCartCardView(
                                viewModal,
                                navController,
                                context,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )


                    }
                else {
                    val list = viewModal.allresponse.collectAsLazyPagingItems()
                    if (list.loadState.refresh is LoadState.Loading) {
                        LoadingBar()
                    }
                    if (list.loadState.refresh is LoadState.NotLoading) {
                        Box(modifier = Modifier.fillMaxSize())
                        {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                CommonHeader(
                                    text = viewModal.getcategory().split("__")[0],
                                    color = headingColor
                                ) {
                                    navController.popBackStack()
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                    // .height(400.dp)
                                ) {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2),
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        // on below line we are displaying our
                                        // items upto the size of the list.
                                        items(list.snapshot().items.size) {
                                            ProductWiseRow(
                                                list.peek(it)!!,
                                                productdetail,
                                                modalBottomSheetState
                                            ) { data ->
                                                // response="called"
                                                viewModal.insertCartItem(
                                                    data.ProductId ?: "",
                                                    data.productImage1 ?: "",
                                                    data.selling_price?.toInt() ?: 0,
                                                    data.productName ?: "",
                                                    data.orignal_price ?: ""
                                                )
                                                viewModal.getItemCount()
                                                viewModal.getItemPrice()
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
                                }


                            }
                            if (viewModal.getitemcountState.value >= 1)
                                AddToCartCardView(
                                    viewModal,
                                    navController,
                                    context,
                                    modifier = Modifier.align(Alignment.BottomCenter)

                                )
                        }
                    }
                    if (list.loadState.refresh is LoadState.Error) {
                        context.showMsg("error occured")
                    }

                    if (list.loadState.append is LoadState.Loading) {
                        LoadingBar()
                    }
                    if (list.loadState.append is LoadState.Error) {
                        context.showMsg("error occured")
                    }
                    if (list.loadState.prepend is LoadState.Loading) {
                        LoadingBar()
                    }
                    if (list.loadState.prepend is LoadState.Error) {
                        context.showMsg("error occured")
                    }
                }


            } else {
                val list = viewModal.allresponse.collectAsLazyPagingItems()
                if (list.loadState.refresh is LoadState.Loading) {
                    LoadingBar()
                }
                if (list.loadState.refresh is LoadState.NotLoading) {
                    Box(modifier = Modifier.fillMaxSize())
                    {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            CommonHeader(
                                text = viewModal.getcategory().split("__")[0],
                                color = headingColor
                            ) {
                                navController.popBackStack()
                            }

                            Column(
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                // .height(400.dp)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    // on below line we are displaying our
                                    // items upto the size of the list.
                                    items(list.snapshot().items.size) {
                                        ProductWiseRow(
                                            list.peek(it)!!,
                                            productdetail,
                                            modalBottomSheetState
                                        ) { data ->
                                            // response="called"
                                            viewModal.insertCartItem(
                                                data.ProductId ?: "",
                                                data.productImage1 ?: "",
                                                data.selling_price?.toInt() ?: 0,
                                                data.productName ?: "",
                                                data.orignal_price ?: ""
                                            )
                                            viewModal.getItemCount()
                                            viewModal.getItemPrice()
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Added to cart",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()

                                            Utils.vibrator(context)
                                        }
//                    if(it==list.itemCount-1)
//                        Spacer(modifier = Modifier.height(80.dp))
                                    }
                                }
                            }


                        }
                        if (viewModal.getitemcountState.value >= 1)
                            AddToCartCardView(
                                viewModal,
                                navController,
                                context,
                                modifier = Modifier.align(Alignment.BottomCenter)

                            )
                    }
                }
                if (list.loadState.refresh is LoadState.Error) {
                    context.showMsg("error occured")
                }

                if (list.loadState.append is LoadState.Loading) {
                    LoadingBar()
                }
                if (list.loadState.append is LoadState.Error) {
                    context.showMsg("error occured")
                }
                if (list.loadState.prepend is LoadState.Loading) {
                    LoadingBar()
                }
                if (list.loadState.prepend is LoadState.Error) {
                    context.showMsg("error occured")
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BodyDashboard(
    list: List<ItemsCollectionsResponse.SubItems>?, context: Context,
    viewModal: HomeAllProductsViewModal,
    productdetail: MutableState<ItemsCollectionsResponse.SubItems>,
    modalBottomSheetState: ModalBottomSheetState
) {
    if (list?.isNotEmpty() == true) {

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
            // .height(400.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(10.dp)
            ) {
                // on below line we are displaying our
                // items upto the size of the list.
                items(list) { item ->

                    ProductWiseRowBanner(item, productdetail, modalBottomSheetState) { data ->

                        viewModal.insertCartItem(
                            data.productId,
                            data.productImage1,
                            data.selling_price.toInt(),
                            data.productName,
                            data.orignal_price
                        )
                        viewModal.getItemCount()
                        viewModal.getItemPrice()
                        context.showMsg("Added to cart")
                        Utils.vibrator(context)
                    }
//                    if(it==list.itemCount-1)
//                        Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    } else {
        NoItemound()
    }


}

@Composable
fun NoItemound() {
    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }
        Text12_h1(
            text = "No Items Available", color = headingColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )


    }

}

@Composable
fun ScrollingImageRow(
    size: Dp = 100.dp, ls:
    List<BannerImageResponse.ItemData.SubCategory>?, value: Int = -1, call: (String) -> Unit
) {
    var selectedItemIndex by remember { mutableStateOf(value) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(ls!!) { index, item ->
            val isSelected = selectedItemIndex == index
            Column() {
                Image(
                    painter = rememberImagePainter(item.subCategoryUrl),
                    contentDescription = null, contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(size)

                        .background(if (isSelected) greyLightColor else whiteColor)
                        .clip(CircleShape)
                        .let { if (isSelected) it.clip(CircleShape) else it }

                        .clickable {
                            selectedItemIndex = index
                            call(item.name)
                        }
                )
                Text10_h2(
                    text = item.name, color = headingColor,
                    modifier = Modifier

                        .align(Alignment.CenterHorizontally)
                )
            }


        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductWiseRow(
    data: HomeAllProductsResponse.HomeResponse,
    productdetail: MutableState<ItemsCollectionsResponse.SubItems>,
    modalBottomSheetState: ModalBottomSheetState,
    call: (HomeAllProductsResponse.HomeResponse) -> Unit
) {
    val scope = rememberCoroutineScope()


    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)

            .width(150.dp)
            .clickable {
                productdetail.value = ItemsCollectionsResponse.SubItems(
                    orignal_price = data.orignal_price ?: "",
                    item_category_name = "",
                    selling_price = data.selling_price ?: "",
                    productDescription = data.productDescription ?: "",
                    productId = data.ProductId ?: "",
                    productImage1 = data.productImage1 ?: "",
                    productImage2 = data.productImage2 ?: "",
                    productImage3 = data.productImage3 ?: "",
                    productName = data.productName ?: "",
                    quantity = data.quantity ?: "",
                    quantityInstructionController = data.quantityInstructionController ?: "",
                )
                scope.launch { modalBottomSheetState.show() }
//                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
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
            Text10_h2(
                text = "${offpercentage}% off", color = sec20timer, modifier = Modifier.align(
                    Alignment.End
                )
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
                            call(data)


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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductWiseRowBanner(
    data: ItemsCollectionsResponse.SubItems,
    productdetail: MutableState<ItemsCollectionsResponse.SubItems>,
    modalBottomSheetState: ModalBottomSheetState,
    call: (ItemsCollectionsResponse.SubItems) -> Unit
) {

    val scope = rememberCoroutineScope()

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)

            .width(150.dp)
            .clickable {
                productdetail.value = data
                scope.launch { modalBottomSheetState.show() }
//                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - (data.selling_price.toFloat() / data.orignal_price.toFloat()) * 100
            )).toString()
            Text10_h2(
                text = "${offpercentage}% off", color = sec20timer, modifier = Modifier.align(
                    Alignment.End
                )
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
            Row(
                modifier = Modifier.padding(start = 10.dp),
//                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text10_h2(
                    "₹ ${data.selling_price}",
                    headingColor,
                    /* modifier= Modifier.weight(0.5F) */
                )
                Text(
                    text = "₹${data.orignal_price}",
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
                            call(data)


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