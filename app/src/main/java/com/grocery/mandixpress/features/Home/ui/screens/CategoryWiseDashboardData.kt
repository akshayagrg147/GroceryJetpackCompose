package com.grocery.mandixpress.features.Home.ui.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter

import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.LoadingBar
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.BannerImageResponse
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.features.Home.ui.viewmodal.HomeEvent
import java.text.DecimalFormat


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryWiseDashboardAllData(

    context: Activity,data:PassParceableBanner,navController: NavHostController,viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
val predictions by viewModal._bannerData.collectAsState()
    val itemBasedCategory by viewModal._bannerCategoryResponse.collectAsState()




if(predictions.second==1){
    ScrollingImageRow(predictions.first.subCategoryList){
        viewModal.onEvent(HomeEvent.BannerCategoryEventFlow(it))
    }
    if(itemBasedCategory.data?.statusCode==200){
        bodyDashboard(itemBasedCategory.data!!.list)
    }
    else  if(itemBasedCategory.data?.statusCode==400){
        context.showMsg("something went wrong")
    }

}
    else{
    val list= viewModal.allresponse.collectAsLazyPagingItems()
    if (list.loadState.refresh is LoadState.Loading) {
        LoadingBar()
    }
    if(list.loadState.refresh is LoadState.NotLoading){
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CommonHeader(text = viewModal.getcategory(),color= headingColor) {
                navController.popBackStack()
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
                        }
//                    if(it==list.itemCount-1)
//                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }



        }
    }
    if(list.loadState.refresh is LoadState.Error){
        context.showMsg("error occured")
    }

    if(list.loadState.append is LoadState.Loading){
        LoadingBar()
    }
    if(list.loadState.append is LoadState.Error){
        context.showMsg("error occured")
    }
    if(list.loadState.prepend is LoadState.Loading){
        LoadingBar()
    }
    if(list.loadState.prepend is LoadState.Error){
        context.showMsg("error occured")
    }
    }

}

fun bodyDashboard(list: List<ItemsCollectionsResponse.SubItems>?) {

}

@Composable
fun ScrollingImageRow(ls:
                      List<BannerImageResponse.ItemData.SubCategory>?,call:(String)->Unit) {
    var selectedItemIndex by remember { mutableStateOf(-1) }

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        itemsIndexed(ls!!) { index, item ->
            val isSelected = selectedItemIndex == index

            Image(
                painter = rememberImagePainter(item.subCategoryUrl),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)
                    .background(if (isSelected) Color.Blue else Color.Gray)
                    .clickable {
                        selectedItemIndex = index
                        call(item.name)
                    }
            )
        }
    }
}
@Composable
fun ProductWiseRow(data: HomeAllProductsResponse.HomeResponse, call:(HomeAllProductsResponse.HomeResponse)->Unit) {



    Card(
        elevation = 2.dp,
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

            val offpercentage:String =(DecimalFormat("#.##").format(100.0- ((data.selling_price?.toFloat() ?: 0.0f) /(data.orignal_price?.toFloat()?:0.0f))*100)).toString()
            Text10_h2(
                text = "${offpercentage}% off", color = sec20timer, modifier = Modifier.align(
                    Alignment.End
                )
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
                Text(text ="₹${data.orignal_price ?: "0.00"}",fontSize = 11.sp  , color = bodyTextColor, modifier = Modifier.padding(start = 5.dp),style= TextStyle(textDecoration = TextDecoration.LineThrough))
                Card( border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
                            call(data)


                        },

                    ) {
                    Text11_body2(text = "ADD", availColor, modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp))
                }



            }

        }
    }






}