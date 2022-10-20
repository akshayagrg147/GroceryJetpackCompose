package com.grocery.groceryapp.features.Home.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

import com.grocery.groceryapp.R
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.CartItemsViewModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(navController: NavHostController, context: Activity, viewModal: CartItemsViewModal = hiltViewModel()) {

    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var cartcount = remember { mutableStateOf(0)}
    val totalamount = remember { mutableStateOf(0) }





    cartcount.value=viewModal.getitemcount.value
    ModalBottomSheetLayout(
        sheetContent = {

            Column(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text24_700(text = "Order Summary", color = Color.Black)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text14_400(
                        text = "Order Amount",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text14_400(
                        text = "₹ ${totalamount.value}",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text14_400(
                        text = "Service Charges",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text14_400(
                        text = "₹ ${(totalamount.value*10)/100}",
                        color = blackColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(start = 10.dp),

                ) {
                    Text24_700(text = "Choose Address", color = Color.Black)
                }
                AddressComponent(viewModal,navController)




            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )


    ) {


            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()) {

                val (l1, l2,l3) = createRefs()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(l1) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
//                    bottom.linkTo(l2.top)

                    }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text24_700(text = "MY Cart", color = Color.Black)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(l2) {
                            top.linkTo(l1.bottom)
                            bottom.linkTo(l3.top)

                        }
                ) {
                    scope.launch{
                          viewModal.getCartItem()
                         viewModal.getAddress()
                        viewModal.getcartItems()
                    }
                    items(viewModal.responseLiveData.value) { data ->
                        ItemEachRow(data, viewModal, cartcount, totalamount)

                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(l3) {
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)

                        },
                    elevation = 0.dp,
                    shape = CutCornerShape(30.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                            .background(colorResource(id = R.color.green_700))
                            .clickable { scope.launch { modalBottomSheetState.show() } },
                        contentAlignment = Alignment.Center
                    ) {
                        Text24_700(
                            text = "Go to Checkout(${cartcount.value})",
                            color = Color.White,
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                        )

                    }
                }
            }



    }}

@Composable
fun AddressComponent(viewModal: CartItemsViewModal, navController: NavHostController) {
    var selectedIndex = remember{mutableStateOf(1)}
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
            // .height(260.dp)
        ) {
//            items(viewModal.list.value) { data ->
//                    AddressFiled(data,selectedIndex)
//                }
//



        }
           Spacer(modifier = Modifier.height((2.dp)))
            Box(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(ScreenRoute.AddressScreen.route)
                }) {
                Text16_700(text = "Add Address", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .align(Alignment.Center), color = Color.Blue)

            }
        CommonButton(
            text = "Proceed to payment",
            modifier = Modifier.fillMaxWidth()
        )
        {
        }


    }



}

@Composable
fun AddressFiled(data: AddressItems, selectedIndex: MutableState<Int>) {

    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp,Color.Black),
        backgroundColor = if(selectedIndex.value==data.id.toInt()) Color.LightGray else Color.White ,modifier = Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(120.dp)
            .padding(start = 5.dp)
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .clickable {
                selectedIndex.value = data.id.toInt()
            }

    ){
        Box(modifier = Modifier

        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text14_400(text = data.customer_name)
                Text14_400(text = data.Address1)
                Text14_400(text = "${data.Address2}, ${data.PinCode},")
                Text14_400(text = data.LandMark)

            }
        }
    }



}


@Composable
fun SimpleRadioButtonComponent() {
    val radioOptions = listOf("DSA", "Java", "C++")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2]) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) })
                        .padding(horizontal = 16.dp)
                ) {
                    val context = LocalContext.current
                    RadioButton(

                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 8F)),
                        onClick = {

                            onOptionSelected(text)
                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }

}
@SuppressLint("UnrememberedMutableState")
@Composable
fun ItemEachRow(
    data: CartItems,
    viewModal: CartItemsViewModal,
    cartcount: MutableState<Int>,
    totalamount: MutableState<Int>,

) {
    Log.d("jdjdjjd","djjjdjd")
   // viewModal.getItemBaseOnProductId(data.ProductIdNumber)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Image(
                painter = rememberAsyncImagePainter(data.strCategoryThumb),
                contentDescription = "",
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text16_700(
                        text = "${data.strProductName}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = {
                        viewModal.DeleteProduct(data.ProductIdNumber)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "")
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text14_400(
                    text = "₹ ${data.strProductPrice}",
                    color = blackColor,
                )
//                Text14_400(text = data.strProductName.toString())
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        CommonMathButton(icon = R.drawable.minus) {
                            cartcount.value -= 1
                            viewModal.deleteCartItems(data)
                            totalamount.value = totalamount.value - (data.strProductPrice!!)

                        }
                        Text14_400(
                            text = viewModal.getItemBaseOnProductId(data.ProductIdNumber)
                            ,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 20.dp),
                            color = Color.Black
                        )
                        CommonMathButton(icon = R.drawable.add) {
                            cartcount.value += 1
                            viewModal.setcartvalue(viewModal.getcartvalue()+1 )
                            viewModal.insertCartItem(data)
                            totalamount.value = totalamount.value + (data.strProductPrice!!)

                        }
                    }


                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

    }

}


