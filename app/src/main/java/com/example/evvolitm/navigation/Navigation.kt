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
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.presentation.CategoryScreenState
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.presentation.ProductDetailScreenState
import com.example.evvolitm.presentation.ProductScreenState
import com.example.evvolitm.ui.screens.CartItemsScreen
import com.example.evvolitm.ui.screens.CategoryProductsScreen
import com.example.evvolitm.ui.screens.OrderForm
import com.example.evvolitm.ui.screens.ProductDetailScreen
import com.example.evvolitm.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    categoryScreenState: CategoryScreenState,
    productScreenState: ProductScreenState,
    productDetailScreenState: ProductDetailScreenState,
    cartScreenState: CartScreenState,
) {
    NavHost(navController = navController, startDestination = Screen.CategoriesScreen.route) {

        composable(route = Screen.CategoriesScreen.route) {
            CategoriesScreen(
                navController = navController,
                categoryScreenState = categoryScreenState,
                onEvent = mainViewModel::onCategoryScreenEvent,
                modifier = Modifier
            )
        }

        composable(
            route = "${Screen.CategoryProductsScreen.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            Log.d("Nav", "inNavigation => categoryId = $categoryId")
            Log.d("Nav", "inNavigation => route = $route")

            LaunchedEffect(categoryId) {
                if (categoryId != null && categoryId != mainViewModel.productCategoryId) {
                    println("In - LaunchedEffect(categoryId)")
                    productScreenState.productList = emptyList()
                    productScreenState.page = 1
                    mainViewModel.loadCategoryProducts(categoryId = categoryId, true)
                    mainViewModel.productCategoryId = categoryId
                }
            }

            CategoryProductsScreen(
                navController = navController,
                categoryId = categoryId ?: "",
                productScreenState = productScreenState,
                onScreenProductEvent = mainViewModel::onProductScreenEvent,
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = mainViewModel::updateCart,
                modifier = Modifier,
            )
        }

        composable(
            route = "${Screen.ProductDetailScreen.route}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")

            Log.d("Nav", "inNavigation => productId = $productId")
            Log.d("Nav", "inNavigation => route = $route")

            LaunchedEffect(productId) {
                if (productId != null) {
                    productDetailScreenState.productDetail = null
                    productDetailScreenState.productImageList = emptyList()
                    productDetailScreenState.productSpecList = emptyList()
                    mainViewModel.loadProductDetail(productId = productId, true)
                }
            }

            ProductDetailScreen(
                productDetailScreenState = productDetailScreenState,
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = mainViewModel::updateCart,
            )
        }


        composable(
            route = Screen.CartScreen.route
        ) {
            CartItemsScreen(
                navController = navController,
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = mainViewModel::updateCart,
                onCreateNewCardScreenState = mainViewModel::createCartScreenState,
            )
        }

        composable(
            route = Screen.OrderScreen.route
        ) {
            OrderForm(
                navController = navController,
                cartScreenState = cartScreenState,
                onCreateNewCardScreenState = mainViewModel::createCartScreenState,
            )
        }
    }
}