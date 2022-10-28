package com.grocery.groceryapp.features.Spash.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.domain.modal.FilterOptions
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItems(
    context: Context,
    ls: HomeAllProductsResponse,
    viewModal: LoginViewModel = hiltViewModel()
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val mutablelist= remember {
        mutableStateOf<HomeAllProductsResponse>(ls)

    }
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


                    var sliderPosition by remember { mutableStateOf(0f) }
                    Column {
                        Text(text = sliderPosition.toString())

                    }








                    Text20_700(
                        text = "FILTER & SORT",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text14_400(text = "Choose a range below")
                            Row(modifier = Modifier, Arrangement.SpaceEvenly) {
                                OutlinedTextField(value = minimum, onValueChange = {
                                    minimum = it
                                }, label = { Text(text = "min") }, modifier = Modifier
                                    .width(100.dp)
                                    .height(50.dp)


                                )
                                Text14_400(
                                    text = "to",
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                OutlinedTextField(value = maximum, onValueChange = {
                                    maximum = it
                                }, label = { Text(text = "max") }, modifier = Modifier
                                    .width(100.dp)
                                    .height(50.dp)

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

                        mutablelist.value.list?.sortedBy { it -> Integer.parseInt(it.price)<40 }
                        scope.launch {
                            modalBottomSheetState.hide()
                        }

                    })
                Text16_700(text = "Desending(Z-A)", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {
                        mutablelist.value.list?.sortedByDescending { it -> it.productName }
                        scope.launch { modalBottomSheetState.hide() }
                    })
                Text16_700(text = "High to low", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {
                        mutablelist.value.list?.sortedBy { it -> it.price?.toInt() }
                        scope.launch { modalBottomSheetState.hide() }
                    })
                Text16_700(text = "Low to high", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 5.dp)
                    .clickable {
                        mutablelist.value.list?.sortedByDescending { it -> it.price?.toInt() }
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
                .background(Color.Gray)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.banner), contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text24_700(text = mutablelist?.value.message?:"none", modifier = Modifier.align(
                        Alignment.BottomCenter))

                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
                    LazyColumn( modifier = Modifier.fillMaxHeight()) {
                        items(mutablelist?.value.list!!) { data ->
                            // Column(modifier = Modifier.padding(10.dp)) {
                            SubItems(data)
                            //}


                        }


                    }
                }

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

@Composable
fun SubItems(data: HomeAllProductsResponse.HomeResponse) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .border(
                border = ButtonDefaults.outlinedBorder,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp)
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
                painter = rememberAsyncImagePainter(data.productImage1),
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




