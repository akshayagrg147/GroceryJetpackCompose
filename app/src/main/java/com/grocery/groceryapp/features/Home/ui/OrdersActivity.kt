package com.grocery.groceryapp.features.Home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.features.Spash.ProfileScreenNavigation

class OrdersActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val openOption = remember {
                mutableStateOf(true)
            }
            com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ProfileScreenNavigation(navController = navController, context = this)
                }
            }
        }
    }
}