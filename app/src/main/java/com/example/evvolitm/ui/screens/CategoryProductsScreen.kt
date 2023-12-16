//package com.example.evvolitm.ui.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.example.evvolitm.R
//import com.example.evvolitm.data.remote.respond.Product
//import com.example.evvolitm.ui.ProductsUiState
//
//
//@Composable
//fun CategoryProductsScreen(
//    navController: NavHostController,
//    productsUiState: ProductsUiState,
//    retryAction: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val categorySlug = navController
//        .currentBackStackEntry
//        ?.arguments
//        ?.getString("categorySlug")
//    println("CategoryProductsScreen: categorySlug = $categorySlug")
//    LaunchedEffect(categorySlug) {
//        println("Launched Effect: categorySlug = $categorySlug")
//        categorySlug?.let {
//            retryAction()
//        }
//    }
//    when (productsUiState) {
//        is ProductsUiState.Loading -> LoadingScreenProducts(
//            navController = navController,
//            modifier = modifier.fillMaxSize()
//        )
//        is ProductsUiState.Success -> ProductListDisplay(
//            navController = navController,
//            products = productsUiState.products,
//            modifier = modifier.fillMaxWidth()
//        )
//
//        is ProductsUiState.Error -> ErrorScreenProducts(
//            navController = navController,
//            retryAction = retryAction,
//            modifier = modifier.fillMaxSize()
//        )
//    }
//}
//
//
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
//
//
//
//@Composable
//fun ProductListDisplay(
//    navController: NavHostController,
//    products: List<Product>,
//    modifier: Modifier = Modifier,
//    contentPadding: PaddingValues = PaddingValues(0.dp)
//) {
//    LazyColumn(
//        modifier = modifier,
//        contentPadding = contentPadding,
//    ) {
//        items(products) {
//            ProductItem(
//                navController = navController,
//                product = it,
//                modifier = Modifier
//                    .padding(
//                        horizontal = dimensionResource(id = R.dimen.padding_medium),
//                        vertical = dimensionResource(id = R.dimen.padding_small)
//                    )
//            )
//        }
//    }
//}
//
//
//@Composable
//fun ProductItem(
//    navController: NavHostController,
//    product: Product,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        modifier = modifier
//    ) {
//        Column {
//            ProductImage(product = product)
//            ProductInformation(product = product, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
//            ProductButton(
//                navController = navController,
//                product = product,
//
//            )
//        }
//    }
//}
//
//@Composable
//fun ProductImage(product: Product, modifier: Modifier = Modifier) {
//    Box {
//        AsyncImage(
//            model = ImageRequest.Builder(context = LocalContext.current).data("http://192.168.1.14:8000/" + product.imageUrl)
//                .crossfade(true).build(),
//            error = painterResource(R.drawable.ic_broken_image),
//            placeholder = painterResource(R.drawable.loading_img),
//            contentDescription = product.title,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//@Composable
//fun ProductInformation(product: Product, modifier: Modifier) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = modifier
//    ) {
//        Text(
//            text = product.title,
//            style = MaterialTheme.typography.titleLarge,
//        )
//        if (product.description != null) {
//            Text(
//                text = product.description ?: "",
//                style = MaterialTheme.typography.labelSmall,
//            )
//        }
//    }
//}
//
//@Composable
//fun ProductButton(
//    navController: NavHostController,
//    product: Product,
//    modifier: Modifier = Modifier
//) {
//    Button(onClick = { /*TODO*/ }) {
//        Text(text = "See Product")
//    }
//}
//
//
////@Preview(showBackground = true)
////@Composable
////fun CategoryProductsLoadingScreenPreview() {
////    EvvoliTmTheme {
////        LoadingScreenProducts()
////    }
////}
////
////@Preview(showBackground = true)
////@Composable
////fun CategoryProductsErrorScreenPreview() {
////    EvvoliTmTheme {
////        ErrorScreenProducts({})
////    }
////}
////
////@Preview(showBackground = true)
////@Composable
////fun CategoryProductListScreenPreview() {
////    EvvoliTmTheme {
////        val mockData = List(10) {
////            Product(
////                "$it",
////                "${it.toString()}) name",
////                "${it.toString()}) nameEn",
////                "${it.toString()}) nameRu",
////                "${it.toString()})_slug",
////                "${it.toString()}) desc",
////                "${it.toString()}) descEn",
////                "${it.toString()}) descRu",
////                "${it.toString()}) imageUrl",
////                "${it.toString()}) thumbUrl",
////            ) }
////        ProductListDisplay(mockData)
////    }
////}
//
//
//
