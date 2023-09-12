package com.grocery.mandixpress.features.Home.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.data.modal.PassingAddress
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems
import com.grocery.mandixpress.features.Home.ui.ui.theme.disableColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.redColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.seallcolor
import com.grocery.mandixpress.features.Home.ui.viewmodal.AddressViewModal

@Composable
fun addressScreen(
    address: PassingAddress,
    navController: NavHostController,

    viewModal: AddressViewModal = hiltViewModel()
) {
val context= LocalContext.current.getActivity()
    val name = remember { mutableStateOf("") }
    val phonenumber = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val address2 = remember { mutableStateOf("") }
    val landmark = remember { mutableStateOf("") }

    val editcalled = !address.name.isNullOrEmpty()

    phonenumber.value = address.phone_number ?: ""
    pincode.value = address.pincode ?: ""
    city.value = address.address1 ?: ""
    address2.value = address.address2 ?: ""
    landmark.value = address.landmark ?: ""


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column (){
                    val namestate = remember {
                        mutableStateOf(true)
                    }
                    CommonTextField(
                        text = name,
                        placeholder = stringResource(id = com.grocery.mandixpress.R.string.name)
                    ) {
                        namestate.value = it.length >= 3
                    }
                    if (!namestate.value)
                        Text12_body1(
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
                    val phonenumberstate = remember {
                        mutableStateOf(true)
                    }
                    CommonNumberField(
                        text = phonenumber,
                        placeholder = stringResource(id =com.grocery.mandixpress. R.string.phone_number)
                    ) {
                        phonenumberstate.value = it.length == 10
                    }
                    if (!phonenumberstate.value)
                        Text12_body1(
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
                    val pincodelocal = remember {
                        mutableStateOf(true)
                    }
                    CommonNumberField(
                        text = pincode,
                        placeholder = stringResource(id = com.grocery.mandixpress.R.string.pincode)
                    ) {
                        pincodelocal.value = it.length == 6

                        when (it) {
                            "136027" -> {
                                city.value="kaithal"
                            }
                            "122505" -> {
                                city.value="gurgaon"
                            }
                            else -> {
                                city.value=""
                            }
                        }

                    }
                    if (!pincodelocal.value) {
                        Text12_body1(
                            text = "pin code should be 6 digit",
                            color = redColor,
                            modifier = Modifier.align(Alignment.End)
                        )

                    }

                            if(pincode.value== "136027" || pincode.value== "122505"|| pincode.value== ""){

                            }else{
                                if (pincode.value.length>5){
                                    Text12_body1(
                                        text = "Not available in your city",
                                        color = redColor,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }

                            }







                }
            }


            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column() {
                    val address = remember {
                        mutableStateOf(true)
                    }


                    CommonTextField(
                        text = address2,
                        placeholder = stringResource(id = com.grocery.mandixpress.R.string.address1)
                    ) {
                        address.value = it.length >= 3
                    }
                    if (!address.value)
                        Text12_body1(
                            text = "State Incomplete",
                            color = redColor,
                            modifier = Modifier.align(Alignment.End)
                        )

                }
            }
            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column() {

                    CommonTextFieldNonEditable(
                        text = city,
                        placeholder = stringResource(id = com.grocery.mandixpress.R.string.city)
                    ) {

                    }



                }
            }
            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Column() {
                    val landmarkstate = remember {
                        mutableStateOf(true)
                    }
                    CommonTextField(
                        text = landmark,
                        placeholder = stringResource(id =com.grocery.mandixpress. R.string.landmark)
                    ) {
                        landmarkstate.value = it.length >= 6
                    }
                    if (!landmarkstate.value)
                        Text12_body1(
                            text = "Landmark address Incomplete",
                            color = redColor,
                            modifier = Modifier.align(Alignment.End)
                        )

                }

            }
            CommonButton(
                text = "Submit Address",
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = if (name.value.isNotEmpty() && phonenumber.value.isNotEmpty() && pincode.value.isNotEmpty() && city.value.isNotEmpty() && address2.value.isNotEmpty() && landmark.value.isNotEmpty()) seallcolor else disableColor
            )
            {
                if (name.value.isNotEmpty() && phonenumber.value.isNotEmpty() && pincode.value.isNotEmpty() && city.value.isNotEmpty() && address2.value.isNotEmpty() && landmark.value.isNotEmpty()) {
                    if (editcalled) {
                        val response = viewModal.UpdateAddress(
                            AddressItems(address.id!!,
                                name.value,
                                phonenumber.value,
                                Integer.parseInt(pincode.value),
                                city.value,
                                address2.value,
                                landmark.value
                            )
                        )
                        if (response.isCompleted) {
                            navController.navigate(DashBoardNavRoute.AddressScreen.screen_route){
                                popUpTo(DashBoardNavRoute.AddressScreen.screen_route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    else
                        viewModal.saveAddress(
                            AddressItems( address.id!!,
                                name.value,
                                phonenumber.value,
                                Integer.parseInt(pincode.value),
                                city.value,
                                address2.value,
                                landmark.value,

                            )
                        )
                    navController.navigate(DashBoardNavRoute.AddressScreen.screen_route){
                        popUpTo(DashBoardNavRoute.AddressScreen.screen_route) {
                            inclusive = true
                        }
                    }


                } else
                    context?.showMsg("Please provide all details")


            }
        }


    }



}

