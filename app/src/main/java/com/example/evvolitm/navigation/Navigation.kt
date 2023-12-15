package com.example.evvolitm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evvolitm.ui.screens.CategoriesScreen
import com.example.evvolitm.ui.screens.CategoryProductsScreen
import com.example.evvolitm.ui.screens.MainViewModel

@Composable
fun Navigation(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Categories.route) {
        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                navController = navController,
                categoriesUiState = mainViewModel.categoriesUiState,
                mainViewModel = mainViewModel,
                retryAction = mainViewModel::getCategories
            )
        }
        composable(
            route = "${Screen.CategoryProducts.route}/{categorySlug}",
            arguments = listOf(navArgument("categorySlug") { type = NavType.StringType })
        ) { backStackEntry ->
            val categorySlug = backStackEntry.arguments?.getString("categorySlug")
            println("in nav : \n\tcategorySlug = $categorySlug")
            CategoryProductsScreen(
                navController = navController,
                productsUiState = mainViewModel.productsUiState,
                retryAction = { categorySlug?.let { mainViewModel.getCategoryProducts(it) } },
                modifier = Modifier,
            )
        }
    }
}