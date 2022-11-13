package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.grocery.groceryapp.BottomNavigation.BottomNavItem

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.greyLightColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProfileViewModal

@Composable
fun ProfileScreenNavigation(navController: NavHostController, context: Activity) {


            OrderHistoryScreen(context,navController)


}
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OrderHistoryScreen(

    context: Activity,navController: NavHostController,viewModal: ProfileViewModal= hiltViewModel()
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        viewModal.callingOrderHistory()
        CommonHeader(text = "Order History") {
            context.finish()
        }

        Column(
            modifier = Modifier
                .padding(top = 5.dp)
            // .height(400.dp)
        ) {
            LazyColumn(
                //  verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp)
                // .verticalScroll(state = scrollState)

            ){

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
                if(viewModal.orderhistorydata.value.statusCode==200)
                {items(viewModal.orderhistorydata.value.list!!){data->
                    OrderHistoryRow(data){
                        navController.currentBackStackEntry?.arguments?.putParcelable("orderDetail", viewModal.orderhistorydata.value)
                        navController.navigate(BottomNavItem.OrderDetail.screen_route)
                    }
                }}

            }

        }

    }



}
@Composable
fun OrderHistoryRow(data: AllOrdersHistoryList.Orders,call:(AllOrdersHistoryList.Orders)->Unit) {
Card(modifier = Modifier.fillMaxWidth() .padding(5.dp).clickable {
    call(data)
}, elevation = 3.dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_orders_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {

                Text16_700(text = data?.orderId?:"", modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(2.dp))
                Text14_400(text = "Total Amount ₹ ${data?.totalOrderValue}")
                Spacer(modifier = Modifier.height(2.dp))

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = androidx.browser.R.color.browser_actions_bg_grey)),


            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text14_400(text = "${data?.orderList?.size} items")


        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
        Text14_400(text = "Order Date ${data?.createdDate} ")
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),


            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(R.drawable.location_pin_icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)


            )
            Text14_400(text = "${data.address}")


        }

        Spacer(modifier = Modifier.height(10.dp))



    }
}


}