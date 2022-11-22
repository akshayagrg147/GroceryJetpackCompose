package com.grocery.groceryapp.features.Home.ui.screens

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.navdrawerColor

import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import com.grocery.groceryapp.data.modal.PassingAddress
import kotlinx.coroutines.launch
@Composable
fun AddressFiled(data: AddressItems, selectedIndex: MutableState<Int>,call:(AddressItems)->Unit) {

    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, Color.Black),
        backgroundColor = if(selectedIndex.value==data.id.toInt()) Color.LightGray else Color.White ,modifier = Modifier
            .fillMaxWidth()

            .padding(15.dp)
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .clickable {
                selectedIndex.value = data.id.toInt()

            }

    ){
        Box(modifier = Modifier

        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Box(modifier = Modifier.fillMaxWidth()){
                    Text18_600(text = data.customer_name, modifier = Modifier)
                    Image(
                        painter = painterResource(id = R.drawable.location_pin),

                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(20.dp)
                            .height(20.dp)
                            .align(Alignment.TopEnd)
                            .clickable { call(data) }

                    )
                }


                Text14_400(text = data.Address1)
                Text14_400(text = "${data.Address2}, ${data.PinCode},")
                Text14_400(text = data.LandMark)

            }
        }
    }



}
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun AllAddress(navHostController: NavHostController,context: Context,viewModal: HomeAllProductsViewModal = hiltViewModel()){
    var selectedIndex = remember{ mutableStateOf(1) }
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {
//        Text24_700(text = "All address", modifier = Modifier
//                .fillMaxWidth()
//            .padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
//                .align(Alignment.CenterHorizontally))

            Text16_700(text = "Add Address", modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navHostController.navigate(DashBoardNavRoute.AddnewAddressScreen.screen_route)
                 //   viewModal.list.value.sortedBy { it.customer_name }

                }
                .padding(start = 10.dp, top = 15.dp, bottom = 10.dp)
                .align(Alignment.CenterHorizontally), color = navdrawerColor
            )

        }

        LazyColumn(
            modifier = Modifier
                .padding(top = 45.dp)
                .fillMaxSize()
            // .height(260.dp)
        ) {
//            key = {
//                it.id
//            }
            scope.launch {
                viewModal.getAddress()

            }
            item {
                if(viewModal.list.value.isEmpty())
                    noAddressAvailable() }
            items(viewModal.list.value) { item ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {


                    viewModal.deleteAddress(item.id.toInt())
                }
                else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    val passing= PassingAddress(item.id,item.customer_name,"ak@gmail.com",item.customer_PhoneNumber,item.PinCode.toString(),item.LandMark,item.Address1,item.Address2)

                    navHostController.currentBackStackEntry?.arguments?.putParcelable("address", passing)
                    navHostController.navigate(DashBoardNavRoute.AddnewAddressScreen.screen_route)
                }

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {

                        val color = when (dismissState.dismissDirection) {
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Transparent
                            else -> {Color.Transparent}
                        }
                        val direction = dismissState.dismissDirection

                        if (direction == DismissDirection.StartToEnd) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.align(Alignment.CenterStart)) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Text(
                                        text = "Move to Edit", fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }

                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.heightIn(5.dp))
                                    Text(
                                        text = "Move to Delete",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray
                                    )

                                }
                            }
                        }
                    },
                    dismissContent = {

                        Card(
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 4.dp else 0.dp
                            ).value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(alignment = Alignment.CenterVertically).animateItemPlacement(animationSpec = tween(durationMillis = 600))
                        ) {
                            AddressFiled(item, selectedIndex, call = {
                                val passing= PassingAddress(it.id,it.customer_name,"ak@gmail.com",it.customer_PhoneNumber,it.PinCode.toString(),it.LandMark,it.Address1,it.Address2)

                                navHostController.currentBackStackEntry?.arguments?.putParcelable("address", passing)
                                navHostController.navigate(DashBoardNavRoute.AddnewAddressScreen.screen_route)


                            })
                        }
                    })
            }

        }}
@Composable
fun noAddressAvailable(){
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(250.dp))
        Text14_400(text = "No Address available", modifier = Modifier.align(Alignment.Center))


    }

}