package com.grocery.groceryapp.features.Home.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.HomeActivity
import com.grocery.groceryapp.features.Spash.ui.viewmodel.AddressViewModal
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel
import com.grocery.groceryapp.features.Spash.ui.viewmodel.ProductByIdViewModal
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun addressScreen(navController: NavHostController , context: Activity,viewModal: AddressViewModal = hiltViewModel()){
    val name = rememberSaveable { mutableStateOf("") }
    val phonenumber = rememberSaveable { mutableStateOf("") }
    val pincode = rememberSaveable { mutableStateOf("") }
    val address1 = rememberSaveable { mutableStateOf("") }
    val address2 = rememberSaveable { mutableStateOf("") }
    val landmark = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text24_700(text = "Add Address", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonTextField(text = name, placeholder = stringResource(id = R.string.name))
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonNumberField(text = phonenumber, placeholder = stringResource(id = R.string.phone_number))
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonNumberField(text = pincode, placeholder = stringResource(id = R.string.pincode))
        }


        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonTextField(text = address1, placeholder = stringResource(id = R.string.address1))
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonTextField(text = address2 , placeholder = stringResource(id = R.string.address2))
        }
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            CommonTextField(text = landmark, placeholder = stringResource(id = R.string.landmark))
        }
        CommonButton(
            text = "Submit Address",
            modifier = Modifier.fillMaxWidth()
        )
        {
            viewModal.saveAddress(AddressItems(name.value,phonenumber.value,Integer.parseInt(pincode.value),address1.value,address2.value,landmark.value))
            navController.navigate(ScreenRoute.CartScreen.route)

        }
    }

}