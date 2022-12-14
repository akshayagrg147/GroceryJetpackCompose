package com.grocery.groceryapp.features.Home.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.grocery.groceryapp.connectionState.ConnectionState
import com.grocery.groceryapp.connectionState.currentConnectivityState
import com.grocery.groceryapp.connectionState.observeConnectivityAsFlow
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Spash.ui.screens.ListItems
import com.grocery.groceryapp.features.Spash.ui.ui.theme.GroceryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConnectivityStatus()
            val data = intent.getParcelableExtra<HomeAllProductsResponse>("parced")
            com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme {
                ListItems(this,data!!)
            }

        }

    }
}


@Composable
fun Greeting5(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    GroceryAppTheme {
        Greeting5("Android")
    }
}
