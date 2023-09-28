package com.grocery.mandixpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Spash.splashScreenNavigation
import com.grocery.mandixpress.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.mandixpress.features.Spash.ui.viewmodel.RegisterLoginViewModal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: sharedpreferenceCommon
    private val viewModal: RegisterLoginViewModal by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppIntegrity()
        setContent {
            GroceryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    splashScreenNavigation(this,sharedPreferences)
                }
            }
        }
    }

    private fun initAppIntegrity() {

//        FirebaseAuth.getInstance().getFirebaseAuthSettings()
//            .setAppVerificationDisabledForTesting(true)
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