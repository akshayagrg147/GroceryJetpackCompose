package com.grocery.mandixpress.features.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.grocery.mandixpress.Utils.Text12_h1

@Composable
fun PrivacyPolicyScreen() {
    var search = remember {
        mutableStateOf("")
    }
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text12_h1(
                text = "There will be the complete privacy policy screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,

                )
        }
    }
}





