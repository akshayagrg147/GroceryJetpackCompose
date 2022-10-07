package com.grocery.groceryapp.features.Home.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.groceryapp.features.Spash.ItemScreenNavigation
import com.grocery.groceryapp.RoomDatabase.Dao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailScreen  : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val productId = intent.extras?.getString("productId")
            val productCategory = intent.extras?.getString("ProductCategory")
            GroceryAppTheme(){
                Surface(color = MaterialTheme.colors.background) {
                    ItemScreenNavigation(context = this,productId,productCategory)
                }
            }
        }
    }



}