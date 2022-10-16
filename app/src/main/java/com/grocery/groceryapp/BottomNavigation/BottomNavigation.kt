package com.grocery.groceryapp.BottomNavigation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyNetwork,
//        BottomNavItem.AddPost,
        BottomNavItem.Notification,
        BottomNavItem.Jobs
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        BottomNavItem.Home.screen_route -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true

        }
        BottomNavItem.MyNetwork.screen_route -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true

        }
        BottomNavItem.Notification.screen_route -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true

        }
        BottomNavItem.Jobs.screen_route-> {
            // Hide BottomBar and TopBar
            bottomBarState.value = true

        }
        else->{
            bottomBarState.value = false
        }
    }




    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = Color.Black
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                        label = { Text(text = item.title,
                            fontSize = 9.sp) },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Black.copy(0.4f),
                        alwaysShowLabel = true,
                        selected = currentRoute == item.screen_route,
                        onClick = {
                            navController.navigate(item.screen_route) {

                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

    )

}