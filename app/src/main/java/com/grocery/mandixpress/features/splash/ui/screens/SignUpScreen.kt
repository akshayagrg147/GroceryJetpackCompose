package com.grocery.mandixpress.features.splash.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.R
import com.grocery.mandixpress.features.splash.ui.viewmodel.RegisterLoginViewModal
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.features.splash.splashnavigation.ScreenRoute
import com.grocery.mandixpress.features.splash.ui.viewmodel.RegisterEvent
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.clickable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import com.grocery.mandixpress.common.Utils.Companion.extractSixDigitNumber
import com.grocery.mandixpress.features.home.ui.screens.HomeActivity
import kotlinx.coroutines.launch

val emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"


@Composable
fun SignUpScreen(
    navController: NavHostController,
    context: Context,
    mobileNumber:String,
    pincode:String,
    viewModal: RegisterLoginViewModal = hiltViewModel()
) {
    var societies by remember { mutableStateOf(listOf<String>()) }
    var isDialog by remember { mutableStateOf(false) }

    if (isDialog)
        CommonProgressBar()

    val contactNum = remember { mutableStateOf<String>(mobileNumber) }
    val pincode = remember { mutableStateOf<String>(pincode) }
    val name = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }
    var selectedSociety by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val flatNumber = remember { mutableStateOf("") }


    LaunchedEffect(key1 = Unit) {

        // Collect societyEventFlow
        launch {
            viewModal.onEvent(
                RegisterEvent.AllSocietyEventFlow
            )

            viewModal.societyEventFlow.collectLatest {
                isDialog = when (it) {
                    is ApiState.Success -> {
                        societies = it.data.list?.map { society -> society.name!! }!!
                        Log.d("fetchingsos", "Fetching societies" + societies)
                        false
                    }
                    is ApiState.Failure -> {
                        context.showMsg("something went wrong")
                        false
                    }
                    ApiState.Loading -> true
                }
            }
        }

        // Collect registerEventFlow
        launch {
            viewModal.registerEventFlow.collectLatest {
                isDialog = when (it) {
                    is ApiState.Success -> {
                        viewModal.setMobileNumber(contactNum.value)
                        viewModal.setJwtToken(it.data.token ?: "")
                        viewModal.savePinCode(pincode.toString())
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                        false
                    }
                    is ApiState.Failure -> {
                        context.showMsg("something went wrong")
                        false
                    }
                    ApiState.Loading -> true
                }
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageHeader()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .clip(RoundedCornerShape(46.dp))
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                // Text Fields
                CommonTextField(text = name, placeholder = stringResource(id = R.string.name))
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = contactNum,
                    enable = false,
                    placeholder = stringResource(id = R.string.phone_number),
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = pincode,
                    enable = false,
                    placeholder = stringResource(id = R.string.pincode),
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = email,
                    placeholder = stringResource(id = R.string.email),
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Society Spinner (Dropdown Menu)
                Column {
                    OutlinedTextField(
                        value = selectedSociety,
                        onValueChange = { selectedSociety = it },
                        readOnly = true,
                        label = { Text("Select Society") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Arrow",
                                Modifier.clickable { expanded = !expanded }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        societies.forEach { society ->
                            DropdownMenuItem(onClick = {
                                selectedSociety = society
                                expanded = false
                            }) {
                                Text(society)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Flat Number TextField
                if (selectedSociety.isNotEmpty()) {
                    CommonTextField(
                        text = flatNumber,
                        placeholder = "Flat Number",
                        keyboardType = KeyboardType.Text
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Sign Up Button
                Column(Modifier.fillMaxWidth()) {
                    CommonButton(
                        text = stringResource(id = R.string.sign_up),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = email.value.matches(emailPattern.toRegex()) && name.value.isNotEmpty(),
                        color = Color.White
                    ) {
                        if (it) {
                            viewModal.onEvent(
                                RegisterEvent.RegisterEventFlow(
                                    RegisterLoginRequest(
                                        email.value, name.value, contactNum.value
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }



}