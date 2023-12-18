package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.ProductDetail
import com.example.evvolitm.presentation.ProductDetailScreenEvents
import com.example.evvolitm.presentation.ProductDetailScreenState

@Composable
fun ProductDetailScreen(
    productDetailScreenState: ProductDetailScreenState,
    onScreenProductEvent: (ProductDetailScreenEvents, String) -> Unit,
//    cartUiState: CartUiState,
    modifier: Modifier = Modifier
) {

    val productDetail: ProductDetail? = productDetailScreenState.productDetail
    Log.d("Nav", "ProductDetailScreen => productDetail = $productDetail")

    if (productDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val listState = rememberLazyListState()

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = modifier
        ) {
            LazyColumn(
                state = listState,
                modifier = modifier,
                contentPadding = PaddingValues(0.dp),
            ) {
                item() {
                    ProductDetailImage(productDetail = productDetail)
                    ProductDetailInformation(
                        productDetail = productDetail,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
//                ProductToCartButtons(
//                    mainViewModel = mainViewModel,
//                    cartUiState = cartUiState,
//                    productDetail = productDetail
//                )
                }
            }
        }
    }
}

@Composable
fun ProductDetailImage(productDetail: ProductDetail, modifier: Modifier = Modifier) {

    val imageModel = ImageRequest.Builder(context = LocalContext.current)
        .data(EvvoliTmApi.BASE_URL + productDetail.imageUrl)
        .crossfade(true)
        .build()

    val imageState = rememberAsyncImagePainter(model = imageModel).state

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {  },
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = productDetail.title
            )
        } else {
            AsyncImage(
                model = imageModel,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = productDetail.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProductDetailInformation(productDetail: ProductDetail, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = productDetail.title,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = productDetail.description ?: "",
            style = MaterialTheme.typography.labelSmall,
        )
        if (productDetail.salePrice < productDetail.price) {
            Row {
                Text(
                    text = productDetail.price.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = productDetail.salePrice.toString(),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        } else {
            Text(
                text = productDetail.price.toString(),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}


//@Composable
//fun ProductToCartButtons(
//    mainViewModel: MainViewModel,
//    cartUiState: CartUiState,
//    productDetail: ProductDetail,
//    modifier: Modifier = Modifier
//) {
//    var productQty = 0
//    val cartItems = cartUiState.cart?.cartItems ?: mutableListOf<CartItem>()
//    cartItems.forEach {
//        if (it.product == productDetail) {
//            productQty = it.quantity
//        }
//    }
//    if (productQty > 0) {
//        MinusQtyPlus(
//            mainViewModel = mainViewModel,
//            cartUiState = cartUiState,
//            productDetail = productDetail,
//        )
//    } else {
//        ProductAddButton(mainViewModel = mainViewModel, productDetail = productDetail)
//    }
//}
//
//@Composable
//fun ProductAddButton(
//    mainViewModel: MainViewModel,
//    productDetail: ProductDetail,
//    modifier: Modifier = Modifier
//) {
//    Button(onClick = { mainViewModel.addToCart(product = productDetail, qty = 1) }) {
//        Text(text = "Add Product")
//    }
//}
//
//@Composable
//fun MinusQtyPlus(
//    mainViewModel: MainViewModel,
//    cartUiState: CartUiState,
//    productDetail: ProductDetail,
//    modifier: Modifier = Modifier,
//    ) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround,
//        modifier = modifier
//            .background(color = colorResource(id = R.color.purple_200), shape = Shapes.extraLarge)
//            .size(height = 40.dp, width = 120.dp)
//    ) {
//        MinusClickable(mainViewModel = mainViewModel, productDetail = productDetail)
//        ProductQty(cartUiState = cartUiState, productDetail = productDetail)
//        PlusClickable(mainViewModel = mainViewModel, productDetail = productDetail)
//    }
//}
//
//@Composable
//fun ProductQty(
//    cartUiState: CartUiState,
//    productDetail: ProductDetail,
//    modifier: Modifier = Modifier
//) {
//    var productQty = 0
//    for (item in cartUiState.cart?.cartItems ?: mutableListOf<CartItem>()) {
//        if (productDetail == item.product) {
//            println("in if (product == item.product) -> item = $item")
//            productQty = item.quantity ?: 0
//        }
//    }
//    Text(text = productQty.toString(), modifier = modifier)
//}
//
//@Composable
//fun PlusClickable(
//    mainViewModel: MainViewModel,
//    productDetail: ProductDetail,
//    modifier: Modifier = Modifier
//) {
//    Icon(
//        imageVector = Icons.Filled.KeyboardArrowRight,
//        contentDescription = "Favorite Icon",
//        modifier = modifier
//            .clickable {
//                mainViewModel.addToCart(product = productDetail, qty = 1)
//                println("Plus clicked!")
//            }
//    )
//}
//
//@Composable
//fun MinusClickable(
//    mainViewModel: MainViewModel,
//    productDetail: Product,
//    modifier: Modifier = Modifier
//) {
//    Icon(
//        imageVector = Icons.Filled.KeyboardArrowLeft,
//        contentDescription = "Favorite Icon",
//        modifier = modifier
//            .clickable {
//                mainViewModel.removeFromCart(product = productDetail, qty = 1)
//                println("Minus clicked!")
//            }
//
//    )
//}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ProductItemPreview() {
//    EvvoliTmTheme {
////        ProductItem(product = ProductList.products[0])
//    }
//}