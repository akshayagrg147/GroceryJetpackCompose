package com.grocery.groceryapp.features.Spash

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.redColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProductByIdViewModal

@Composable
fun ItemScreenNavigation(context: Context, productId: String?, category: String?, viewModal: ProductByIdViewModal = hiltViewModel()) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoute.ItemDetailScreen.route) {
        composable(ScreenRoute.ItemDetailScreen.route) {
            when (category) {
                "Best" -> viewModal.calllingBestProductById(ProductIdIdModal(productId=productId))
                "exclusive" -> viewModal.calllingExclsuiveProductById(ProductIdIdModal(productId=productId))
                else -> {
                    viewModal.calllingAllProductById(ProductIdIdModal(productId=productId))
                }
            }
            var response=viewModal.responseLiveData
            if(response.value.statusCode==200){
                ItemDetailsScreen(response.value)
            }
            else{
                Toast.makeText(context, response.value.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ItemDetailsScreen(value: ProductByIdResponseModal) {
    val productdetail = remember { mutableStateOf(false) }
    val neutrition = remember { mutableStateOf(false) }
    val reviws = remember { mutableStateOf(false) }
    val pager = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .align(Alignment.TopCenter)

        ){
            HorizontalPager(count = 3, state = pager) { index ->
                if(index==0)
                Banner(pagerState = pager,value.homeproducts?.productImage1?:"")
                if(index==1)
                    Banner(pagerState = pager,value.homeproducts?.productImage2?:"")
                if(index==2)
                    Banner(pagerState = pager,value.homeproducts?.productImage3?:"")
            }

        }


        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 300.dp)
                .align(Alignment.TopCenter)
        ) {
            val itemcart: MutableState<Int> = remember { mutableStateOf(1) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text20_700(
                    text = value.homeproducts?.productName?:"",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart_icon),
                        contentDescription = "",
                        tint = redColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text14_400(text = value.homeproducts?.quantity?:"")
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    CommonButton(icon = R.drawable.minus){

                        itemcart.value -= 1

                    }
                    if(itemcart.value>=1)
                        Text16_700(
                            text = itemcart.value.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 20.dp),
                            color = Color.Black
                        )
                    CommonButton(icon = R.drawable.add){
                        itemcart.value += 1
                    }
                }
                Text18_600(
                    text = value.homeproducts?.price?:"",
                    color = blackColor,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

            }


//row 1
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text20_700(
                    text = "Product Details",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(painter = painterResource(
                    id = if (productdetail.value) R.drawable.right_arrow
                    else
                        R.drawable.ic_baseline_arrow_drop_down_24
                ), contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clickable
                        {
                            productdetail.value = !productdetail.value
                        })


            }
            if(productdetail.value)
                Text14_400(text = value.homeproducts?.ProductDescription?:"")

            //row 3
            Spacer(modifier = Modifier.height(20.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text20_700(
                    text = "Reviews",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(painter = painterResource(
                    id = if (reviws.value) R.drawable.right_arrow
                    else
                        R.drawable.ic_baseline_arrow_drop_down_24
                ), contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clickable
                        {
                            reviws.value = !reviws.value
                        })


            }
            if(reviws.value)
                Text14_400(text = "Apples are nutritious. Apples may be good for weight loss. apples may be good for your heart. As part of a healtful and varied diet.",)

        }

        Box(
            modifier  = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                .background(colorResource(id = R.color.green_700))
                .clip(RoundedCornerShape(30.dp)),
            ) {
            Text24_700(text = "Add to Basket", color = Color.White,modifier  = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center))

        }

    }


}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(
    pagerState: PagerState,
    productImage1: String?
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(productImage1),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()

        )

        HorizontalPagerIndicator(
            pagerState = pagerState, pageCount = 3,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 10.dp),
            indicatorWidth = 8.dp,
            indicatorShape = RectangleShape
        )
    }

}

