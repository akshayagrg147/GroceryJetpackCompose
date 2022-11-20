package com.grocery.groceryapp.features.Home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grocery.groceryapp.features.Home.Navigator.gridItems

import com.grocery.groceryapp.R
import com.grocery.groceryapp.Utils.Text14_400
import com.grocery.groceryapp.Utils.Text24_700
import com.grocery.groceryapp.features.Home.domain.modal.MainProducts
import com.grocery.groceryapp.features.Home.ui.ui.theme.*

@Composable
fun SearchScreen() {
    var search = remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Find Products",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        TextField(
            value = search.value,
            shape = RoundedCornerShape(8.dp),


            onValueChange = {
                search.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .padding(start = 10.dp, end = 10.dp,top=10.dp),
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
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            val ls: MutableList<MainProducts> = ArrayList()
            ls.add(MainProducts("Fruit Basket", R.drawable.fruitbasket, Purple700))
            ls.add(MainProducts("Snacks", R.drawable.snacks, borderColor))
            ls.add(MainProducts("Non Veg", R.drawable.nonveg, disableColor))
            ls.add(MainProducts("Oils", R.drawable.oils, darkFadedColor))


            gridItems(
                data = ls,
                columnCount = 2,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) { itemData ->
                SearchItems(itemData)
            }
        }


    }


}

@Composable
fun SearchItems(data: MainProducts) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = data.color

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = data.image),

                contentDescription = "splash image",
                modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .align(alignment = Alignment.CenterHorizontally),


                )
            Text24_700(
                text = data.name, color = headingColor,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )


        }
    }
}



