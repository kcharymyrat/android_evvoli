package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.Product
import com.example.evvolitm.presentation.ProductScreenEvents
import com.example.evvolitm.presentation.ProductScreenState


@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryId: String,
    productScreenState: ProductScreenState,
    onScreenProductEvent: (ProductScreenEvents, String) -> Unit,
    modifier: Modifier = Modifier
) {

//    println()
//    LaunchedEffect(categoryId) {
//        Log.d("Nav", "LaunchedEffect => categoryId = $categoryId")
//        retryAction()
//    }

    Log.d("Nav", "CategoryProductsScreen => categoryId = $categoryId")
    Log.d("Nav",
        "CategoryProductsScreen => " +
                "productScreenState.productList = ${productScreenState.productList}"
    )

    if (productScreenState.productList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        ProductListDisplay(
            navController = navController,
            categoryId = categoryId,
            productScreenState = productScreenState,
            onProductScreenEvent = onScreenProductEvent,
            modifier = Modifier.fillMaxSize()
        )
    }


}


@Composable
fun ProductListDisplay(
    navController: NavHostController,
    categoryId: String,
    productScreenState: ProductScreenState,
    onProductScreenEvent: (ProductScreenEvents, String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(productScreenState.productList.size) {productIndex ->
            Log.d("Nav", "ProductListDisplay " +
                    "=> productScreenState.productList[index] " +
                    "= ${productScreenState.productList[productIndex]}"
            )
            ProductItem(
                navController = navController,
                product = productScreenState.productList[productIndex],
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
            )
//            println("productList = ${productScreenState.productList}")
            println("productIndex = $productIndex")
//            println("productScreenState.productList.size = ${productScreenState.productList.size}")
//            println("productScreenState.isLoading = ${productScreenState.isLoading}")

            if (productIndex >= productScreenState.productList.size - 1 && !productScreenState.isLoading) {
                println("should Fire")
                onProductScreenEvent(ProductScreenEvents.OnPaginate(), categoryId)

            }
        }
    }
}


@Composable
fun ProductItem(
    navController: NavHostController,
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column {
            ProductImage(product = product)
            ProductInformation(product = product, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            ProductButton(
                navController = navController,
                product = product,
            )
        }
    }
}

@Composable
fun ProductImage(product: Product, modifier: Modifier = Modifier) {
    val imageModel = ImageRequest.Builder(context = LocalContext.current)
        .data(EvvoliTmApi.BASE_URL + product.imageUrl)
        .crossfade(true)
        .build()

    val imageState = rememberAsyncImagePainter(model = imageModel).state

    Box(
        modifier = modifier.clickable {  },
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = product.title
            )
        } else {
            AsyncImage(
                model = imageModel,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProductInformation(product: Product, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
fun ProductButton(
    navController: NavHostController,
    product: Product,
    modifier: Modifier = Modifier
) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "See Product")
    }
}


///**
// * The home screen displaying the loading message.
// */
//@Composable
//fun LoadingScreenProducts(navController: NavHostController, modifier: Modifier = Modifier) {
//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
//}
//
///**
// * The home screen displaying error message with re-attempt button.
// */
//@Composable
//fun ErrorScreenProducts(navController: NavHostController, retryAction: () -> Unit, modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
//        )
//        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
//        Button(onClick = retryAction) {
//            Text(stringResource(R.string.retry))
//        }
//    }
//}


