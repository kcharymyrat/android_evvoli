package com.example.evvolitm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.util.Screen


sealed class BottomNavItem(val screen: Screen, val icon: ImageVector, val label: String) {
    object Categories : BottomNavItem(Screen.CategoriesScreen, Icons.Default.Home, "Categories")
    object Cart : BottomNavItem(Screen.CartScreen, Icons.Default.ShoppingCart, "Cart")
    object About : BottomNavItem(Screen.AboutScreen, Icons.Default.Info, "About")
}


@Composable
fun BottomNavigationBar(navController: NavHostController, cartScreenState: CartScreenState) {
    val items = listOf(
        BottomNavItem.Categories,
        BottomNavItem.Cart,
        BottomNavItem.About
    )

    NavigationBar {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if ("cart" in item.screen.route.toString().lowercase()) {
                        Box(
                            modifier = Modifier.size(24.dp)  // Adjust the size to match other icons
                        ) {
                            Icon(
                                item.icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.matchParentSize()  // Make the icon fill the Box
                            )
                            if (cartScreenState.cartQty > 0) {
                                Text(
                                    text = cartScreenState.cartQty.toString(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .offset(x = (12).dp, y = (-12).dp)  // Adjust the position
                                        .background(
                                            color = MaterialTheme.colorScheme.secondary,
                                            shape = CircleShape
                                        )
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    } else {
                        Icon(
                            item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                label = { Text(item.label) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


