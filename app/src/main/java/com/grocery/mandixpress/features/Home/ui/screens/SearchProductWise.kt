package com.grocery.mandixpress.features.Home.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.Home.Navigator.gridItems
import com.grocery.mandixpress.features.Home.ui.ui.theme.*
import com.grocery.mandixpress.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun SearchResult(
    data: HomeAllProductsResponse.HomeResponse,
    viewModal: HomeAllProductsViewModal,
    context: Context, navcontroller: NavHostController
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .width(150.dp)
            .clickable {
               // navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 15.dp)
        ) {
            val offpercentage: String = (DecimalFormat("#.##").format(
                100.0 - ((data.selling_price?.toFloat() ?: 0.0f) / (data.orignal_price?.toFloat()
                    ?: 0.0f)) * 100
            )).toString()
            Text(
                text = "${offpercentage}% off", color = titleColor,
                modifier = Modifier.align(
                    Alignment.End
                ),
                fontSize = 10.sp,
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

                Text10_h2(
                    text = "₹ ${data.selling_price}",
                    color = headingColor,
                    //  modifier= Modifier.weight(0.5F)
                )
                Text(
                    text = "₹${data.orignal_price ?: "0.00"}",
                    fontSize = 11.sp,
                    color = bodyTextColor,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Card(
                    border = BorderStroke(1.dp, titleColor),
                    modifier = Modifier

                        .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                        .padding(start = 20.dp)

                        .background(color = whiteColor)
                        .clickable {
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

                        },

                    ) {
                    Text11_body2(
                        text = "ADD",
                        availColor,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                    )
                }


            }

        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchScreenProducts(
    navHostController: NavHostController,
    context: Context,
    viewModal: HomeAllProductsViewModal = hiltViewModel()
) {
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
        val focusRequester = FocusRequester()
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }

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
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .focusRequester(focusRequester = focusRequester),
            placeholder = {
                Text12_body1(
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

                        responseData.value = HomeAllProductsResponse()
                    }) {
                        Icon(
                            Icons.Default.Close, contentDescription = "",
                        )
                    }
                }
            },
            leadingIcon = {
                IconButton(onClick = { responseData.value = HomeAllProductsResponse() }) {
                    Icon(
                        Icons.Default.Search, contentDescription = "",
                    )
                }
            },
            singleLine = true,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Text13_body1(
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
                SearchResult(itemData, viewModal, context, navHostController)
            }
        }
    }


}



