package com.grocery.groceryapp.Home.ui
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.grocery.groceryapp.Home.RecyclerViewItems.ItemEachRow
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text24_700

@Composable
fun CartScreen() {
    var search= remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text24_700(text = "MY Cart", color = Color.Black)
        }
        Box(
            modifier  = Modifier


                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .background(colorResource(id = R.color.green_700))
                .clip(RoundedCornerShape(10.dp)),



            ) {
            Text24_700(text = "Go to Checkout", color = Color.White,modifier  = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.Center))

        }

        LazyColumn(
            //  verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize().padding(bottom = 15.dp)
            // .verticalScroll(state = scrollState)

        ){

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
            items(5){
                ItemEachRow()
            }

        }





    }



}