package com.grocery.mandixpress.features.home.ui.screens

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.Utils
import com.grocery.mandixpress.common.AddToCartCardView
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.home.Navigator.gridItems
import com.grocery.mandixpress.features.home.ui.ui.theme.*
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
import com.grocery.mandixpress.roomdatabase.CartItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun SearchResult(
    data: HomeAllProductsResponse.HomeResponse,
    viewModal: HomeAllProductsViewModal,
    context: Context,
    showExtraChargesPopUp:(CartItems,AdminAccessTable, Boolean)->Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        if (  data.quantity?.isNotEmpty()==true && data.quantity.toInt()==0)
        Text11_body2(
            text = "out of stock",
            redColor,
            modifier = Modifier.padding(end = 5.dp, top = 15.dp).align(alignment = Alignment.Center)

        )
        Card(
            elevation = 2.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .alpha(if (data.quantity?.isNotEmpty() == true && data.quantity.toInt() == 0) 0.7f else 1.0f)
                .clickable {
                    // navcontroller.navigate(DashBoardNavRoute.ProductDetail.senddata("${data.ProductId!!} exclusive"))
                }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 15.dp)
            ) {
                val originalPrice = data.orignal_price?.toFloat() ?: 0.0f
                val sellingPrice = data.selling_price?.toFloat() ?: 0.0f

                val offPercentage = ((originalPrice - sellingPrice) / originalPrice) * 100

                val formattedPercentage = DecimalFormat("#.##").format(offPercentage)

                Text10_h2(
                    text = "${formattedPercentage}% off", color = sec20timer,
                    modifier = Modifier.align(
                        Alignment.End
                    ),

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
                    text = data.productName?:"", color = headingColor,
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
                                if (data.quantity?.isNotEmpty() == true && data.quantity.toInt() != 0) {
                                    // response="called"
                                    viewModal.insertCartItem(
                                        data.ProductId ?: "",
                                        data.productImage1 ?: "",
                                        data.selling_price?.toInt() ?: 0,
                                        data.productName?:"",
                                        data.orignal_price ?: "",
                                        data.sellerId.toString()
                                    ) { adminAccess, cartItems ->
                                        if (adminAccess.city != null) {
                                            showExtraChargesPopUp(cartItems, adminAccess, true)


                                        } else {
                                            MainScope().launch {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Added to cart",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()

                                                Utils.vibrator(context)
                                            }
                                        }

                                    }
                                    viewModal.getItemCount()
                                    viewModal.getItemPrice()
                                    MainScope().launch {
                                        Toast
                                            .makeText(
                                                context,
                                                "Added to cart",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()

                                        Utils.vibrator(context)
                                    }
                                }

                            },

                        ) {
                        Text11_body2(
                            text = if (data.quantity?.isNotEmpty() == true && data.quantity.toInt() == 0) "Notify" else "ADD",
                            availColor,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                    }


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
    val search = rememberSaveable {
        mutableStateOf("")
    }
    val responseData: MutableState<HomeAllProductsResponse> = rememberSaveable {
        mutableStateOf(HomeAllProductsResponse())
    }
    val scope = rememberCoroutineScope()
    var newSellerAddedDialog by remember { mutableStateOf(false) }


    if(newSellerAddedDialog)
        com.grocery.mandixpress.common.CustomDialog(
            title = "Mandi Express",
            message = "Delivery charges may change as you are adding to other seller",
            onShowDialog = {
                newSellerAddedDialog=false


            }
            , onYesClick = {
                newSellerAddedDialog=false
                viewModal.updateDeliveryCharges(viewModal.getStoreAdminCartTable().first, viewModal.getStoreAdminCartTable().second)
                { it ->
                    if (it != 0) {
                        MainScope().launch {
                            Toast
                                .makeText(
                                    context,
                                    "Added to cart",
                                    Toast.LENGTH_SHORT
                                )
                                .show()

                            Utils.vibrator(context)
                        }
                    }
                }

            })
    scope.launch(Dispatchers.IO) {
        viewModal.repo.collectLatest {
            when (it) {
                is ApiState.Success -> {

                    responseData.value = it.data
                }
                is ApiState.Failure -> {
                    showLog("gettingresponse", it.msg.message.toString())

                }

            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            val focusRequester = FocusRequester()
            DisposableEffect(Unit) {
                focusRequester.requestFocus()
                onDispose { }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navHostController.popBackStack() },
                    modifier = Modifier.padding(end = 0.dp, top = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.Black // Customize the color as needed
                    )
                }



                TextField(
                    value = search.value,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        search.value = it
                        viewModal.setupFlow(search.value)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text // Set keyboard type to Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .padding(start = 5.dp, end = 10.dp, top = 10.dp)
                        .focusRequester(focusRequester = focusRequester),
                    placeholder = {
                        Text12_body1(
                            text = "Search Product",
                            color = bodyTextColor,
                        )
                    },


                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.LightGray,
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
                        IconButton(onClick = {
                            responseData.value = HomeAllProductsResponse()
                        }) {
                            Icon(
                                Icons.Default.Search, contentDescription = "",
                            )
                        }
                    },
                    singleLine = true,
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            if (search.value.isNotEmpty())
                Text13_body1(
                    text = "Results finds ${responseData.value.list?.size}",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            else {
                Text13_body1(
                    text = "Results finds 0",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
if(responseData.value.list?.isNotEmpty()==true)
            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize()
            ) {
                gridItems(
                    data = responseData.value.list?: emptyList(),
                    columnCount = 2,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 1.dp)
                ) { itemData ->
                    if (search.value.isNotEmpty())
                        SearchResult(itemData, viewModal, context){
                                cartItem,obj,boolean->
                            newSellerAddedDialog =boolean
                            viewModal.tempStoreAdminCartTable(obj,cartItem)


                        }
                }
            }
            else{
    Image(
        painter = painterResource(id = R.drawable.noitems),
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = Modifier.height(300.dp).fillMaxWidth()
    )
            }
        }
        if (viewModal.getitemcountState.value >= 1 &&(viewModal.getFreeDeliveryMinPrice()>0.0))
            AddToCartCardView(
                viewModal,
                navHostController,
                context,
                modifier = Modifier.align(Alignment.BottomCenter)
            )


    }
}



