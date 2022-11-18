package com.grocery.groceryapp.features.Home.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.R
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.Utils.Text20_700
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.data.modal.OrderIdResponse
import com.grocery.groceryapp.features.Home.ui.ui.theme.whiteColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.AddressViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun OrderConfirmation(
    data: OrderIdResponse,
    navController: NavHostController,viewModal: HomeAllProductsViewModal = hiltViewModel()
) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder(LocalContext.current))
            } else {
                add(GifDecoder())
            }
        }
        .build()
    BackHandler {


        navController.popBackStack(BottomNavItem.Home.screen_route,false)
     //   navController.popBackStack(0, false)

    }
    if(data.statusCode==200)
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Center

        ) {
            viewModal.deleteCartItems()


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp),
                shape = RoundedCornerShape(8.dp), elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(whiteColor).padding(20.dp)
                        .clickable { }
                ) {
                    Box(modifier = Modifier.fillMaxWidth(),  Alignment.Center) {
                        Image(
                            painter = rememberImagePainter(
                                imageLoader = imageLoader,
                                data = R.drawable. success,
                                builder = {
                                },
                            ),
                            alignment = Alignment.TopCenter,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Text20_700(text = "Order Placed!",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .align(Alignment.CenterHorizontally))
                    Text14_400(
                        text = "Your will receive a confirmation email with order details",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)

                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order Id:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "${data.productResponse?.orderId}",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order value",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "${data.productResponse?.totalOrderValue}",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }




                    }
                }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp).padding(20.dp),
                shape = RoundedCornerShape(8.dp), elevation = 10.dp
            ){
                Column(
                    modifier = Modifier
                        .background(whiteColor).padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ){
                    Text16_700(
                        text = "Delivery by Wed,Sept 7th 22",
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                    )

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Deliver Address:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "${data.productResponse?.address}",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Contact Number:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "${data.productResponse?.mobilenumber}",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order Date:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "${data.productResponse?.createdDate}",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }


                }
            }

        }


}
    else
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize(), verticalArrangement = Arrangement.Center

        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(20.dp),
                shape = RoundedCornerShape(8.dp), elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(whiteColor)
                        .clickable { }
                ) {
                    Box(modifier = Modifier.fillMaxWidth(),  Alignment.Center) {
                        Image(
                            painter = rememberImagePainter(
                                imageLoader = imageLoader,
                                data = R.drawable. failed,
                                builder = {
                                },
                            ),
                            alignment = Alignment.TopCenter,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Text20_700(text = "Order Failed!",
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .align(Alignment.CenterHorizontally))
                    Text14_400(
                        text = "Some missing information",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 10.dp)

                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),Arrangement.SpaceBetween) {
                        Text16_700(
                            text = "Order Id:",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )
                        Text14_400(
                            text = "Bk12H1121",
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        )

                    }
                }
            }

        }





    }


}