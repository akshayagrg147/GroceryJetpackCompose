package com.grocery.mandixpress.features.splash.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.AddToCartCardView
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.home.domain.modal.FilterOptions
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat

private val headerHeight = 160.dp
private val toolbarHeight = 16.dp


private val paddingMedium = 16.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 72.dp
private var sortType by mutableStateOf("")

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItems(
    navController: NavHostController,
    context: Context,
    viewModal: HomeAllProductsViewModal,

    ) {

    viewModal.setList(viewModal.myParcelableData!!)

    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var filterclicked by remember { mutableStateOf(false) }
    var productClicked by remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(1) }
    val productdetail: MutableState<HomeAllProductsResponse.HomeResponse> = remember {
        mutableStateOf(HomeAllProductsResponse.HomeResponse())
    }
    ModalBottomSheetLayout(

        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {

             if (filterclicked)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .background(Color.White)
                        .padding(top = 10.dp), Arrangement.SpaceEvenly
                ) {
                    val ls: MutableList<FilterOptions> = ArrayList()
                    ls.add(FilterOptions("By Budget", "1"))
                     LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                    ) {
                        items(ls) { item ->

                            ItemEachRow(item, selectedIndex) { _, selectedvalue ->
                                selectedIndex.value = selectedvalue

                            }
                        }
                    }

                    Divider(
                        color = Color.Blue, modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 5.dp)
                            .width(1.dp)
                    )

                    Column(modifier = Modifier.fillMaxSize()) {


                        val hundredone = remember { mutableStateOf(false) }
                        val twohundredone = remember { mutableStateOf(false) }
                        val threehundredone = remember { mutableStateOf(false) }
                        val fivehundredone = remember { mutableStateOf(false) }
                        Text12_h1(
                            text = "FILTER & SORT",   color= headingColor,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text12_body1(
                                text = "Choose a range below",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Row {
                                Checkbox(

                                    checked = hundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { hundredone.value = it },
                                )
                                Text10_h2(
                                    text = "Rs. 99 and below",
                                    modifier = Modifier.padding(16.dp),

                                )
                            }

                            Row {
                                Checkbox(

                                    checked = twohundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { twohundredone.value = it },
                                )
                                Text10_h2(
                                    text = "Rs. 100 and 299",
                                    modifier = Modifier.padding(16.dp),

                                )
                            }

                            Row {
                                Checkbox(

                                    checked = threehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { threehundredone.value = it },
                                )
                                Text10_h2(
                                    text = "Rs. 300 and 399",
                                    modifier = Modifier.padding(16.dp),

                                )
                            }
                            Row {
                                Checkbox(

                                    checked = fivehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { fivehundredone.value = it },
                                )
                                Text10_h2(
                                    text = "Rs. 400 and above",
                                    modifier = Modifier.padding(16.dp),

                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        CommonButton(
                            text = "Apply",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)

                        )
                        {
                            scope.launch { modalBottomSheetState.hide() }
                            val filterlist: List<HomeAllProductsResponse.HomeResponse>? = viewModal.globalmutablelist1.value.list
                            var filterlist1: List<HomeAllProductsResponse.HomeResponse>? = null
                            if (hundredone.value)
                                filterlist1 = filterlist?.filter {
                                    it.selling_price?.toInt()!! <= 99
                                }
                             if (twohundredone.value)
                                 filterlist1 = filterlist?.filter {
                                    it.selling_price?.toInt()!! in 100..299
                                }
                             if (threehundredone.value)
                                 filterlist1 =filterlist?.filter {
                                    it.selling_price?.toInt()!! in 300..499
                                }
                            if (fivehundredone.value)
                                filterlist1 =filterlist?.filter {
                                    it.selling_price?.toInt()!! >=500
                                }
                            else{
                               //  filterlist1=filterlist
                            }
                            viewModal.setFilterList(filterlist1)

                            viewModal.setvalue("filter")



                        }
                    }
                }
            else if(productClicked){
                showItemDescription(productdetail, modalBottomSheetState)
            }
            else
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text13_body1(text = "Asending(A-Z)", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp)
                        .clickable {
                            viewModal.setvalue("asc")


//                       lss?.sortedBy { it.productName?.lowercase() }
//                        var list = arrayListOf<HomeAllProductsResponse.HomeResponse>()
//                        lss?.forEach {
//                            list.add(it)
//                        }
//                        var dou=list.sortBy { it.price?.toInt() }
                            sortType = "asc"

                            scope.launch { modalBottomSheetState.hide() }
//                        srt(sortType,lss!!)

                        })
                    Text13_body1(text = "Desending(Z-A)", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp)
                        .clickable {
                            viewModal.setvalue("dsc")
                            scope.launch { modalBottomSheetState.hide() }
                        })
                    Text13_body1(text = "High to low", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp)
                        .clickable {
                            viewModal.setvalue("hightolow")
                            scope.launch { modalBottomSheetState.hide() }
                        })
                    Text13_body1(text = "Low to high", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 5.dp)
                        .clickable {
                            viewModal.setvalue("lowtohigh")
                            scope.launch { modalBottomSheetState.hide() }
                        })

                }

        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (l3) = createRefs()
                val createguidlinefromtop = createGuidelineFromBottom(70.dp)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    val scroll: ScrollState = rememberScrollState(0)

                    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
                    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Header(scroll, headerHeightPx)
                        Body(scroll, viewModal, context) {

                            productClicked = true
                            filterclicked = false
                            productdetail.value = it
                            scope.launch { modalBottomSheetState.show() }
                        }
                        Toolbar(navController,scroll, headerHeightPx, toolbarHeightPx,{sort->
                            if(sort) {
                                filterclicked = false
                                productClicked = false
                                scope.launch { modalBottomSheetState.show() }
                            }

                        },{ filter->
                            if(filter){
                                filterclicked = true
                                productClicked = false
                                scope.launch { modalBottomSheetState.show() }


                            }


                        })
                        Title(viewModal.myParcelableData!!.message ?: "none", scroll, headerHeightPx, toolbarHeightPx)
                    }

                }
                if (viewModal.getitemcountState.value >= 1 &&(viewModal.getFreeDeliveryMinPrice().isNotEmpty()))
                    AddToCartCardView(
                        viewModal,
                        navController,
                        context,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(l3) {
                                this.top.linkTo(createguidlinefromtop)
                                this.bottom.linkTo(parent.bottom)
                            }
                    )

            }



        }
    }


}
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun showItemDescription(
    productdetail: MutableState<HomeAllProductsResponse.HomeResponse>,
    modalBottomSheetState: ModalBottomSheetState
) {

    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(pageCount = 3)
    Box(modifier = Modifier
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
            .background(Color.White)
            .constrainAs(l1) {
                top.linkTo(parent.top)

            }
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )) {
                Card(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)

                ) {
                    HorizontalPager( state = pager) { index ->
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
                        color= headingColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = {

                    }) {

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text12_body1(
                    text = productdetail.value.productDescription ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text13_body1(text = "Product Detail", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp))



            }


        }


    }

}





