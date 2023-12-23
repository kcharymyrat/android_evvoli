package com.example.evvolitm.ui.screens

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.domain.model.ProductDetail
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.presentation.ProductDetailScreenState


@Composable
fun ProductDetailScreen(
    productDetailScreenState: ProductDetailScreenState,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val productDetail: ProductDetail? = productDetailScreenState.productDetail

    Log.d("Nav", "ProductDetailScreen => productDetail = $productDetail")

    // Get screen width
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Determine the total number of items
    var totalItems = productDetailScreenState.productImageList.size
    if (productDetail?.videoUrl != null) totalItems++ // +1 for video item
    if (productDetail?.imageUrl != null) totalItems++

    // State to track the current visible item index
    val listState = rememberLazyListState()
    val currentItemIndex = remember { mutableIntStateOf(0) }

    // Update the current item index based on the LazyRow scroll state
    LaunchedEffect(listState.firstVisibleItemIndex) {
        currentItemIndex.intValue = listState.firstVisibleItemIndex
    }

    if (productDetail == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
        ) {
            item {
                LazyRow(
                    state = listState,
                    // You can customize the content padding if needed
                    contentPadding = PaddingValues(0.dp),
                    // Customize the horizontal arrangement of items
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    item {
                        if (productDetail.videoUrl != null) {
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
                // Dot indicators
                DotIndicators(totalItems = totalItems, currentIndex = currentItemIndex.intValue)
            }

            item {
                Column(
                    modifier = modifier,
                ) {
                    ProductDetailInformation(
                        productDetail = productDetail,
                        cartScreenState = cartScreenState,
                        onUpdateCartAndItsState = onUpdateCartAndItsState,
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    )
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
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ProductDetailInformation(
    productDetail: ProductDetail,
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    modifier: Modifier
) {
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
            style = MaterialTheme.typography.labelLarge,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (productDetail.salePrice < productDetail.price) {
                Row {
                    Text(
                        text = productDetail.price.toString() + " m.",
                        style = MaterialTheme.typography.titleLarge,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = productDetail.salePrice.toString() + " m.",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            } else {
                Text(
                    text = productDetail.price.toString() + " m.",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            ProductDetailToCartButtons(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                productDetail = productDetail
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


@Composable
fun ProductDetailToCartButtons(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    productDetail: ProductDetail,
    modifier: Modifier = Modifier
) {

    val productQty = remember { mutableIntStateOf(0) }
    LaunchedEffect(cartScreenState) {
        productQty.intValue = cartScreenState.cartItems.find {
            it.product.id == productDetail.id
        }?.cartItem?.quantity ?: 0
        println("in LaunchedEffect _cart: productQty.intValue = ${productQty.intValue}")
        println("ProductDetailToCartButtons: _cart : $cartScreenState ")
    }

    println("ProductDetailToCartButtons: _cart : cartScreenState = $cartScreenState ")
    println("ProductDetailToCartButtons: _cart : productQty = $productQty")

    if (productQty.intValue > 0) {
        DetailMinusQtyPlus(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            productDetail = productDetail,
            cartProductQty = productQty.intValue,
            modifier = modifier
        )
    } else {
        ProductDetailAddButton(
            cartScreenState = cartScreenState,
            onUpdateCartAndItsState = onUpdateCartAndItsState,
            productDetail = productDetail,
            modifier = modifier
        )
    }
}


@Composable
fun ProductDetailAddButton(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    productDetail: ProductDetail,
    modifier: Modifier = Modifier
) {
    val newCartItemProduct = CartItemProduct(
        id = productDetail.id,
        title = productDetail.title,
        titleEn = productDetail.titleEn,
        titleRu = productDetail.titleRu,
        model = productDetail.model,
        slug = productDetail.slug,
        imageUrl = productDetail.imageUrl,
        price = productDetail.price.toDouble(),
        salePrice = productDetail.salePrice.toDouble(),
    )

    Button(
        onClick = {
            println("in ProductAddButton onClick: product.id = ${productDetail.id}")
            onUpdateCartAndItsState(newCartItemProduct, false)
        },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp, // Normal elevation
            pressedElevation = 8.dp, // Elevation when the button is pressed
            disabledElevation = 0.dp  // Elevation when the button is disabled
        )
    ) {
        Text(text = "Add Product")
    }
}

@Composable
fun DetailMinusQtyPlus(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    productDetail: ProductDetail,
    cartProductQty: Int,
    modifier: Modifier = Modifier,
) {
    Button(
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
            DetailMinusClickable(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                productDetail = productDetail
            )
            Spacer(modifier.width(8.dp))
            ProductDetailQty(
                cartScreenState = cartScreenState,
                productDetail = productDetail,
                cartProductQty = cartProductQty
            )
            Spacer(modifier.width(8.dp))
            DetailPlusClickable(
                cartScreenState = cartScreenState,
                onUpdateCartAndItsState = onUpdateCartAndItsState,
                productDetail = productDetail
            )
        }
    }
}

@Composable
fun ProductDetailQty(
    cartScreenState: CartScreenState,
    productDetail: ProductDetail,
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
fun DetailPlusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    productDetail: ProductDetail,
    modifier: Modifier = Modifier
) {
    val newCartItemProduct = CartItemProduct(
        id = productDetail.id,
        title = productDetail.title,
        titleEn = productDetail.titleEn,
        titleRu = productDetail.titleRu,
        model = productDetail.model,
        slug = productDetail.slug,
        imageUrl = productDetail.imageUrl,
        price = productDetail.price.toDouble(),
        salePrice = productDetail.salePrice.toDouble(),
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowRight,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .clickable {
                println("in PlusClickable onClick: product.id = ${productDetail.id}")
                onUpdateCartAndItsState(newCartItemProduct, false)
            }
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
    )
}

@Composable
fun DetailMinusClickable(
    cartScreenState: CartScreenState,
    onUpdateCartAndItsState: (CartItemProduct, Boolean) -> Unit,
    productDetail: ProductDetail,
    modifier: Modifier = Modifier
) {
    val newCartItemProduct = CartItemProduct(
        id = productDetail.id,
        title = productDetail.title,
        titleEn = productDetail.titleEn,
        titleRu = productDetail.titleRu,
        model = productDetail.model,
        slug = productDetail.slug,
        imageUrl = productDetail.imageUrl,
        price = productDetail.price.toDouble(),
        salePrice = productDetail.salePrice.toDouble(),
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowLeft,
        contentDescription = "Favorite Icon",
        modifier = modifier
            .clickable {
                println("in MinusClickable onClick: product.id = ${productDetail.id}")
                onUpdateCartAndItsState(newCartItemProduct,true)
            }
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Transparent)

    )
}


@Composable
fun DotIndicators(totalItems: Int, currentIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        for (i in 0 until totalItems) {
            Dot(isSelected = i == currentIndex)
        }
    }
}

@Composable
fun Dot(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(10.dp)
            .clip(CircleShape)
            .background(if (isSelected) MaterialTheme.colorScheme.tertiary else Color.LightGray)
    )
}
