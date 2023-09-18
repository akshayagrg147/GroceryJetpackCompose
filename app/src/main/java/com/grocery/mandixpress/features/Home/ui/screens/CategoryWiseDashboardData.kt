package com.grocery.mandixpress.features.Home.ui.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.grocery.mandixpress.R

import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.LoadingBar
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.BannerImageResponse
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.features.Home.ui.viewmodal.HomeEvent
import java.text.DecimalFormat


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CategoryWiseDashboardAllData(

    context: Activity,navController: NavHostController,viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
    val bundle = navController.previousBackStackEntry?.savedStateHandle?.get<PassParceableBanner>("data") ?: PassParceableBanner()

   val itemBasedCategory by viewModal._bannerCategoryResponse.collectAsState()
    LaunchedEffect(key1 = Unit){
        viewModal.onEvent(HomeEvent.BannerCategoryEventFlow(
            bundle.second?.subCategoryList?.get(0)?.name ?:""))
    }

if(bundle.index==0){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {
            CommonHeader(text = viewModal.getcategory(), color = headingColor) {
                navController.popBackStack()
            }
            ScrollingImageRow(size = 70.dp, bundle.second?.subCategoryList) {
                viewModal.onEvent(HomeEvent.BannerCategoryEventFlow(it))
            }
            Image(
                painter = rememberImagePainter(bundle.second?.imageUrl1), null, modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
            )
            Log.d("gggddd", "previous1:- ${itemBasedCategory.data?.statusCode}")


            if (itemBasedCategory.data?.statusCode == 200) {
                bodyDashboard(itemBasedCategory.data!!.list, context, viewModal)
            }
            else  {
                val transition = rememberInfiniteTransition()
                val translateAnim by transition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1000f,
                    animationSpec = infiniteRepeatable(
                        tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                        RepeatMode.Reverse
                    )
                )
                val brush = Brush.linearGradient(
                    colors = ShimmerColorShades,
                    start = Offset(10f, 10f),
                    end = Offset(translateAnim, translateAnim)
                )
                ShimmerItem(brush = brush)
            }

        }
        if(viewModal.getitemcountState.value>=1)
            cardviewAddtoCart(
                viewModal,
                navController,
                context!!,
                modifier = Modifier.align(Alignment.BottomCenter)
            )


    }


}
    else{
    val list= viewModal.allresponse.collectAsLazyPagingItems()
    if (list.loadState.refresh is LoadState.Loading) {
        LoadingBar()
    }
    if(list.loadState.refresh is LoadState.NotLoading){
        Box(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CommonHeader(text = viewModal.getcategory(), color = headingColor) {
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
                            ProductWiseRow(list.peek(it)!!) { data ->
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
            if (viewModal.getitemcountState.value >= 1)
                cardviewAddtoCart(
                    viewModal,
                    navController,
                    context!!,
                    modifier = Modifier.align(Alignment.BottomCenter)

                )
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
@Composable
fun bodyDashboard(
    list: List<ItemsCollectionsResponse.SubItems>?,context:Context,
    viewModal: HomeAllProductsViewModal
) {
    if(list?.isNotEmpty()==true) {
        Log.d("gggddd","list:- ${list}")

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
                items(list ) { item ->
                    Log.d("gggddd","item:- ${item}")

                    ProductWiseRowBanner(item) { data ->
                        viewModal.insertCartItem(
                            data.productId ?: "",
                            data.productImage1 ?: "",
                            data.selling_price.toInt() ?: 0,
                            data.productName ?: "",
                            data.orignal_price ?: ""
                        )
                        viewModal.getItemCount()
                        viewModal.getItemPrice()
                        context.showMsg("Added to cart")
                        Utils.vibrator(context)
                    }
//                    if(it==list.itemCount-1)
//                        Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
    else{
        noItemound()
    }



}
@Composable
fun noItemound() {
    Column(modifier = Modifier.fillMaxSize(),Arrangement.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        }
        Text12_h1(
            text = "No Items Available", color = headingColor,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )


    }

}
@Composable
fun ScrollingImageRow(
    size: Dp =100.dp, ls:
                      List<BannerImageResponse.ItemData.SubCategory>?, call:(String)->Unit) {
    var selectedItemIndex by remember { mutableStateOf(-1) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        itemsIndexed(ls!!) { index, item ->
            val isSelected = selectedItemIndex == index

            Image(
                painter = rememberImagePainter(item.subCategoryUrl),
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(size)

                    .background(if (isSelected) greyLightColor else whiteColor)
                    .clip(CircleShape)
                    .let { if (isSelected) it.clip(CircleShape) else it }

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

@Composable
fun ProductWiseRowBanner(data: ItemsCollectionsResponse.SubItems, call:(ItemsCollectionsResponse.SubItems)->Unit) {



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