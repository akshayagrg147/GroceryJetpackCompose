package com.grocery.groceryapp.Home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.BottomNavigation.NavigationGraph
import com.grocery.groceryapp.ui.theme.GroceryAppTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { com.grocery.groceryapp.BottomNavigation.BottomNavigation(navController = navController) }
                ) {

                    NavigationGraph(navController = navController,this@HomeActivity)
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    GroceryAppTheme {
        Greeting2("Android")
    }
}