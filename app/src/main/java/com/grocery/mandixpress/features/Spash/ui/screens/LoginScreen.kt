package com.grocery.mandixpress.features.Spash

import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.R
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.common.OtpView
import com.grocery.mandixpress.features.Home.ui.ui.theme.fadedTextColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.titleColor
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.mandixpress.features.Spash.ui.viewmodel.LoginEvent
import com.grocery.mandixpress.features.Spash.ui.viewmodel.LoginViewModel
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
    val mobileRegisterResponse by viewModal.loginResponse.collectAsState()
    var loginResponse by remember { mutableStateOf(false) }

    var otp by remember {
        mutableStateOf("")
    }

    if (isDialog)
        CommonProgressBar("Hold for a moment")

    LaunchedEffect(key1 = loginResponse){
        Log.d("loginViewModal", "checkMobileNumberExist: not exist")
        if(loginResponse)
        viewModal.onEvent(LoginEvent.LoginResponse("+91${mobile}"))

}


    LaunchedEffect(key1 = mobileRegisterResponse) {
        if (mobileRegisterResponse.isLoading) {
            Log.d("loginViewModal", "checkMobileNumberExist: loading")
        }
        else if (mobileRegisterResponse.data != null) {
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

        }
        else if (mobileRegisterResponse.error.isNotEmpty()) {
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
       // CurvedBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),

//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),

                contentDescription = "splash image",
                modifier = Modifier
                    .fillMaxWidth().
                        size(100.dp)

                    .align(Alignment.CenterHorizontally)

            )
            Text18_h1(
                text = "Shop Smart, Shop Quick", color = headingColor,
                modifier = Modifier.padding(bottom = 5.dp, top = 15.dp, start = 10.dp).fillMaxWidth().align(Alignment.CenterHorizontally)
            )

            if (!isCompleted)
            {
                Text12_body1(
                    text = "Log in or Sign Up. ", color = headingColor,
                    modifier = Modifier.padding(bottom = 5.dp, top = 2.dp, start = 10.dp)
                )

                OutlinedTextField(value = mobile,


                    keyboardOptions = KeyboardOptions .Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    onValueChange = {
                        mobile = it
                    },
                    leadingIcon =  { Text("+91") },
                    label = { Text(text = "Enter Mobile  no.") }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                , shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Column(Modifier.fillMaxWidth()) {

                    Row(verticalAlignment = Alignment.Bottom) {
                        CommonButton(
                            text = "Continue",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp,end=10.dp),
                            backgroundColor = titleColor,
                            color = Color.White,
                            enabled = mobile.length==10

                        ) {
                            if(mobile.length!=10)
                                return@CommonButton
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

                    Text12_body1(
                        text = "Please Enter 6 digit code sent on +91${mobile}", color = fadedTextColor,
                        modifier = Modifier.padding( start = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OtpView(otpText = otp, modifier = Modifier.padding( start = 10.dp)) {
                        otp = it
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                resendTimer(){
                    if(it){
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
                    CommonButton(
                        text = "Submit Otp",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        backgroundColor = titleColor,
                        color = Color.White,
                        enabled = otp.length==6


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
                                            Log.d("errormsg","${it.msg}")
                                            context.showMsg("something went wrong")
                                        }
                                        ApiState.Loading -> {
                                            isDialog = true
                                        }
                                    }
                                }
                            }

                    }


            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ){
                Text10_h2(text = "By Continuing in our T&C",  modifier = Modifier.align(
                    Alignment.BottomCenter))
            }


        }

    }



}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun resendTimer(call:(Boolean)->Unit) {
        var otp by remember { mutableStateOf("") }
        var countdown by remember { mutableStateOf(30) } // Initial countdown time

        val timerState by rememberUpdatedState(countdown)
        var isTimerActive by remember { mutableStateOf(true) }

        // Create a CountDownTimer
        val countDownTimer = remember {
            object : CountDownTimer((timerState * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    countdown = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    countdown = 0
                    isTimerActive = false
                }
            }
        }
    LaunchedEffect(isTimerActive) {
        if (isTimerActive)
        countDownTimer.start()
        else {
            countDownTimer.cancel()
        }

    }

    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (countdown > 0) {
                Text(
                    text = "OTP will expire in $countdown seconds",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray
                )
            } else {
                Text12_h1(
                    text = "Resend OTP",
                    color = headingColor,
                    modifier = Modifier.clickable{
                        countdown = 30
                        isTimerActive=true
                        call(true)

                    }
                )
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

