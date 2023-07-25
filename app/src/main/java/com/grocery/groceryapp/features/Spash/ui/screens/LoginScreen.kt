package com.grocery.groceryapp.features.Spash

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
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
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginEvent
import com.grocery.groceryapp.features.Spash.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
    val mobileRegisterResponse by viewModal.loginResponse.collectAsState()
    var loginResponse by remember { mutableStateOf(false) }

    var otp by remember {
        mutableStateOf("")
    }

    if (isDialog)
        CommonProgressBar()

    SideEffect{
        Log.d("loginViewModal", "checkMobileNumberExist: not exist")
        if(loginResponse)
        viewModal.onEvent(LoginEvent.LoginResponse("+91${mobile}"))

}


    LaunchedEffect(key1 = mobileRegisterResponse) {
        if (mobileRegisterResponse.isLoading) {
            Log.d("loginViewModal", "checkMobileNumberExist: loading")
        } else if (mobileRegisterResponse.data != null) {
            Log.d("loginViewModal", "checkMobileNumberExist: loadinged")
            isDialog = false

            if (mobileRegisterResponse.data!!.isMobileExist == true) {
                Log.d("loginViewModal", "checkMobileNumberExist: loadinged rr")
                sharedPreferences.setJwtToken(mobileRegisterResponse.data!!.jwtToken!!)
                sharedPreferences.setMobileNumber("+91${mobile}")
                navController.navigate(ScreenRoute.LocateMeScreen.route)
            } else {
                Log.d("loginViewModal", "checkMobileNumberExist: loardinged rr")
                navController.currentBackStackEntry?.arguments?.apply {
                    putString("mobileNumber", "+91${mobile}")
                }
                navController.navigate(ScreenRoute.SignUpScreen.route) {
                    popUpTo(ScreenRoute.SplashScreen.route) {
                        inclusive = true
                    }


                }
            }

        } else if (mobileRegisterResponse.error.isNotEmpty()) {
            isDialog = false
            Log.d("loginViewModal", "checkMobileNumberExist: error")
            Toast.makeText(context, mobileRegisterResponse.error, Toast.LENGTH_SHORT).show()
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.TopCenter
    ){
        CurvedBackground()
        Column(
            modifier = Modifier

                .padding(20.dp)
        ) {
            if (!isCompleted)
            {
                Image(
                    painter = painterResource(id = R.drawable.ornge_carrot),

                    contentDescription = "splash image",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)


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
                            color = Color.White,
                            enabled = mobile.length==10

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
                        color = Color.White,
                        enabled = otp.isNotEmpty()


                    ) {


                        scope.launch(Dispatchers.Main) {
                                viewModal.signWithCredential(
                                    otp
                                ).collect {
                                    when (it) {
                                        is ApiState.Success -> {
                                            context.showMsg(it.data)

                                            loginResponse=true

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



}

@Composable
fun CurvedBackground() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawPath(
            path = Path().apply {
                moveTo(0f, size.height)
                lineTo(0f, size.height * 0.7f)
                quadraticBezierTo(
                    size.width * 0.25f,
                    size.height * 0.6f,
                    size.width * 0.5f,
                    size.height * 0.7f
                )
                quadraticBezierTo(
                    size.width * 0.75f,
                    size.height * 0.8f,
                    size.width,
                    size.height * 0.7f
                )
                lineTo(size.width, size.height)
                close()
            },
            color = Color.Blue,
        )
    }
}

