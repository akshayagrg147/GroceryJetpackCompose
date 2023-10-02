package com.grocery.mandixpress.features.Home.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.features.Spash.ui.screens.ListItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListItemsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConnectivityStatus()
            val data = intent.getParcelableExtra<HomeAllProductsResponse>("parced")
            com.grocery.mandixpress.features.Home.ui.ui.theme.GroceryAppTheme {
                ListItems(this,data!!,)
            }

        }

    }
}



