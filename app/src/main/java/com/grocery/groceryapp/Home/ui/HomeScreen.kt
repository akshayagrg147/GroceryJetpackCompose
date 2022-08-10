package com.grocery.groceryapp.Home.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.grocery.groceryapp.Home.RecyclerViewItems.SellingItems
import com.grocery.groceryapp.Home.RecyclerViewItems.offerItems
import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text16_700
import com.grocery.groceryapp.ui.theme.*

@Composable
fun homescreen() {
    var search= remember {
        mutableStateOf("")
    }
    LazyColumn(
        //  verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
        // .verticalScroll(state = scrollState)

    ){
      item{
          Column(modifier = Modifier.fillMaxSize()) {

              Image(
                  painter = painterResource(id = R.drawable.ornge_carrot),
                  contentDescription = "Carrot Icon",
                  alignment=Alignment.Center,


                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 10.dp)
                      .width(30.dp)
                      .height(30.dp)
                      .align(Alignment.CenterHorizontally)
              )
              Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                  Icon(
                     Icons.Default.LocationOn,
                      contentDescription = "",
                      modifier = Modifier
                          .size(20.dp)
                          ,
                      tint = redColor
                  )
                  Text16_700(
                      text = "kaithal,Haryana",
                      color = headingColor,
                  )
              }
              Spacer(modifier = Modifier.height(10.dp))

              TextField(
                  value = search.value,
                  shape = RoundedCornerShape(8.dp),


                  onValueChange = {
                      search.value = it
                  },
                  modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(24.dp))
                      .padding(start = 10.dp, end = 10.dp),
                  placeholder = {
                      Text14_400(
                          text = "Search Store",
                          color = bodyTextColor,
                      )
                  },

                  colors = TextFieldDefaults.textFieldColors(
                      focusedIndicatorColor = Color.Transparent,
                      unfocusedIndicatorColor = Color.Transparent,
                      cursorColor = Color.Transparent,
                      trailingIconColor = titleColor,
                      backgroundColor = greycolor,
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
              Row(modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 10.dp)) {
                  Text16_700(text = "Exclusive Offers" , color = Color.Black, modifier = Modifier
                      .weight(3f)
                      .padding(start = 10.dp),)
                  Text14_400("See all",color = seallcolor, modifier = Modifier
                      .weight(1f)
                      .padding(top = 5.dp, start = 20.dp))
              }
              LazyRow(
                  modifier = Modifier
                      .fillMaxWidth()
                  // .height(260.dp)
              ) {
                  val list = listOf(
                      "A", "B", "C", "D")

                  items(list) { data ->
                     // Column(modifier = Modifier.padding(10.dp)) {
                          offerItems()
                      //}



                  }}

              Row(modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 10.dp)) {
                  Text16_700(text = "Best Selling" , color = Color.Black, modifier = Modifier
                      .weight(3f)
                      .padding(start = 10.dp),)
                  Text14_400("See all",color = seallcolor, modifier = Modifier
                      .weight(1f)
                      .padding(top = 5.dp, start = 20.dp))
              }

              LazyRow(
                  modifier = Modifier
                      .fillMaxWidth()
                  // .height(260.dp)
              ) {
                  val list = listOf(
                      "A", "B", "C", "D")

                  items(list) { data ->
                      // Column(modifier = Modifier.padding(10.dp)) {
                      SellingItems()
                      //}



                  }}


          }
      }
//        item {
//            FlowRow() {
//                repeat(5){
//                    offerItems()
//                }
//            }
//        }
//        items(5) { data ->
//            //  Column(modifier = Modifier.padding(10.dp)) {
//            offerItems()
//            //  }
//
//
//
//        }
    }


}