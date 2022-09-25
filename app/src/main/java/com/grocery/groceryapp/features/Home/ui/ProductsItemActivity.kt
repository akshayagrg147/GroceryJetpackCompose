package com.grocery.groceryapp.features.Home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.grocery.groceryapp.features.Home.Navigator.gridItems
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.*
import com.grocery.groceryapp.features.Home.ui.ui.theme.blackColor
import com.grocery.groceryapp.features.Home.ui.ui.theme.titleColor
import kotlinx.coroutines.launch

class ProductItemsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                  productItems()
                }
            }
        }
    }


}

@Composable
fun Greeting4(name: String) {
    Text(text = "Hello $name!")
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun productItems() {
    val scope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)) {
                    CategoriesCheckBox()
                    BrandCheckBox()

                }
                CommonButton(
                    text = "Apply Filter",
                    Modifier.fillMaxWidth().padding(10.dp).align(Alignment.BottomCenter),

                    backgroundColor =  titleColor ,
                    color =  Color.White

                )

            }

        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp, topEnd = 20.dp
        )
    ){
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (l1,l2) = createRefs()
            Box(modifier = Modifier
                .fillMaxWidth().padding(10.dp)
                .constrainAs(l1) {
                    top.linkTo(parent.top)

                }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_arow_back) ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 5.dp)
                    ,
                    tint = blackColor
                )
                Text18_600(text = "All Items", color = Color.Black, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 5.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.filter) ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 5.dp)
                        .align(Alignment.TopEnd)
                        .clickable {  scope.launch {  modalBottomSheetState.show() } }
                    ,
                    tint = blackColor,
                )

            }
//            LazyColumn(
//                modifier = Modifier
//                    .background(MaterialTheme.colors.background)
//                    .constrainAs(l2) {
//                        top.linkTo(l1.bottom)
//
//                    },
//                contentPadding = PaddingValues(top = 26.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ){
//                val list = mutableListOf<String>("A", "B", "C", "D","E","f")
//                gridItems(
//                    data = list,
//                    columnCount = 2,
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                ) { itemData ->
//                    SellingItems(data)
//                }
//            }

        }
    }



}

@Composable
fun BrandCheckBox() {
    Text18_600(text = "Brands")
    Spacer(modifier = Modifier.height(5.dp))
    val checkedState = remember { mutableStateOf(true) }

    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Eggs", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Noodles", modifier = Modifier.padding(16.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Chips", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Pastas", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
}

@Composable
fun CategoriesCheckBox() {
    Text18_600(text = "Categories")
    Spacer(modifier = Modifier.height(5.dp))
    val checkedState = remember { mutableStateOf(true) }

    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Individual Collections", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "cacola", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Ifad", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
    Row {
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier.padding(start = 13.dp),
            onCheckedChange = { checkedState.value = it },
        )
        Text(text = "Kazi Farmas", modifier = Modifier.padding(start = 13.dp,top=10.dp))
    }
}
