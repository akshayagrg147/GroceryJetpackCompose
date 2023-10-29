package com.grocery.mandixpress.features.home.ui.screens

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.compose.rememberNavController
import com.grocery.mandixpress.features.home.dashboardnavigation.NavigationGraph
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.Constants.Companion.ACTION_CUSTOM_BROADCAST
import com.grocery.mandixpress.connectionState.ConnectionState
import com.grocery.mandixpress.connectionState.currentConnectivityState
import com.grocery.mandixpress.connectionState.observeConnectivityAsFlow
import com.grocery.mandixpress.connectionState.ui.onlineconnection
import com.grocery.mandixpress.features.home.ui.ui.theme.GroceryAppTheme
import com.grocery.mandixpress.features.home.ui.viewmodal.HomeAllProductsViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity() : ComponentActivity() {
    private val viewModal: HomeAllProductsViewModal by viewModels()

    @Inject
    lateinit var sharedpreferenceCommon: sharedpreferenceCommon

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
                    NavigationGraph(
                        viewModal,
                        navController = navController,
                        this@HomeActivity,
                        sharedpreferenceCommon
                    )
                }
//                FirebaseApp.initializeApp(this)
//                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val token = task.result
//                        Log.d("firebaseToken", "${token}")
//                        // Save or use the FCM token as needed
//                    }
//                }
            }
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_CUSTOM_BROADCAST) {
                val message = intent.getStringExtra("title")
                if (message?.contains("orders") == true) {

                }
                // Handle the received message here
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ACTION_CUSTOM_BROADCAST)
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }


}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}


@Composable
fun ConnectivityStatus() {
    var internetconnectvity by remember { mutableStateOf(false) }
    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        internetconnectvity = true

        // navController.navigate(DashBoardNavRoute.Home.screen_route)

    } else {
        internetconnectvity = false
    }
    if (!internetconnectvity)
        CustomDialog() {
            internetconnectvity = true
        }


}


@Composable
fun CustomDialog(call: (Boolean) -> Unit) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false)
    ) {
        onlineconnection() {
            if (it)
                call(true)
        }
    }

}

