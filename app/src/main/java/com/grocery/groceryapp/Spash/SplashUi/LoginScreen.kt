package com.grocery.groceryapp.Spash

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Spash.Modal.Country
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.ui.theme.bodyTextColor
import com.grocery.groceryapp.ui.theme.fadedTextColor
import com.grocery.groceryapp.ui.theme.headingColor
import com.grocery.groceryapp.ui.theme.titleColor
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun loginScreen(navController: NavHostController, context: Context) {
    val scope = rememberCoroutineScope()
    var filteredFavData: List<Country>
    val countries = remember { countryList(context) }
    val isCompleted= remember { mutableStateOf("") }


    val name= remember {
        mutableStateOf("")
    }
    val search= remember {
        mutableStateOf("")
    }
    val code= remember {
        mutableStateOf("")
    }
    val contactNum= remember {
        mutableStateOf("")
    }
    val email= remember {
        mutableStateOf("")
    }
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize(),Arrangement.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    TextField(
                        value = search.value,
                        onValueChange = {
                            search.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp)),
                        placeholder = {
                            Text14_400(
                                text = "Search Country",
                                color = bodyTextColor,
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Transparent,
                            trailingIconColor = titleColor,
                            backgroundColor = Color.White,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            if (search.value != "") {
                                IconButton(onClick = {
                                    search.value = ""
                                }) {
                                    Icon(
                                        Icons.Default.Close, contentDescription = "",
                                    )
                                }
                            }
                        },
                        leadingIcon = {
                            IconButton(onClick = {}) {
                                Icon(
                                    Icons.Default.Search, contentDescription = "",
                                )
                            }
                        },
                        singleLine = true,
                    )
                }
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {

                    val searchedText = search.value
                    filteredFavData = if (searchedText.isEmpty()) {
                        countries
                    } else {
                        val resultData = arrayListOf<Country>()
                        for (fav in countries) {
                            if (fav.name.lowercase(Locale.getDefault()).contains(
                                    searchedText.lowercase(Locale.getDefault())
                                )
                            ) {
                                resultData.add(fav)
                            }
                        }
                        resultData
                    }

                    items(filteredFavData) { country ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    code.value = country.dialCode
                                    scope.launch {
                                        modalBottomSheetState.hide()
                                    }

                                }
                                .padding(10.dp)
                        ) {
                            Text(
                                text = localeToEmoji(countryCode = country.code)
                            )

                            Text(
                                text = country.name,
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .weight(2f)
                            )

                            Text(
                                text = country.dialCode,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }

                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ){
        Column(modifier = Modifier
            .fillMaxSize().fillMaxSize()
            .padding(10.dp),Arrangement.Center) {
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
                text =  "Log In", color = headingColor,
                modifier = Modifier.padding(bottom = 10.dp, top = 25.dp)
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
            {
                CommonTextField(
                    text = code,
                    placeholder = "",
                    keyboardType = KeyboardType.Phone,
                    modifier = Modifier.width(50.dp),
                    trailingIcon = R.drawable.ic_baseline_arrow_drop_down_24,
                    iconColor = Color.Unspecified,
                    size = 12.dp,
                    enable = false

                ) {
                    scope.launch {  modalBottomSheetState.show() }
                }

                CommonTextField(
                    text = contactNum  ,
                    placeholder = stringResource(id = R.string.phone_number),
                    keyboardType = KeyboardType.Phone,
                    modifier = Modifier.padding(start = 10.dp)
                )

            }


            Column(Modifier.fillMaxWidth()) {

                Row(verticalAlignment = Alignment.Bottom) {
                    CommonButton(
                        text = "Log In",
                        backgroundColor =  titleColor ,
                        color =  Color.White

                    ) {
                        navController.navigate(ScreenRoute.LocateMeScreen.route)
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)

                ) {
                    Text14_400(
                        text = "Don't have an account?", color = fadedTextColor,
                    )
                    Text14_400(
                        text = "Sign Up",
                        color = titleColor,
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .clickable {
                                navController.navigate(ScreenRoute.SignUpScreen.route)
                            }
                    )

                }
            }




        }
    }


}