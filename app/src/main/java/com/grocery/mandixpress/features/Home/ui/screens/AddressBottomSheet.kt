package com.grocery.mandixpress.features.Home.ui.screens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.CommonTextField
import com.grocery.mandixpress.Utils.Text12_body1
import com.grocery.mandixpress.Utils.Text12_h1
import com.grocery.mandixpress.Utils.Text16_h1
import com.grocery.mandixpress.features.Home.ui.viewmodal.AddressViewModal
import com.grocery.mandixpress.features.Home.ui.viewmodal.CartItemsViewModal
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun PredictionItem(prediction: AutocompletePrediction,click:(String)->Unit) {
    Text12_body1(text = prediction.getFullText(null).toString(), modifier = Modifier.padding(8.dp).clickable{
        click(prediction.getFullText(null).toString())
    })
}

@OptIn( ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CurvedBottomSheetWithButton(viewModel: AddressViewModal = hiltViewModel(),) {
    val placesClient: PlacesClient = Places.createClient(LocalContext.current)

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val address = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current


    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth().requiredHeight(400.dp)

            ) {

                // Curved background for the sheet
                Box(
                    modifier = Modifier.fillMaxWidth()
                        ,
                    contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.minus), // Replace with your image resource
                        contentDescription = "Cross Button",
                        modifier = Modifier, // Adjust the size as needed
                        contentScale = ContentScale.Fit
                    )


                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            )
                        )

                ) {
                    val predictions by viewModel.predictions.collectAsState()
                    Text16_h1(text = "Add Address", color = Color.Black, modifier = Modifier.padding(start = 20.dp,top=20.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    // Text inside the curved background
                    CommonTextField(
                        trailingIcon= com.bumptech.glide.R.drawable.abc_ic_search_api_material,
                        iconColor=Color.LightGray,
                        text = address,
                        placeholder = stringResource(id = R.string.address),
                        modifier = Modifier.padding(start = 10.dp,end=10.dp)
                    ) {
                        viewModel.searchAddress( it,placesClient)
                        scope.launch {
                            viewModel.predictions.collect{

                                //    Log.d("checkdata","${it.get(0).getFullText(null)}")


                                }
                            }

                       // namestate.value = it.length >= 3
                    }

                    LazyColumn {
                               items(predictions) {
                                        prediction ->
                                    Log.d("checkdata","${prediction.getFullText(null)}")

                                    PredictionItem(prediction = prediction){

                                    }
                                }



                        }
                }



            }
        }
    ) {
        // Content of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Open Bottom Sheet button
            Button(
                onClick = {
                    scope.launch {
                        bottomSheetState.show()
                    }

                }
            ) {
                Text12_body1(text = "Open Bottom Sheet")
            }
            println("sizeandvalue ${viewModel.predictions.collectAsState()}")

        }
    }
}


