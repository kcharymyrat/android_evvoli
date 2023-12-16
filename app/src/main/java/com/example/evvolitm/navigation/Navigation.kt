package com.example.evvolitm.navigation

import com.example.evvolitm.ui.screens.CategoriesScreen
//import com.example.evvolitm.ui.screens.CategoryProductsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.evvolitm.presentation.CategoryScreenState
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    categoryScreenState: CategoryScreenState,
) {
    NavHost(navController = navController, startDestination = Screen.Categories.route) {
        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                navController = navController,
                categoryScreenState = categoryScreenState,
                onEvent = mainViewModel::onEvent
            )
        }
//        composable(
//            route = "${Screen.CategoryProducts.route}/{categorySlug}",
//            arguments = listOf(navArgument("categorySlug") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val categorySlug = backStackEntry.arguments?.getString("categorySlug")
//            println("in nav : \n\tcategorySlug = $categorySlug")
//            CategoryProductsScreen(
//                navController = navController,
//                productsUiState = mainViewModel.productsUiState,
//                retryAction = { categorySlug?.let { mainViewModel.getCategoryProducts(it) } },
//                modifier = Modifier,
//            )
//        }
    }
}