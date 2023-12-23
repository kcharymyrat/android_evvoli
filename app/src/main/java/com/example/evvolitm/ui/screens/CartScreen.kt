package com.example.evvolitm.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.example.evvolitm.presentation.CartScreenState
//
//@Composable
//fun CartItemsScreen(
//    mainViewModel: MainViewModel,
//    navController: NavHostController,
//    cartScreenState: CartScreenState,
//    onUpdateCartAndItsState: (String, String, String, Boolean) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val cart = cartScreenState.cart
//    val cartQty = cartScreenState.quantity ?: 0
//    val cartPrice = cart?.totalPrice ?: 0.00
//    val cartItems = cart?.cartItems?.toList() ?: listOf<CartItem>()
//
//    Column (
//        modifier = modifier
//    ) {
//
//        CartCheckout(
//            cartPrice = cartPrice,
//            navController = navController
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        CartItems(
//            mainViewModel = mainViewModel,
//            navController = navController,
//            cartUiState = cartScreenState,
//            cartItems = cartItems,
//        )
//
//    }
//}
//
//@Composable
//fun CartCheckout(
//    cartPrice: Double,
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//        ) {
//            Text(
//                text = "TOTAL",
//                style = MaterialTheme.typography.titleLarge,
//            )
//            Text(
//                text = "%.2f".format(cartPrice) + " m.",
//                style = MaterialTheme.typography.titleLarge,
//            )
//        }
//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//        ) {
//            Text(
//                text = "Proceed to Checkout",
//            )
//        }
//    }
//}
//
//@Composable
//fun CartItems(
//    mainViewModel: MainViewModel,
//    navController: NavHostController,
//    cartUiState: CartUiState,
//    cartItems: List<CartItem>,
//    modifier: Modifier = Modifier
//) {
//    Card {
//        LazyColumn(
//            modifier = modifier.background(color= Color.Magenta).padding(vertical = 4.dp)
//        ) {
//            items(cartItems) { cartItem ->
//                CartItemProduct(
//                    mainViewModel = mainViewModel,
//                    navController = navController,
//                    cartUiState = cartUiState,
//                    product = cartItem.product,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun CartItemProduct(
//    mainViewModel: MainViewModel,
//    navController: NavHostController,
//    cartUiState: CartUiState,
//    product: Product,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        modifier = modifier
//    ) {
//        val productSlug = product.titleResId
//        Column {
//            ProductImage(product = product)
//            CartItemProductInfo(
//                product = product,
//                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
//            )
//            CartItemButton(
//                mainViewModel = mainViewModel,
//                cartUiState = cartUiState,
//                product = product,
//            )
//        }
//    }
//}
//
//@Composable
//fun CartItemProductInfo(product: Product, modifier: Modifier) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween,
//        modifier = modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = stringResource(id = product.titleResId),
//            style = MaterialTheme.typography.titleLarge,
//        )
//        if (product.salePrice < product.price) {
//            Row(modifier = Modifier) {
//                Text(
//                    text = "%.2f".format(product.price) + " TMT",
//                    style = MaterialTheme.typography.titleLarge,
//                    textDecoration = TextDecoration.LineThrough
//                )
//                Text(
//                    text = "%.2f".format(product.salePrice) + " TMT",
//                    style = MaterialTheme.typography.titleLarge,
//                )
//            }
//        } else {
//            Text(
//                text = "%.2f".format(product.price) + " TMT",
//                style = MaterialTheme.typography.titleLarge,
//            )
//        }
//    }
//}
//
//
//@Composable
//fun CartItemButton(
//    mainViewModel: MainViewModel,
//    cartUiState: CartUiState,
//    product: Product,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround,
//        modifier = modifier.fillMaxWidth()
//    ) {
//        ProductToCartButtons(
//            mainViewModel = mainViewModel,
//            cartUiState = cartUiState,
//            product = product
//        )
//
//    }
//}