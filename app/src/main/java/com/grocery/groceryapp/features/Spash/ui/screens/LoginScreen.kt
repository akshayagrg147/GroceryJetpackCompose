package com.grocery.groceryapp.features.Spash

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.CommonProgressBar
import com.grocery.groceryapp.common.OtpView
import com.grocery.groceryapp.features.Home.ui.ui.theme.fadedTextColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.headingColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import com.grocery.groceryapp.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun loginScreen(
    navController: NavHostController, context: Activity,sharedPreferences:sharedpreferenceCommon,
    viewModal: LoginViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    var isCompleted by remember { mutableStateOf(false) }
    var isDialog by remember { mutableStateOf(false) }
    var mobile by remember { mutableStateOf("") }

    var otp by remember {
        mutableStateOf("")
    }

    if (isDialog)
        CommonProgressBar()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        if (!isCompleted)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Image(
                    painter = painterResource(id = R.drawable.ornge_carrot),

                    contentDescription = "splash image",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .align(alignment = Alignment.CenterHorizontally)

                )

                Text14_h1(
                    text = "Log In", color = headingColor,
                    modifier = Modifier.padding(bottom = 10.dp, top = 25.dp)
                )
                OutlinedTextField(value = mobile,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                    mobile = it
                }, label = { Text(text = "+91") }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(Modifier.fillMaxWidth()) {

                    Row(verticalAlignment = Alignment.Bottom) {
                        CommonButton(
                            text = "Send Otp",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            backgroundColor = titleColor,
                            color = Color.White

                        ) {
                            scope.launch(Dispatchers.Main) {
                                viewModal.createUserWithPhone(
                                    "+91${mobile}",
                                    context
                                ).collect {
                                    when (it) {
                                        is ApiState.Success -> {
                                            isDialog = false
                                            context.showMsg(it.data)
                                            isCompleted = true
                                        }
                                        is ApiState.Failure -> {
                                            isDialog = false
                                            context.showMsg(it.msg.toString())
                                        }
                                        ApiState.Loading -> {
                                            isDialog = true
                                        }
                                    }
                                }
                            }

                        }
                    }

                }

            }
        else{
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text12_body1(
                    text = "Please Enter 6 digit code sent on +91${mobile}", color = fadedTextColor,
                )
                Spacer(modifier = Modifier.height(10.dp))
                OtpView(otpText = otp) {
                    otp = it
                }
                Spacer(modifier = Modifier.height(20.dp))
                CommonButton(
                    text = "Submit Otp",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    backgroundColor = titleColor,
                    color = Color.White

                ) {
                    scope.launch(Dispatchers.Main) {
                        viewModal.signWithCredential(
                            otp
                        ).collect {
                            when (it) {
                                is ApiState.Success -> {
                                    isDialog = false
                                    context.showMsg(it.data)
                                    viewModal.checkMobileNumberExist("+91${mobile}")
                                    var response=  viewModal.resp.value
                                    if(response.statusCode==200)
                                    {if(response.isMobileExist==true)
                                    { sharedPreferences.setJwtToken(response.jwtToken!!)
                                        sharedPreferences.setMobileNumber("+91${mobile}")
                                        navController.navigate(ScreenRoute.LocateMeScreen.route)}
                                    else{
                                        navController.currentBackStackEntry?.arguments?.apply {
                                            putString("mobileNumber","+91${mobile}")
                                        }
                                        navController.navigate(ScreenRoute.SignUpScreen.route)
                                    }
                                    }
                                    else {
                                        navController.currentBackStackEntry?.arguments?.apply {
                                            putString("mobileNumber", "+91${mobile}")
                                        }
                                    }

                                }
                                is ApiState.Failure -> {
                                    isDialog = false
                                    context.showMsg(it.msg.toString())
                                }
                                ApiState.Loading -> {
                                    isDialog = true
                                }
                            }
                        }
                    }
                }

            }
        }


    }


}

