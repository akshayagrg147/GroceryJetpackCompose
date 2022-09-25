package com.grocery.groceryapp.features.Spash.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel


@Composable
fun ListItems(context: Context, ls:HomeAllProductsResponse?, viewModal: LoginViewModel = hiltViewModel()){
    @Composable
    fun SubItems(data: HomeAllProductsResponse.HomeResponse) {
        Column(modifier = Modifier
            .background(Color.White)
            .border(
                border = ButtonDefaults.outlinedBorder,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(10.dp)
            .fillMaxSize()) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)) {
                val (l0,l1,l2)=createRefs()
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
                        }, alignment = Alignment.CenterStart)
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .constrainAs(l2) {
                        start.linkTo(l1.end)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }) {
                    Text18_600(text = data.productName!!)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text14_400(text = data.quantity!!)
                    Row(){
                        val offamount=data.orignalprice?.toInt()!!-data.price?.toInt()!!
                        Text16_700(text = data.price)
                        Text(text = data.orignalprice, color = bodyTextColor, modifier = Modifier.padding(start = 10.dp),style= TextStyle(textDecoration = TextDecoration.LineThrough))
                        Text16_700(text = "$offamount OFF", modifier = Modifier.padding(start = 10.dp), color = Color.Green)
                    }


                }

            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        Spacer(modifier = Modifier.height(50.dp))
        Text24_700(text = ls?.message?:"none", modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(60.dp))
        Box(modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
            LazyColumn( modifier = Modifier.fillMaxHeight()) {
                items(ls?.list!!) { data ->
                    // Column(modifier = Modifier.padding(10.dp)) {
                    SubItems(data)
                    //}


                }


            }
        }
        
    }
    
}


