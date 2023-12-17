package com.example.evvolitm.navigation

import android.util.Log
import com.example.evvolitm.ui.screens.CategoriesScreen
//import com.example.evvolitm.ui.screens.CategoryProductsScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.evvolitm.presentation.CategoryScreenState
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.presentation.ProductScreenState
import com.example.evvolitm.ui.screens.CategoryProductsScreen
import com.example.evvolitm.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    categoryScreenState: CategoryScreenState,
    productScreenState: ProductScreenState
) {
    NavHost(navController = navController, startDestination = Screen.Categories.route) {

        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                navController = navController,
                categoryScreenState = categoryScreenState,
                onEvent = mainViewModel::onCategoryScreenEvent,
                modifier = Modifier
            )
        }

        composable(
            route = "${Screen.CategoryProducts.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            Log.d("Nav", "inNavigation => categoryId = $categoryId")
            Log.d("Nav", "inNavigation => route = $route")

            LaunchedEffect(categoryId) {
                if (categoryId != null) {
                    productScreenState.productList = emptyList()
                    productScreenState.page = 1
                    mainViewModel.loadCategoryProducts(categoryId = categoryId, true)
                }
            }

            CategoryProductsScreen(
                navController = navController,
                categoryId = categoryId ?: "",
                productScreenState = productScreenState,
                onScreenProductEvent = mainViewModel::onProductScreenEvent,
                modifier = Modifier,
            )
        }


    }
}