@Composable
fun SubItems(
    data: HomeAllProductsResponse.HomeResponse,
    viewModal: HomeAllProductsViewModal,
    context: Context, passclicked:(HomeAllProductsResponse.HomeResponse)->Unit
) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)

            .width(160.dp)
            .clickable {
                passclicked(data)

//                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier

                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {

            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.selling_price?.toFloat() ?: 0.0f) / (data.orignal_price?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()
            Text10_h2(
                text = "${offpercentage}% off", color = sec20timer,
                modifier = Modifier.align(
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
                                data.productName,
                                data.orignal_price ?: "",
                                data.sellerId.toString()
                            )
                            viewModal.getItemCount()
                            viewModal.getItemPrice()
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
fun ItemEachRow(
    item: FilterOptions,
    selectedIndex: MutableState<Int>,
    call: (item: String, selectedvalue: Int) -> Unit
) {
    Card(elevation = 1.dp,


        backgroundColor = if (selectedIndex.value == item.productId?.toInt()) Color.White else Color.White,
        modifier = Modifier

            .width(100.dp)
            .height(50.dp)

            .padding(start = 5.dp, bottom = 5.dp)

            .clickable {

                call(item.productId ?: "", item.productId?.toInt()!!)
            }) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center


        ) {

            Text12_body1(text = item.name, modifier = Modifier.align(Alignment.CenterHorizontally))


        }

    }


}

@Composable
private fun Header(
    scroll: ScrollState,
    headerHeightPx: Float,

    ) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(headerHeight)
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 2f // Parallax effect
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }
    ) {
        Image(

            painter = painterResource(id = R.drawable.banner),
            modifier = Modifier,
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, whiteColor),
                        startY = 3 * headerHeightPx / 4 // Gradient applied to wrap the title only
                    )
                )
        )
    }
}

