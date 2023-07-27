

package com.grocery.groceryapp.features.Home.ui.screens




import android.os.Bundle
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.features.Home.domain.modal.Coupon
import com.grocery.groceryapp.features.Home.ui.viewmodal.CouponViewModel
import kotlinx.coroutines.launch


@Composable
fun ApplyCoupons(navController: NavController, viewModel: CouponViewModel = hiltViewModel()) {
    val coupons: List<Coupon> by viewModel.coupons.collectAsState(emptyList())
    var searchText by remember { mutableStateOf("") }

    Column {
        SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it })
        CouponList(navController=navController,coupons = coupons.filter { it.title.contains(searchText, ignoreCase = true) })
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
fun CouponList(navController: NavController,coupons: List<Coupon>) {
    LazyColumn {
        items(coupons) { coupon ->
            CouponItem(coupon,navController)
        }
    }
}
@Composable
fun CouponItem(coupon: Coupon,navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = coupon.title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = coupon.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(16.dp))
            ApplyButton(navController){
                coroutineScope.launch {
                    val bundle = Bundle()
                    bundle.putString("CouponName", coupon.couponName)
                    bundle.putInt("CouponPercent", coupon.percentage)

                    navController.previousBackStackEntry?.savedStateHandle?.set("passCoupon", bundle)

                    navController.popBackStack()
                }
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
        Text(text = "Apply")
    }
}

