package com.grocery.groceryapp.features.Home.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.grocery.groceryapp.features.Home.domain.modal.PassProductIdCategory
import com.grocery.groceryapp.features.Home.ui.ui.theme.GroceryAppTheme
import com.grocery.groceryapp.features.Spash.ItemScreenNavigation
@Composable
fun OrderDetailsScreen(
    model: String?,
    context: HomeActivity,
    navController: NavHostController
) {

    ItemScreenNavigation(context, model?.split(" ")?.get(0), model?.split(" ")?.get(1),navController)

    }
