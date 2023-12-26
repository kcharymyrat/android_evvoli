package com.example.evvolitm.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.evvolitm.navigation.Navigation
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.ui.components.BottomNavigationBar
import com.example.evvolitm.ui.components.EvvoliTopBar
import com.example.evvolitm.ui.components.CustomSearchBar
import com.example.evvolitm.util.Screen

@Composable
fun EvvoliTmScreenContainer(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    val categoryScreenState = mainViewModel.categoryScreenState.collectAsState().value
    val productScreenState = mainViewModel.productScreenState.collectAsState().value
    val searchProductScreenState = mainViewModel.searchProductScreenState.collectAsState().value
    val productDetailScreenState = mainViewModel.productDetailScreenState.collectAsState().value
    val cartScreenState = mainViewModel.cartScreenState.collectAsState().value
    val orderStatus = mainViewModel.orderStatus.collectAsState().value

    val currentRoute = currentRoute(navController)

    Scaffold(
        topBar = {
            println("currentRoute = $currentRoute")
            Log.d("Nav", "currentRoute = $currentRoute")

            Column {
                EvvoliTopBar(cartScreenState = cartScreenState, navController = navController)
                // Conditionally display SearchScreen
                if (currentRoute == Screen.CategoriesScreen.route ||
                    currentRoute?.split("/")?.first() == Screen.CategoryProductsScreen.route ||  currentRoute?.split("/")?.first() == Screen.SearchProductsScreen.route) {
                    CustomSearchBar(navController)
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController, cartScreenState)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Navigation(
                navController = navController,
                mainViewModel = mainViewModel,
                categoryScreenState = categoryScreenState,
                productScreenState = productScreenState,
                searchProductScreenState = searchProductScreenState,
                productDetailScreenState = productDetailScreenState,
                cartScreenState = cartScreenState,
                orderStatus = orderStatus,
            )
        }
    }
}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EvvoliTmPreview() {
//    EvvoliTmTheme(darkTheme = false) {
//        EvvoliTmScreenContainer()
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EvvoliTmDarkThemePreview() {
//    EvvoliTmTheme(darkTheme = true) {
//        EvvoliTmScreenContainer()
//    }
//}