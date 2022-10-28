package com.grocery.groceryapp.features.Home.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.ScreenRoute
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.navdrawerColor

import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import kotlinx.coroutines.launch

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
                .clickable { navHostController.navigate(BottomNavItem.AddnewAddressScreen.senddata("1")) }
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
            scope.launch{
                viewModal.getAddress()

            }
            items(viewModal.list.value){item ->
                AddressFiled(item,selectedIndex, call = {
                  //  viewModal.list.remove(item)
                    viewModal.deleteAddress(it)

                })
            }
//            itemsIndexed(viewModal.list) { index, item ->
//                AddressFiled(item,selectedIndex, call = {
//                    viewModal.list.remove(item)
//                    viewModal.deleteAddress(it)
//
//                })
//
//            }

            }








}
@Composable
fun AddressFiled(data: AddressItems, selectedIndex: MutableState<Int>,call:(Int)->Unit) {

    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, Color.Black),
        backgroundColor = if(selectedIndex.value==data.id.toInt()) Color.LightGray else Color.White ,modifier = Modifier
            .fillMaxWidth()
            .width(180.dp)
            .height(150.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .clickable {
                selectedIndex.value = data.id.toInt()

            }

    ){
        Box(modifier = Modifier

        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Box(modifier = Modifier.fillMaxWidth()){
                    Text14_400(text = data.customer_name, modifier = Modifier)
                    Image(
                        painter = painterResource(id = R.drawable.close_button),

                        contentDescription = "",
                        modifier = Modifier
                            .padding()
                            .width(20.dp)
                            .height(20.dp)
                            .align(Alignment.TopEnd)
                            .clickable { call(data.id.toInt()) }

                    )
                }


                Text14_400(text = data.Address1)
                Text14_400(text = "${data.Address2}, ${data.PinCode},")
                Text14_400(text = data.LandMark)

            }
        }
    }



}