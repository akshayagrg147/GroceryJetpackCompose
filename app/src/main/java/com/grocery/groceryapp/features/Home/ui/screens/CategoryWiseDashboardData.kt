package com.grocery.groceryapp.features.Spash

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.grocery.groceryapp.DashBoardNavRouteNavigation.DashBoardNavRoute

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.Utils
import com.grocery.groceryapp.data.modal.AllOrdersHistoryList
import com.grocery.groceryapp.data.modal.CategoryWiseDashboardResponse
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.Navigator.gridItems
import com.grocery.groceryapp.features.Home.ui.AllItems
import com.grocery.groceryapp.features.Home.ui.GroceriesItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.*
import com.grocery.groceryapp.features.Home.ui.viewmodal.ProfileViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import java.text.DecimalFormat


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryWiseDashboardAllData(

    context: Activity,navController: NavHostController,viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
     val list= viewModal.allresponse.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CommonHeader(text = viewModal.getcategory()) {
            context.finish()
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
                    ProductWiseRow(list.peek(it)!!){data->
                        // response="called"
                        viewModal.insertCartItem(
                            data.ProductId ?: "",
                            data.productImage1 ?: "",
                            data.price?.toInt() ?: 0,
                            data.productName ?: "",
                            data.orignalprice ?: ""
                        )
                        viewModal.getCartItem()
                        Toast
                            .makeText(context, "Added to cart", Toast.LENGTH_SHORT)
                            .show()

                        Utils.vibrator(context)
                    }
//                    if(it==list.itemCount-1)
//                        Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }





    }



}
@Composable
fun ProductWiseRow(data: HomeAllProductsResponse.HomeResponse, call:(HomeAllProductsResponse.HomeResponse)->Unit) {



    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)

            .width(150.dp)
            .clickable {
//                navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {

            val offpercentage:String =(DecimalFormat("#.##").format(100.0- ((data.price?.toFloat() ?: 0.0f) /(data.orignalprice?.toFloat()?:0.0f))*100)).toString()
            Text(
                text = "${offpercentage}% off", color = titleColor, modifier = Modifier.align(
                    Alignment.End
                ),fontSize = 10.sp,
            )

            Image(

                painter = rememberImagePainter(data.productImage1),
                contentDescription = "splash image",
                modifier = Modifier
                    .width(100.dp)
                    .height(70.dp)
                    .align(alignment = Alignment.CenterHorizontally)


            )

            Text20_700(
                text = data.productName!!, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text12Sp_600(
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

                Text12Sp_600(
                    text = "₹ ${data.price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(text ="₹${data.orignalprice ?: "0.00"}",fontSize = 11.sp  , color = bodyTextColor, modifier = Modifier.padding(start = 5.dp),style= TextStyle(textDecoration = TextDecoration.LineThrough))
                Card( border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            call(data)


                        },

                    ) {
                    Text13_700(text = "ADD", availColor, modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp))
                }



            }

        }
    }






}