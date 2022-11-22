package com.grocery.groceryapp.features.Spash

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grocery.groceryapp.data.modal.RegisterLoginRequest
import com.grocery.groceryapp.R
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.features.Spash.ui.viewmodel.RegisterLoginViewModal
import com.grocery.groceryapp.features.Spash.domain.Modal.Country
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.common.CommonProgressBar
import com.grocery.groceryapp.features.Home.ui.ui.theme.headingColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import com.grocery.groceryapp.features.Spash.SplashNavigation.ScreenRoute

@OptIn(ExperimentalMaterialApi::class)
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
        mutableStateOf(navController.previousBackStackEntry?.arguments?.getString("mobileNumber")!!)
    }
    val name = remember {
        mutableStateOf("")
    }
    val search = remember {
        mutableStateOf("")
    }
    val code = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .fillMaxSize(), verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ornge_carrot),

                contentDescription = "splash image",
                modifier = Modifier
                    .padding(top = 150.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            )
            Text24_700(
                text = "Sign Up", color = headingColor,
                modifier = Modifier.padding(bottom = 10.dp, top = 25.dp)
            )
            Column(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    CommonTextField(text = name, placeholder = stringResource(id = R.string.name))
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                )
                {


                    CommonTextField(
                        text = contactNum,
                        placeholder = stringResource(id = R.string.phone_number),
                        keyboardType = KeyboardType.Phone,
                        modifier = Modifier.padding(start = 10.dp)
                    )

                }

                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    CommonTextField(
                        text = email,
                        placeholder = "E Mail",
                    )
                }
                Column(Modifier.fillMaxWidth()) {

                    Row {
                        CommonButton(
                            text = "Sign Up",
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = titleColor,
                            color = Color.White

                        ) {
                            isDialog=true
                            val res = viewModal.registrationGetResponse.value


                            viewModal.setData(
                                RegisterLoginRequest(
                                email.value,name.value,contactNum.value
                            )
                            )
                            viewModal.getRegistration()
                            if(res.statusCode == 200)
                            {  isDialog=false
                                Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                                sharedPreferences.setJwtToken(res.token!!)
                                navController.navigate(ScreenRoute.LocateMeScreen.route)
                            }
                            else{
                                isDialog=false
                                Toast.makeText(context, res.message, Toast.LENGTH_SHORT).show()
                            }


                        }
                    }


                }

            }


        }




}