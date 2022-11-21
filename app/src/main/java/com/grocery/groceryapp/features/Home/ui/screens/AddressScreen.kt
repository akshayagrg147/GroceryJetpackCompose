package com.grocery.groceryapp.features.Home.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.BottomNavigation.BottomNavItem
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.disableColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.redColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.seallcolor
import com.grocery.groceryapp.features.Spash.ui.viewmodel.AddressViewModal
import com.wajahatkarim3.compose.books.ui.model.PassingAddress

@Composable
fun addressScreen(
    address: PassingAddress,
    navController: NavHostController,
    context: Activity,
    viewModal: AddressViewModal = hiltViewModel()
) {

    val name = remember { mutableStateOf("") }
    val phonenumber = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val address1 = remember { mutableStateOf("") }
    val address2 = remember { mutableStateOf("") }
    val landmark = remember { mutableStateOf("") }

    val editcalled = !address.name.isNullOrEmpty()

    phonenumber.value = address.phone_number ?: ""
    pincode.value = address.pincode ?: ""
    address1.value = address.address1 ?: ""
    address2.value = address.address2 ?: ""
    landmark.value = address.landmark ?: ""


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text24_700(text = "Add Address", color = Color.Black)
                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }
                        CommonTextField(
                            text = name,
                            placeholder = stringResource(id = R.string.name)
                        ) {
                            state.value = it.length >= 3
                        }
                        if (!state.value)
                            Text14_400(
                                text = "Enter full name",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )

                    }
                }

                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }
                        CommonNumberField(
                            text = phonenumber,
                            placeholder = stringResource(id = R.string.phone_number)
                        ) {
                            state.value = it.length == 10
                        }
                        if (!state.value)
                            Text14_400(
                                text = "phone number should be 10 digit",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )

                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }
                        CommonNumberField(
                            text = pincode,
                            placeholder = stringResource(id = R.string.pincode)
                        ) {
                            state.value = it.length == 6

                        }
                        if (!state.value)
                            Text14_400(
                                text = "pin code should be 6 digit",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )
                    }
                }


                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }


                        CommonTextField(
                            text = address1,
                            placeholder = stringResource(id = R.string.address1)
                        ) {
                            state.value = it.length >= 6
                        }
                        if (!state.value)
                            Text14_400(
                                text = "Address Incomplete",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )

                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }
                        CommonTextField(
                            text = address2,
                            placeholder = stringResource(id = R.string.address2)
                        ) {
                            state.value = it.length >= 6
                        }
                        if (!state.value)
                            Text14_400(
                                text = "Alternative address Incomplete",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )


                    }
                }
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column() {
                        val state = remember {
                            mutableStateOf(false)
                        }
                        CommonTextField(
                            text = landmark,
                            placeholder = stringResource(id = R.string.landmark)
                        ) {
                            state.value = it.length >= 6
                        }
                        if (!state.value)
                            Text14_400(
                                text = "Landmark address Incomplete",
                                color = redColor,
                                modifier = Modifier.align(Alignment.End)
                            )

                    }

                }
                CommonButton(
                    text = "Submit Address",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (name.value.isNotEmpty() && phonenumber.value.isNotEmpty() && pincode.value.isNotEmpty() && address1.value.isNotEmpty() && address2.value.isNotEmpty() && landmark.value.isNotEmpty()) seallcolor else disableColor
                )
                {
                    if (name.value.isNotEmpty() && phonenumber.value.isNotEmpty() && pincode.value.isNotEmpty() && address1.value.isNotEmpty() && address2.value.isNotEmpty() && landmark.value.isNotEmpty()) {
                        if (editcalled) {
                            val response = viewModal.UpdateAddress(
                                AddressItems(address.id!!,
                                    name.value,
                                    phonenumber.value,
                                    Integer.parseInt(pincode.value),
                                    address1.value,
                                    address2.value,
                                    landmark.value
                                )
                            )
                            if (response.isCompleted) {
                                navController.navigate(BottomNavItem.AddressScreen.screen_route)
                            }
                        } else
                            viewModal.saveAddress(
                                AddressItems( address.id!!,
                                    name.value,
                                    phonenumber.value,
                                    Integer.parseInt(pincode.value),
                                    address1.value,
                                    address2.value,
                                    landmark.value,

                                )
                            )
                        if (name.value.isNotEmpty()) {
                            navController.navigate(BottomNavItem.AddressScreen.screen_route)
                        } else {
                            navController.navigate(ScreenRoute.CartScreen.route)
                        }
                        // navController.navigate(ScreenRoute.CartScreen.route)}

                    } else
                        Toast.makeText(context, "Please provide all details", Toast.LENGTH_LONG)
                            .show()

                }
            }
        }


    }


}