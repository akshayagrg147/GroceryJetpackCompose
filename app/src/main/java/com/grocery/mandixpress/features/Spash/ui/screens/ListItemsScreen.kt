package com.grocery.mandixpress.features.Spash.ui.screens

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.Home.domain.modal.FilterOptions
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Spash.ui.viewmodel.HomeAllProductsViewModal
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
    context: Context,
    ls: HomeAllProductsResponse,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    viewModal.setList(ls)

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var listdata = null
    var filterclicked by remember { mutableStateOf(false) }
    var minimum by remember { mutableStateOf("") }
    var maximum by remember { mutableStateOf("") }
    var selectedIndex = remember { mutableStateOf(1) }
    ModalBottomSheetLayout(

        sheetContent = {

            if (filterclicked)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(top = 10.dp), Arrangement.SpaceEvenly
                ) {
                    val ls: MutableList<FilterOptions> = ArrayList()
                    ls.add(FilterOptions("By Budget", "1"))
                    // ls.add(FilterOptions("Change Sort", "2"))
                    LazyColumn(
                        //  verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(bottom = 1.dp)
                        // .verticalScroll(state = scrollState)

                    ) {
                        items(ls) { item ->

                            ItemEachRow(item, selectedIndex) { productid, selectedvalue ->
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
                            text = "FILTER & SORT",
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
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(
                                    text = "Rs. 49 and below",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 12.sp
                                )
                            }

                            Row {
                                Checkbox(

                                    checked = twohundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { twohundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(
                                    text = "Rs. 11 and 30",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 12.sp
                                )
                            }

                            Row {
                                Checkbox(

                                    checked = threehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { threehundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(
                                    text = "Rs. 41 and 50",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 12.sp
                                )
                            }
                            Row {
                                Checkbox(

                                    checked = fivehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { fivehundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(
                                    text = "Rs. 50 and above",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 12.sp
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
                            var filterlist: List<HomeAllProductsResponse.HomeResponse>? = viewModal.globalmutablelist1.value.list
                            var filterlist1: List<HomeAllProductsResponse.HomeResponse>? = null
                            if (hundredone.value)
                                filterlist1 = filterlist?.filter {
                                    it.selling_price?.toInt()!! <= 49
                                }
                             if (twohundredone.value)
                                 filterlist1 = filterlist?.filter {
                                    it.selling_price?.toInt()!! in 11..30
                                }
                             if (threehundredone.value)
                                 filterlist1 =filterlist?.filter {
                                    it.selling_price?.toInt()!! in 41..50
                                }
                            if (fivehundredone.value)
                                filterlist1 =filterlist?.filter {
                                    it.selling_price?.toInt()!! >=51
                                }
                            else{
                               //  filterlist1=filterlist
                            }
                            viewModal.setFilterList(filterlist1)

                            viewModal.setvalue("filter")



                        }
                    }
                }
            else
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
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
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (l3) = createRefs()
            var createguidlinefromtop = createGuidelineFromBottom(70.dp)

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
                    Body(scroll, viewModal, context)
                    Toolbar(scroll, headerHeightPx, toolbarHeightPx)
                    Title(ls?.message ?: "none", scroll, headerHeightPx, toolbarHeightPx)
                }

//                item{
//                    Box(modifier = Modifier
//                        .fillMaxWidth()
//                        .height(110.dp)) {
//                        Image(
//                            painter = painterResource(id = R.drawable.banner), contentDescription = "",
//                            modifier = Modifier
//                                .fillMaxWidth()
//                        )
//                        Text24_700(text = ls?.message?:"none", modifier = Modifier.align(
//                            Alignment.BottomCenter))
//
//                    }
//                }
//
//
//                items(   lss!!) { data ->
//                    Box(modifier = Modifier
//                        .fillMaxHeight()
//                        .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
//                        SubItems(data){
//                            lss?.sortedByDescending { it->it.productName }
//                        }
//                    }
//                    // Column(modifier = Modifier.padding(10.dp)) {
//
//                    //}
//
//
//                }


            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .constrainAs(l3) {
                        this.top.linkTo(createguidlinefromtop)
                        this.bottom.linkTo(parent.bottom)
                    }, backgroundColor = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text14_h1(text = "Sort", modifier = Modifier.clickable {
                        filterclicked = false
                        scope.launch { modalBottomSheetState.show() }

                    })
                    Divider(
                        color = Color.Blue, modifier = Modifier
                            .height(20.dp)
                            .width(1.dp)
                    )
                    Text14_h1(text = "Filter", modifier = Modifier.clickable {
                        filterclicked = true
                        scope.launch { modalBottomSheetState.show() }
                    })
                }


            }

        }
    }


}

fun srt(sortTyoe: String, lss: List<HomeAllProductsResponse.HomeResponse>) {
    when (sortTyoe) {
        "asc" -> {
            lss?.sortedBy { it.productName }
        }
        "dsc" -> {
            lss?.sortedByDescending { it.productName }
        }
        "high" -> {
            lss?.sortedBy { it.selling_price?.toInt() }
        }
        "low" -> {
            lss?.sortedByDescending { it.selling_price?.toInt() }
        }

    }

}

@Composable
fun SubItems(
    data: HomeAllProductsResponse.HomeResponse,
    viewModal: HomeAllProductsViewModal,
    context: Context,
    itemSize: Dp,
    call: () -> Unit
) {

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)

            .width(160.dp)
            .clickable {
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
            Text(
                text = "${offpercentage}% off", color = titleColor,
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


        backgroundColor = if (selectedIndex.value == item.productId?.toInt()) Color.LightGray else Color.White,
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
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
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
    context: Context,

    ) {
    var ls: List<HomeAllProductsResponse.HomeResponse>? = null
    when (viewModal.responseLiveData.value) {

        "asc" -> {
            context.showMsg("asc")
            Log.d("sjsjjsj", viewModal.listState.value.list?.get(0)!!.productName!!)
            ls = viewModal.globalmutablelist1.value.list?.sortedBy { it?.productName?.lowercase() }

            Log.d("sjsjjsj", viewModal.listState.value!!.list?.get(0)!!.productName!!)
        }

        "dsc" -> {
            context.showMsg("dsc")
            ls = viewModal.globalmutablelist1.value.list?.sortedByDescending { it?.productName?.lowercase() }
        }
        "high" -> {
            context.showMsg("high")
            ls = viewModal.globalmutablelist1.value.list?.sortedBy { it.selling_price?.toInt() }
        }
        "low" -> {
            context.showMsg("low")
            ls = viewModal.globalmutablelist1.value.list?.sortedByDescending { it?.selling_price?.toInt() }
        }
        "filter" -> {

            ls = viewModal.listState.value.list
        }
        else -> {
            ls = viewModal.globalmutablelist1.value.list
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
            .verticalScroll(scroll)

    ) {

        Spacer(Modifier.height(headerHeight))
        val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
        FlowRow(
            mainAxisSize = SizeMode.Expand,
            mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
        ) {
            if (ls != null) {
                for (item in ls) {
                    SubItems(item, viewModal, context, itemSize) {

                    }
                }
            }

        }


        Spacer(modifier = Modifier.height(70.dp))

    }
}

@Composable
private fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeightPx: Float) {
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
            navigationIcon = {
                IconButton(
                    onClick = {},
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

    Text(
        text = str,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

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

                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

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

private val productnamecompare = Comparator<HomeAllProductsResponse.HomeResponse> { left, right ->
    left.productName?.lowercase()!!.compareTo(right.productName?.lowercase()!!)
}




