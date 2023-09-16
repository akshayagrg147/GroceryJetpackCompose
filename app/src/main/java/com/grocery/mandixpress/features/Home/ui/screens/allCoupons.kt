

package com.grocery.mandixpress.features.Home.ui.screens




import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.Text10_h2
import com.grocery.mandixpress.data.modal.RelatedSearchRequest
import com.grocery.mandixpress.features.Home.domain.modal.Coupon
import com.grocery.mandixpress.features.Home.domain.modal.CouponResponse
import com.grocery.mandixpress.features.Home.ui.viewmodal.CouponEvents
import com.grocery.mandixpress.features.Home.ui.viewmodal.CouponViewModel
import com.grocery.mandixpress.features.Home.ui.viewmodal.ProductEvents
import kotlinx.coroutines.launch


@Composable
fun ApplyCoupons(navController: NavController, viewModel: CouponViewModel = hiltViewModel()) {
    val commonResponse by viewModel.coupons.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        viewModel.onEvents(
            CouponEvents.CouponEvent(
            )

        )

    }
    Column {
        SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it })
        CouponList(navController=navController,coupons = commonResponse.data?.itemData?.filter { it.couponTitle.contains(searchText, ignoreCase = true) }?: emptyList())
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        label = { Text(stringResource(id = R.string.search_hint)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun CouponList(navController: NavController,coupons: List<CouponResponse.ItemData>) {

    LazyColumn {
        items(coupons) { coupon ->
            CouponItem(coupon,navController)
        }
    }
}
@Composable
fun CouponItem(coupon: CouponResponse.ItemData,navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 3.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        val coroutineScope = rememberCoroutineScope()
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(10.dp).weight(2f)

            ) {
                Text(text = "PROMO CODE", style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),color=Color.DarkGray)
                Text(text = coupon.couponCode,style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),color=Color.Black)
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = coupon.couponTitle, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp),color=Color.LightGray)
                Spacer(modifier = Modifier.height(3.dp))

            }
            Column(modifier=Modifier.background(Color.Black).weight(1f).padding(start = 10.dp,end=10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ApplyButton(navController){
                    coroutineScope.launch {
                        val bundle = Bundle()
                        bundle.putString("CouponName", coupon.couponCode)
                        bundle.putInt("CouponPercent", 10)

                        navController.previousBackStackEntry?.savedStateHandle?.set("passCoupon", bundle)

                        navController.popBackStack()
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Valid till", style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),color=Color.White)
                Text(text = coupon.expireDate, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),color=Color.White, modifier = Modifier.padding(bottom = 10.dp))
            }

        }

    }
}

@Composable
fun ApplyButton(navController: NavController,call:()->Unit) {


    Button(
        onClick = {
            call()

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text10_h2(text = "Apply")
    }
}

