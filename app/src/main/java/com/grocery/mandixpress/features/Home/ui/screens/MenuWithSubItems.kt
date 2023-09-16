package com.grocery.mandixpress.features.Spash.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.*
import com.grocery.mandixpress.Utils.Text10_h2
import com.grocery.mandixpress.Utils.Text13_body1
import com.grocery.mandixpress.Utils.Text14_h2
import com.grocery.mandixpress.features.Home.ui.ui.theme.greyLightColor
import com.grocery.mandixpress.features.Home.ui.ui.theme.whiteColor
import kotlinx.coroutines.launch








@Composable
fun TabContentScreen(data: String) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text13_body1(
            text = data

        )
    }
}