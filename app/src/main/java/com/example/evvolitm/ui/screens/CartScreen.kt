package com.example.evvolitm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.data.local.cart.CartItemProductEntity
import com.example.evvolitm.data.local.cart.CartItemWithProduct
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.util.Screen

@Composable
fun CartItemsScreen(
    navController: NavHostController,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    onCreateNewCardScreenState: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (cartScreenState.id == null) {
        onCreateNewCardScreenState()
    }

    val cartQty = cartScreenState.cartQty
    val cartPrice = cartScreenState.cartTotalPrice
    val cartItems = cartScreenState.cartItems

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Card {
            CartCheckout(
                cartPrice = cartPrice,
                navController = navController
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CartItems(
            navController = navController,
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            cartItems = cartItems,
        )

    }
}

@Composable
fun CartCheckout(
    cartPrice: Double,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                text = "TOTAL",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "%.2f".format(cartPrice) + " m.",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Button(
            onClick = { navController.navigate(Screen.OrderScreen.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp, // Normal elevation
                pressedElevation = 8.dp, // Elevation when the button is pressed
                disabledElevation = 0.dp  // Elevation when the button is disabled
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                text = "Proceed to Checkout",
            )
        }
    }
}

@Composable
fun CartItems(
    navController: NavHostController,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    cartItems: List<CartItemWithProduct>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        items(cartItems) { cartItem ->
            CartItemProductComposable(
                navController = navController,
                cartUiState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                product = cartItem.product,
                productQty = cartItem.cartItem.quantity,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun CartItemProductComposable(
    navController: NavHostController,
    cartUiState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    productQty: Int,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        val productSlug = "product.titleResId"
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CartItemProductImage(
                product = product,
                onSeeDetailsButtonClicked = {},
                modifier = Modifier.weight(1f)
            )
            Column(modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            ) {
                CartItemProductInfo(product = product)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "%.2f".format(product.salePrice * productQty) + " m.")
                    CartItemButton(
                        cartScreenState = cartUiState,
                        onUpdateCartAndItsState = onUpdateCartAndItsState,
                        product = product,
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemProductImage(
    product: CartItemProductEntity,
    onSeeDetailsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageModel = ImageRequest.Builder(context = LocalContext.current)
        .data(EvvoliTmApi.BASE_URL + product.imageUrl)
        .crossfade(true)
        .build()

    val imageState = rememberAsyncImagePainter(model = imageModel).state

    Box(
        modifier = modifier.clickable { onSeeDetailsButtonClicked() },
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = "product.title"
            )
        } else {
            AsyncImage(
                model = imageModel,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "product.title",
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}


@Composable
fun CartItemProductInfo(product: CartItemProductEntity, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Title",
            style = MaterialTheme.typography.titleSmall,
        )
        if (product.salePrice < product.price) {
            Row(modifier = Modifier) {
                Text(
                    text = "%.2f".format(product.price) + " m.",
                    style = MaterialTheme.typography.labelLarge,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "%.2f".format(product.salePrice) + " m.",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        } else {
            Text(
                text = "%.2f".format(product.price) + " m.",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}


@Composable
fun CartItemButton(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        CartItemProductToCartButtons(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            product = product
        )
    }
}

@Composable
fun CartItemProductToCartButtons(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    modifier: Modifier = Modifier
) {

    val productQty = remember { mutableIntStateOf(0) }
    LaunchedEffect(cartScreenState) {
        productQty.intValue = cartScreenState.cartItems.find {
            it.product.id == product.id
        }?.cartItem?.quantity ?: 0
        println("in LaunchedEffect _cart: productQty.intValue = ${productQty.intValue}")
        println("ProductDetailToCartButtons: _cart : $cartScreenState ")
    }

    println("ProductDetailToCartButtons: _cart : cartScreenState = $cartScreenState ")
    println("ProductDetailToCartButtons: _cart : productQty = $productQty")

    CartProductMinusQtyPlus(
        cartScreenState = cartScreenState,
        onUpdateCartAndItsState = onUpdateCartAndItsState,
        product = product,
        cartProductQty = productQty.intValue,
        modifier = modifier
    )
}

@Composable
fun CartProductMinusQtyPlus(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    cartProductQty: Int,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp, // Normal elevation
            pressedElevation = 8.dp, // Elevation when the button is pressed
            disabledElevation = 0.dp  // Elevation when the button is disabled
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier,
        ) {
            CartProductMinusClickable(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                product = product
            )
            Spacer(modifier.width(8.dp))
            CartProductDetailQty(
                cartScreenState = cartScreenState,
                product = product,
                cartProductQty = cartProductQty
            )
            Spacer(modifier.width(8.dp))
            CartProductPlusClickable(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                product = product
            )
        }
    }
}

@Composable
fun CartProductDetailQty(
    cartScreenState: CartScreenState,
    product: CartItemProductEntity,
    cartProductQty: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = cartProductQty.toString(),
        modifier = modifier,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun CartProductPlusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    modifier: Modifier = Modifier
) {
    val newCartItemProduct = CartItemProduct(
        id = product.id,
        title = product.title,
        titleEn = product.titleEn,
        titleRu = product.titleRu,
        model = product.model,
        slug = product.slug,
        imageUrl = product.imageUrl,
        price = product.price.toDouble(),
        salePrice = product.salePrice.toDouble(),
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowRight,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .clickable {
                println("in PlusClickable onClick: product.id = ${product.id}")
                onUpdateCartAndItsState(newCartItemProduct, false)
            }
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
    )
}

@Composable
fun CartProductMinusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    product: CartItemProductEntity,
    modifier: Modifier = Modifier
) {
    val newCartItemProduct = CartItemProduct(
        id = product.id,
        title = product.title,
        titleEn = product.titleEn,
        titleRu = product.titleRu,
        model = product.model,
        slug = product.slug,
        imageUrl = product.imageUrl,
        price = product.price.toDouble(),
        salePrice = product.salePrice.toDouble(),
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowLeft,
        contentDescription = "Increase product quantity",
        modifier = modifier
            .clickable {
                println("in MinusClickable onClick: product.id = ${product.id}")
                onUpdateCartAndItsState(newCartItemProduct, false)
            }
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Transparent)

    )
}