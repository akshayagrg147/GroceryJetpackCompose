package com.grocery.mandixpress.features.Spash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.SplashNavigation.ScreenRoute
import com.grocery.mandixpress.appUpdate.InAppUpdateManager
import com.grocery.mandixpress.features.Home.ui.screens.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavHostController,
    context: Context,
    sharedpreferenceCommon: sharedpreferenceCommon
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = com.grocery.mandixpress.R.drawable.logo),

            contentDescription = "splash image",
            modifier = Modifier
                .padding()

                .align(Alignment.CenterVertically)

        )

//        Column(verticalArrangement = Arrangement.SpaceBetween) {
//            Text14_h1(text = "Mandi", modifier = Modifier.padding(top = 5.dp), color = Color.White)
//            Text12_body1(
//                text = "Online Groceries",
//                modifier = Modifier.padding(),
//                color = Color.White
//            )
//
//        }


    }
    AutoUpdateScreen(sharedpreferenceCommon,navController)





}
@Composable
fun AutoUpdateScreen(
    sharedpreferenceCommon: sharedpreferenceCommon,
    navController: NavHostController
) {
    val context = LocalContext.current
    val updateManager = remember { InAppUpdateManager(context as ComponentActivity) }
    var updateAvailable by remember { mutableStateOf(false) }

    val resultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle successful update
                    if (sharedpreferenceCommon.getJwtToken().isNotEmpty()) {
                        if (sharedpreferenceCommon.getCombinedAddress().isNotEmpty())
                            context.startActivity(Intent(context, HomeActivity::class.java))
                        else {

                            navController.navigate(ScreenRoute.LocateMeScreen.route)
                        }
                    }
                    else{
                        navController.navigate(ScreenRoute.LoginScreen.route)
                    }


            }
        }

    LaunchedEffect(key1 = updateManager) {
        updateAvailable = withContext(Dispatchers.IO) {
            updateManager.checkForUpdates()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (updateAvailable) {
            updateManager.startUpdateFlow()
          //  resultLauncher.launch(null)
        } else {
            LaunchedEffect(key1 = Unit){
                delay(500)
                Log.d("groceryApp", "SplashScreen:${sharedpreferenceCommon.getJwtToken()} ")
                if (sharedpreferenceCommon.getJwtToken().isNotEmpty()) {
                    if (sharedpreferenceCommon.getCombinedAddress().isNotEmpty())
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    else {

                        navController.navigate(ScreenRoute.LocateMeScreen.route)
                    }
                }
                else{

                    navController.navigate(ScreenRoute.LoginScreen.route)
                }

            }
        }
    }
}