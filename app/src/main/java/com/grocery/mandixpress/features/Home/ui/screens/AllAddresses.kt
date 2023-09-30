package com.grocery.mandixpress.features.Home.ui.screens

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.grocery.mandixpress.DashBoardNavRouteNavigation.DashBoardNavRoute
import com.grocery.mandixpress.R
import com.grocery.mandixpress.Utils.*
import com.grocery.mandixpress.common.AppCustomChips
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems

import com.grocery.mandixpress.features.Home.ui.viewmodal.HomeAllProductsViewModal
import com.grocery.mandixpress.data.modal.PassingAddress
import com.grocery.mandixpress.features.Home.ui.ui.theme.*


@Composable
fun AddressFiled(data: AddressItems, selectedIndex: MutableState<Int>,call:(AddressItems)->Unit) {

    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (selectedIndex.value == data.id.toInt()) Color.LightGray else whiteColor // Set the border color based on the condition
        ),
       modifier = Modifier
            .fillMaxWidth()


            .clip(RoundedCornerShape(1.dp, 1.dp, 1.dp, 1.dp))
            .clickable {
                selectedIndex.value = data.id.toInt()

            }

    ){
        Row( modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
Box(modifier = Modifier.background(buttonDisableColor).padding(10.dp)) {
    Icon(
        painter = painterResource(id = R.drawable.homeicon), // Replace with your icon resource
        contentDescription = "Location Icon",
        tint = Color.Unspecified,
        modifier = Modifier.size(17.dp)
    )
    
}

            Column(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)) {
                Text12_h1(text = "Home:"+data.customer_name, modifier = Modifier, color = headingColor)
                Text10_h2(text = "${data.Address1},${data.Address2}, ${data.PinCode},${data.LandMark}", modifier = Modifier.fillMaxWidth(), color = bodyTextColor)

            }

            Image(
                painter = painterResource(id = com.google.android.material.R.drawable.material_ic_edit_black_24dp),

                contentDescription = "",
                modifier = Modifier
                    .padding()
                    .width(20.dp)
                    .height(20.dp)

                    .clickable { call(data) }

            )
        }
    }



}
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun AllAddress(navHostController: NavHostController,context: Context) {

    var selected by remember { mutableStateOf(0) }

    Column(modifier = Modifier.background(color = lineColor)) {
        CommonHeader(text = "Address", color = Color.Black) {
            navHostController.popBackStack()
        }
        FlowRow {
            listOf<String>("All Address", "Add Address").forEachIndexed { index, s ->
                AppCustomChips(
                    selected = index == selected,
                    index = index,
                    title = s,
                    unSelectedBackgroundColor = lineColor,
                    borderStroke = BorderStroke(1.dp,Color.LightGray)
                ) { data ->
                    selected = data
                }
            }
        }


        if (selected == 0)
            allAddress(navHostController)
        else if (selected == 1)
        {
            addressScreen(address = PassingAddress(), navController = navHostController)
        }
    }




}
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun allAddress(navHostController: NavHostController,viewModal: HomeAllProductsViewModal = hiltViewModel()){
    var selectedIndex = remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit){
        viewModal.getAddress()
    }
    LazyColumn(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxSize()
        // .height(260.dp)
    ) {
        item {
            if(viewModal.list.value.isEmpty())
                noAddressAvailable()


        }
        items(viewModal.list.value) { item ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModal.deleteAddress(item.id.toInt())
            }

            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier
                    .padding(vertical = Dp(1f)),
                directions = setOf(
                    DismissDirection.EndToStart
                ),
                dismissThresholds = { direction ->
                    FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                },
                background = {

                    val color = when (dismissState.dismissDirection) {
                        DismissDirection.EndToStart -> lightred
                        null -> Color.Transparent
                        else -> {Color.Transparent}
                    }
                    val direction = dismissState.dismissDirection

                    if (direction == DismissDirection.StartToEnd) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Text11_body2(
                                    text = "Move to Edit",

                                    color = Color.White
                                )
                            }

                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.heightIn(5.dp))
                                Text11_body2(
                                    text = "Move to Delete",
                                    color = Color.LightGray
                                )

                            }
                        }
                    }
                },
                dismissContent = {

                    Card(
                        shape = RoundedCornerShape(10.dp),
                        elevation = animateDpAsState(
                            if (dismissState.dismissDirection != null) 4.dp else 0.dp
                        ).value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterVertically).background(color=lineColor).padding(10.dp)
                            .animateItemPlacement(animationSpec = tween(durationMillis = 600))
                    ) {
                        AddressFiled(item, selectedIndex, call = {
                            val passing= PassingAddress(it.id,it.customer_name,"ak@gmail.com",it.customer_PhoneNumber,it.PinCode.toString(),it.LandMark,it.Address1,it.Address2)

                            navHostController.currentBackStackEntry?.arguments?.putParcelable("address", passing)
                            navHostController.navigate(DashBoardNavRoute.AddnewAddressScreen.screen_route)


                        })
                    }
                })
        }

    }}




@Composable
fun noAddressAvailable(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(250.dp))
        Text12_body1(text = "No Address available")


    }

}
