package com.grocery.mandixpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.grocery.mandixpress.SharedPreference.CombinedSharedPreference
import com.grocery.mandixpress.features.splash.splashnavigation.splashScreenNavigation
import com.grocery.mandixpress.features.home.ui.ui.theme.GroceryAppTheme
import com.grocery.mandixpress.features.splash.ui.viewmodel.RegisterLoginViewModal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: CombinedSharedPreference
    private val viewModal: RegisterLoginViewModal by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppIntegrity()
        setContent {
            GroceryAppTheme {
                splashScreenNavigation(this)

            }
        }
    }



    private fun initAppIntegrity() {
        FirebaseApp.initializeApp(this)
        val firebaseApp=FirebaseAppCheck.getInstance()
        firebaseApp.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GroceryAppTheme {
        Greeting("Android")
    }
}