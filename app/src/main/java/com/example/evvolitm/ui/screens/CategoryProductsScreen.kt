package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.colorResource
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
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.presentation.ProductScreenEvents
import com.example.evvolitm.presentation.ProductScreenState
import com.example.evvolitm.ui.theme.Shapes
import com.example.evvolitm.util.Screen
import kotlinx.coroutines.FlowPreview


@Composable
fun CategoryProductsScreen(
    navController: NavHostController,
    categoryId: String,
    productScreenState: ProductScreenState,
    onScreenProductEvent: (ProductScreenEvents, String) -> Unit,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

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
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            modifier = Modifier.fillMaxSize()
        )
    }


}


@OptIn(FlowPreview::class)
@Composable
fun ProductListDisplay(
    navController: NavHostController,
    categoryId: String,
    productScreenState: ProductScreenState,
    onProductScreenEvent: (ProductScreenEvents, String) -> Unit,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    LazyColumn(
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
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
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
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column {
            ProductImage(product = product)
            ProductInformation(product = product, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            ProductListItemButtons(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                product = product,
                onSeeDetailsButtonClicked = {
                    navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")
                }
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
fun ProductInformation(
    product: Product,
    modifier: Modifier
) {
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
fun ProductListItemButtons(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    onSeeDetailsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ) {
        SeeProductButton(
            product = product,
            onSeeDetailsButtonClicked = onSeeDetailsButtonClicked,
        )
        ProductToCartButtons(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product
        )

    }
}

@Composable
fun SeeProductButton(
    product: Product,
    onSeeDetailsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSeeDetailsButtonClicked,
        modifier = modifier
    ) {
        Text(text = "See Product")
    }
}

@Composable
fun ProductToCartButtons(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    var productQty = 0
    val cartItems = cartScreenState.cartItems
    cartItems.forEach {
        if (it.productId == product.id) {
            productQty = it.quantity
        }
    }
    if (productQty > 0) {
        MinusQtyPlus(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product,
            modifier = modifier
        )
    } else {
        ProductAddButton(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product,
            modifier = modifier
        )
    }
}

@Composable
fun ProductAddButton(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    Button(onClick = {
        println("in ProductAddButton onClick: product.id = ${product.id}")
        onUpdateCartAndItsState(product.id, product.price, product.salePrice,false)
    }) {
        Text(text = "Add Product")
    }
}

@Composable
fun MinusQtyPlus(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(color = colorResource(id = R.color.purple_200), shape = Shapes.extraLarge)
            .size(height = 40.dp, width = 120.dp)
    ) {
        MinusClickable(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product
        )
        ProductQty(
            cartScreenState = cartScreenState,
            product = product
        )
        PlusClickable(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product
        )
    }
}

@Composable
fun ProductQty(
    cartScreenState: CartScreenState,
    product: Product,
    modifier: Modifier = Modifier
) {
    var productQty = 0
    for (item in cartScreenState.cartItems) {
        if (product.id == item.productId) {
            println("in if (product == item.product) -> item = $item")
            productQty = item.quantity ?: 0
            break
        }
    }
    Text(text = productQty.toString(), modifier = modifier)
}

@Composable
fun PlusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowRight,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .clickable {
                println("in PlusClickable onClick: product.id = ${product.id}")
                onUpdateCartAndItsState(product.id, product.price, product.salePrice,false)
            }
    )
}

@Composable
fun MinusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowLeft,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .clickable {
                println("in MinusClickable onClick: product.id = ${product.id}")
                onUpdateCartAndItsState(product.id, product.price, product.salePrice,false)
            }

    )
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


