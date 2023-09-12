package com.grocery.mandixpress.features.Spash

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.R
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.ui.viewmodel.RegisterLoginViewModal
import com.grocery.mandixpress.features.Spash.domain.Modal.Country
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.CommonProgressBar
import com.grocery.mandixpress.features.Home.ui.ui.theme.headingColor
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.mandixpress.features.Spash.ui.viewmodel.RegisterEvent
import kotlinx.coroutines.flow.collectLatest

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
    context: Context, sharedPreferences: sharedpreferenceCommon,
    viewModal: RegisterLoginViewModal = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var filteredFavData: List<Country>
    val countries = remember { countryList(context) }
    val isCompleted = remember { mutableStateOf("") }
    var isDialog by remember { mutableStateOf(false) }

    if (isDialog)
        CommonProgressBar()

    val contactNum = remember {
        mutableStateOf(
            navController.previousBackStackEntry?.arguments?.getString("mobileNumber")
                ?: ""
        )
    }
    val name = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }


    LaunchedEffect(key1 = Unit){
        viewModal.registerEventFlow.collectLatest {
        isDialog  =  when(it){
                is ApiState.Success ->{
                    sharedPreferences.setJwtToken(it.data.token!!)
                    navController.navigate(ScreenRoute.LocateMeScreen.route)
                    false
                }

                is ApiState.Failure -> {
                    context.showMsg(it.msg.message.toString())
                    false
                }
                ApiState.Loading -> true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray) // Add a background color to the entire screen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
               // Apply rounded corners with a radius of 16.dp

               , // Increase the padding for better spacing
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally // Align the content to the center horizontally
        ) {
            PageHeader()

            Column(
                modifier = Modifier.fillMaxSize().background(Color.White)
                    .clip(RoundedCornerShape(46.dp))
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = headingColor,
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp, start = 5.dp)
                )
                CommonTextField(text = name, placeholder = stringResource(id = R.string.name))
                Spacer(modifier = Modifier.height(16.dp)) // Add some vertical spacing between the rows
                CommonTextField(
                    text = contactNum,
                    enable = false,
                    placeholder = stringResource(id = R.string.phone_number),
                    keyboardType = KeyboardType.Phone
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonTextField(
                    text = email,
                    placeholder = stringResource(id = R.string.email),
                )
                Spacer(modifier = Modifier.height(32.dp)) // Add some extra spacing at the bottom
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