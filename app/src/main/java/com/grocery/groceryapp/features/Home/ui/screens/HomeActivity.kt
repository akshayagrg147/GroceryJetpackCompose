package com.grocery.groceryapp.features.Home.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.grocery.groceryapp.DashBoardNavRouteNavigation.NavigationGraph
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.showMsg
import com.grocery.groceryapp.connectionState.ConnectionState
import com.grocery.groceryapp.connectionState.currentConnectivityState
import com.grocery.groceryapp.connectionState.observeConnectivityAsFlow
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.groceryapp.features.Spash.ui.viewmodel.HomeAllProductsViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity() : ComponentActivity() {
    private val viewModal: HomeAllProductsViewModal by viewModels()
    @Inject
    lateinit var sharedpreferenceCommon:sharedpreferenceCommon
     @OptIn(ExperimentalCoroutinesApi::class)
     @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                //    bottomBar = { com.grocery.groceryapp.BottomNavigation.BottomNavigation(navController = navController) }
                ) {
                    ConnectivityStatus()
                    NavigationGraph(navController = navController,this@HomeActivity,sharedpreferenceCommon)


                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        LocalContext.current.showMsg("connected")
    } else {
        LocalContext.current.showMsg("Disconnected")
    }
}

