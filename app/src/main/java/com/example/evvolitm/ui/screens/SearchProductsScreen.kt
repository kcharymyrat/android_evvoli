package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.evvolitm.R
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.presentation.ProductScreenEvents
import com.example.evvolitm.presentation.ProductScreenState
import kotlinx.coroutines.FlowPreview

@Composable
fun SearchProductsScreen(
    navController: NavHostController,
    searchProductScreenState: ProductScreenState,
    onSearchProductScreenEvent: (ProductScreenEvents, String) -> Unit,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (searchProductScreenState.productList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        SearchProductListDisplay(
            navController = navController,
            searchProductScreenState = searchProductScreenState,
            onSearchProductScreenEvent = onSearchProductScreenEvent,
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            modifier = Modifier.fillMaxSize()
        )
    }


}


@OptIn(FlowPreview::class)
@Composable
fun SearchProductListDisplay(
    navController: NavHostController,
    searchProductScreenState: ProductScreenState,
    onSearchProductScreenEvent: (ProductScreenEvents, String) -> Unit,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {


        items(searchProductScreenState.productList.size) {productIndex ->

            ProductItem(
                navController = navController,
                product = searchProductScreenState.productList[productIndex],
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
            )

            if (productIndex >= searchProductScreenState.productList.size - 1 && !searchProductScreenState.isLoading) {
                onSearchProductScreenEvent(ProductScreenEvents.OnPaginate(), searchProductScreenState.query)
            }
        }
    }
}