package com.grocery.groceryapp.features.Home.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.groceryapp.features.Spash.ItemScreenNavigation

class OrderDetailScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bundle = intent.extras?.getString("name")
            GroceryAppTheme(){
                Surface(color = MaterialTheme.colors.background) {
                    ItemScreenNavigation(context = this)
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    GroceryAppTheme {
        Greeting3("Android")
    }
}