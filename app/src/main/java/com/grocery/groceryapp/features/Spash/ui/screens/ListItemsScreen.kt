package com.grocery.groceryapp.features.Spash.ui.screens

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.domain.modal.FilterOptions
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

private val headerHeight = 210.dp
private val toolbarHeight = 56.dp

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
    viewModal: LoginViewModel = hiltViewModel()
) {
    viewModal.setList(ls)

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()




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
                ls.add(FilterOptions("Change Sort", "2"))
                LazyColumn(
                    //  verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
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


                    val hundredone = remember { mutableStateOf(true) }
                    val twohundredone = remember { mutableStateOf(false) }
                    val threehundredone = remember { mutableStateOf(false) }
                    val fuvehundredone = remember { mutableStateOf(false) }
                    Text20_700(
                        text = "FILTER & SORT",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text14_400(text = "Choose a range below", modifier = Modifier.align(Alignment.CenterHorizontally))
                            Row {
                                Checkbox(

                                    checked = hundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { hundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(text = "Rs. 200 and below", modifier = Modifier.padding(16.dp), fontSize = 14.sp)
                            }

                            Row {
                                Checkbox(

                                    checked = twohundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { twohundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(text = "Rs. 201 and 300", modifier = Modifier.padding(16.dp), fontSize = 14.sp)
                            }

                            Row {
                                Checkbox(

                                    checked = threehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { threehundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(text = "Rs. 301 and 499", modifier = Modifier.padding(16.dp), fontSize = 14.sp)
                            }
                            Row {
                                Checkbox(

                                    checked = fuvehundredone.value,
                                    modifier = Modifier.padding(10.dp),
                                    onCheckedChange = { fuvehundredone.value = it },
                                )
                                // below line is use to add text to our check box and we are
                                // adding padding to our text of checkbox
                                Text(text = "Rs. 500 and above", modifier = Modifier.padding(16.dp), fontSize = 14.sp)
                            }

                        }



                    Spacer(modifier = Modifier.height(25.dp))

                    CommonButton(
                        text = "Apply",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp).clickable {
                                scope.launch { modalBottomSheetState.hide() }
//                                lss?.map {
//                                    if(hundredone.value)
//                                    it.price!!.toInt() <= 10
//                                    if(twohundredone.value)
//                                        it.price!!.toInt() >20 &&   it.price.toInt() < 30
//                                    if(threehundredone.value)
//                                        it.price!!.toInt() >30 &&   it.price.toInt() < 40
//                                    if(fuvehundredone.value)
//                                        it.price!!.toInt() > 50
//                                }

                            }
                    )
                    {


                    }
                }
            }
            else
            Column(modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)) {
                Text16_700(text = "Asending(A-Z)", modifier = Modifier
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
                Text16_700(text = "Desending(Z-A)", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {

                        scope.launch { modalBottomSheetState.hide() }
                    })
                Text16_700(text = "High to low", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {
                        scope.launch { modalBottomSheetState.hide() }
                    })
                Text16_700(text = "Low to high", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {
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

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                val scroll: ScrollState = rememberScrollState(0)

                val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
                val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Header(scroll, headerHeightPx)
                    Body(scroll,viewModal)
                    Toolbar(scroll, headerHeightPx, toolbarHeightPx)
                    Title(ls?.message?:"none",scroll, headerHeightPx, toolbarHeightPx)
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
            Card(modifier = Modifier
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
                    Text24_700(text = "Sort", modifier = Modifier.clickable {
                        filterclicked=false
                        scope.launch { modalBottomSheetState.show() }

                    })
                    Divider(
                        color = Color.Blue, modifier = Modifier
                            .height(20.dp)
                            .width(1.dp)
                    )
                    Text24_700(text = "Filter", modifier = Modifier.clickable {
                        filterclicked=true
                        scope.launch { modalBottomSheetState.show() }
                    })
                }


            }

        }
    }



}
fun srt(sortTyoe:String,lss:List<HomeAllProductsResponse.HomeResponse>){
    when (sortTyoe){
        "asc" -> { lss?.sortedBy { it.productName }}
        "dsc" -> { lss?.sortedByDescending { it.productName }}
        "high" -> { lss?.sortedBy { it.price?.toInt() }}
        "low" -> {lss?.sortedByDescending { it.price?.toInt() }}

    }

}

@Composable
fun SubItems(data: HomeAllProductsResponse.HomeResponse,call:()->Unit) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .border(
                border = ButtonDefaults.outlinedBorder,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { call() }
            .padding(top = 30.dp)
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            val (l0, l1, l2) = createRefs()
            Spacer(modifier = Modifier
                .width(100.dp)
                .constrainAs(l0) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(l1.start)
                })
            Image(
                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)

                    .constrainAs(l1) {
                        top.linkTo(parent.top)
                        start.linkTo(l0.end)
                        end.linkTo(l2.start)
                    }, alignment = Alignment.CenterStart
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .constrainAs(l2) {
                    start.linkTo(l1.end)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }) {
                Text18_600(text = data.productName!!)
                Spacer(modifier = Modifier.height(8.dp))
                Text14_400(text = data.quantity!!)
                Row() {
                    val offamount = data.orignalprice?.toInt()!! - data.price?.toInt()!!
                    Text(text = "₹ ${data.price}")
                    Text(
                        text = "₹ ${data.orignalprice}",
                        color = bodyTextColor,
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )
                    Text14_400(
                        text = "$offamount% OFF",
                        modifier = Modifier.padding(start = 10.dp),
                        color = Color.LightGray
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

            Text14_400(text = item.name, modifier = Modifier.align(Alignment.CenterHorizontally))


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
    viewModal: LoginViewModel
) {
    when (viewModal.responseLiveData.value){
        "asc" -> {
            Log.d("sjsjjsj", viewModal.listState.value.list?.get(0)!!.productName!!)
            viewModal.listState.value.list?.sortedWith(comparator = productnamecompare)
            Log.d("sjsjjsj", viewModal.listState.value!!.list?.get(0)!!.productName!!)}

        "dsc" -> {
            viewModal.listState.value.list?.sortedByDescending { it?.productName ?.lowercase()}
        }
        "high" -> { viewModal.listState.value.list?.sortedBy { it.price?.toInt() }}
        "low" -> {viewModal.listState.value.list?.sortedByDescending { it?.price?.toInt() }}

    }
       Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
               .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
               .verticalScroll(scroll)

    ) {

        Spacer(Modifier.height(headerHeight))
                repeat(viewModal.listState.value.list?.sortedWith(comparator = productnamecompare)?.size?:0) {
                    SubItems(viewModal.listState.value.list?.get(it)!!){
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
private fun Title(str:String,
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Text(
        text = str,
        fontSize = 30.sp,
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




