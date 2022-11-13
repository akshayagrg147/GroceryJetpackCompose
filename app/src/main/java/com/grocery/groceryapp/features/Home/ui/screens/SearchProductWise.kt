package com.grocery.groceryapp.features.Home.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.Navigator.gridItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.bodyTextColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.greycolor
import com.grocery.groceryapp.features.Home.ui.ui.theme.headingColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SearchResult(
    data: HomeAllProductsResponse.HomeResponse
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White,

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            val offpercentage = data.orignalprice?.toInt()?.minus((data.price?.toInt() ?: 0))
            Text(
                text = "${offpercentage}% off", color = titleColor, modifier = Modifier.align(
                    Alignment.End
                ),fontSize = 10.sp,
            )

            Image(
                painter = rememberImagePainter(data.productImage1),

                contentDescription = "",
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp)
                    .align(alignment = Alignment.CenterHorizontally),


                )
            Text13_700(
                text = data.productName ?: "", color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = "₹${data.orignalprice ?: "0.00"}",
                    color = bodyTextColor,fontSize = 11.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Text12Sp_600(
                    text = "₹${data.price ?: "0.00"}", color = headingColor,
                    modifier = Modifier.width(IntrinsicSize.Min)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))


        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchScreenProducts(viewModal: HomeAllProductsViewModal = hiltViewModel()) {
    var search = rememberSaveable {
        mutableStateOf("")
    }
    var responseData: MutableState<HomeAllProductsResponse> = rememberSaveable {
        mutableStateOf(HomeAllProductsResponse())
    }
    val scope = rememberCoroutineScope()
    scope.launch(Dispatchers.IO) {
        viewModal.repo?.collectLatest {
            when (it) {
                is ApiState.Success -> {

                    responseData.value = it.data
                }
                is ApiState.Failure -> {
                    Log.d("gettingresponse", it.msg.message.toString())

                }

            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {


        TextField(
            value = search.value,
            shape = RoundedCornerShape(8.dp),
            onValueChange = {
                search.value = it
                viewModal.setupFlow(search.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .padding(start = 10.dp, end = 10.dp),
            placeholder = {
                Text14_400(
                    text = "Search Store",
                    color = bodyTextColor,
                )
            },

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Transparent,
                trailingIconColor = titleColor,
                backgroundColor = greycolor,
                disabledIndicatorColor = Color.Transparent
            ),

            trailingIcon = {
                if (search.value != "") {
                    IconButton(onClick = {
                        search.value = ""
                    }) {
                        Icon(
                            Icons.Default.Close, contentDescription = "",
                        )
                    }
                }
            },
            leadingIcon = {
                IconButton(onClick = {responseData.value}) {
                    Icon(
                        Icons.Default.Search, contentDescription = "",
                    )
                }
            },
            singleLine = true,
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        Text16_700(
            text = "Results finds ${responseData.value.list?.size}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize()
        ) {
            gridItems(
                data = responseData.value.list!!,
                columnCount = 2,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 1.dp)
            ) { itemData ->
                SearchResult(itemData)
            }


        }


    }


}



