package com.example.evvolitm.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                categoriesUiState = mainViewModel.categoriesUiState,
                retryAction = mainViewModel::getCategories
            )
        }
    }

}