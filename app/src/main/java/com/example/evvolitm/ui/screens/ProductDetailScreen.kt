package com.example.evvolitm.ui.screens

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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

    // Get screen width
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    if (productDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val listState = rememberLazyListState()

        Column(
            modifier = Modifier,
        ) {
            LazyRow(
                // You can customize the content padding if needed
                contentPadding = PaddingValues(0.dp),
                // Customize the horizontal arrangement of items
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (productDetail.videoUrl != null) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(screenWidth) // Full screen width
                                .height(300.dp)
                        ) {
                            println("productDetail.videoUrl  = ${productDetail.videoUrl }")
                            VideoPlayerComposable(videoUrl = productDetail.videoUrl)
                        }
                    }
                }

                item {
                    ProductDetailImage(
                        productDetail = productDetail,
                        imageUrl = productDetail.imageUrl,
                        modifier = Modifier
                            .width(screenWidth) // Full screen width
                            .height(300.dp)
                    )
                }

                items(items = productDetailScreenState.productImageList) { image ->
                    // Replace this with your custom composable item
                    val imageUrl = image.imageUrl
                    ProductDetailImage(
                        productDetail = productDetail,
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .width(screenWidth) // Full screen width
                            .height(300.dp)
                    )
                }

            }

            LazyColumn(
                state = listState,
                modifier = modifier,
                contentPadding = PaddingValues(0.dp),
            ) {
                item() {
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
fun ProductDetailImage(
    productDetail: ProductDetail,
    imageUrl: String,
    modifier: Modifier = Modifier
) {

    val imageModel = ImageRequest.Builder(context = LocalContext.current)
        .data(EvvoliTmApi.BASE_URL + imageUrl)
        .crossfade(true)
        .build()

    val imageState = rememberAsyncImagePainter(model = imageModel).state

    Box(
        modifier = modifier.clickable { },
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
                modifier = Modifier.fillMaxWidth().padding(8.dp)
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

@Composable
fun VideoPlayerComposable(videoUrl: String) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }

    // Save and restore the playback position and play state
    var playbackPosition by rememberSaveable { mutableLongStateOf(0L) }
    var isPlaying by rememberSaveable { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(EvvoliTmApi.BASE_URL + videoUrl)
            setMediaItem(mediaItem)
            seekTo(playbackPosition)
            playWhenReady = isPlaying
            prepare()
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    isLoading.value = playbackState == Player.STATE_BUFFERING || playbackState == Player.STATE_IDLE
                }
            })
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            // Save the current playback position and state before disposing of the player
            playbackPosition = exoPlayer.currentPosition
            isPlaying = exoPlayer.isPlaying
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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