@Composable
private fun Body(

    scroll: ScrollState,
    viewModal: HomeAllProductsViewModal,
    context: Context, passclicked: (HomeAllProductsResponse.HomeResponse) -> Unit

    ) {
    var ls: List<HomeAllProductsResponse.HomeResponse>? = null
    when (viewModal.responseLiveData.value) {

        "asc" -> {
            ls = viewModal.globalmutablelist1.value.list?.sortedBy { it.productName?.lowercase() }
        }

        "dsc" -> {
            ls = viewModal.globalmutablelist1.value.list?.sortedByDescending { it.productName?.lowercase() }
        }
        "high" -> {
            ls = viewModal.globalmutablelist1.value.list?.sortedBy { it.selling_price?.toInt() }
        }
        "low" -> {
            ls = viewModal.globalmutablelist1.value.list?.sortedByDescending { it.selling_price?.toInt() }
        }
        "filter" -> {
            ls = viewModal.listState.value.list
        }
        else -> {
            ls = viewModal.globalmutablelist1.value.list
        }

    }
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
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                .verticalScroll(scroll)

        ) {

            Spacer(Modifier.height(headerHeight))
            (LocalConfiguration.current.screenWidthDp.dp / 2)
            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {
                if (ls != null) {
                    for (item in ls) {
                        SubItems(item, viewModal, context, ){
                            passclicked(it)
                        }
                    }
                }

            }


            Spacer(modifier = Modifier.height(70.dp))

        }
    }


}

@Composable
private fun Toolbar( navController: NavHostController,scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float, callSort: (Boolean) -> Unit,callFilter:(Boolean)->Unit) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf {
            scroll.value >= toolbarBottom
        }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        TopAppBar(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                )
            ),
            actions = {
                IconButton(onClick = {
                    callSort(true)

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.sort),

                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(15.dp)
                            .height(15.dp)
                    )

                }

                // lock icon
                IconButton(onClick = {
                    callFilter(true)

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.filter),

                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(15.dp)
                            .height(15.dp)
                    )

                }
            },
            navigationIcon = {
                IconButton(
                    onClick = {  navController.popBackStack()},
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
                IconButton(
                    onClick = { /* Handle second icon click */ },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.Black
                    )
                }
            },
            title = {},
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}

@Composable
private fun Title(
    str: String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text16_h1(
        text = str,
        color= headingColor,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)

                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 5f

                val titleYFirstInterpolatedPoint = lerp(
                    headerHeight - titleHeightPx.toDp() - paddingMedium,
                    headerHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val marginTopWhenScrolled = 7.dp // Adjust the margin value as needed


                // Calculate the margin based on the scroll position
                val marginTop = if (collapseFraction > 0) {
                    lerp(0.dp, marginTopWhenScrolled, collapseFraction).toPx()
                } else {
                    0f // No margin when not scrolled
                }


                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                ) + marginTop.dp

                val titleX = lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}





