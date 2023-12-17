package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.Category
import com.example.evvolitm.presentation.CategoryScreenEvents
import com.example.evvolitm.presentation.CategoryScreenState
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.util.Screen
import kotlin.reflect.KFunction1


@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoryScreenState: CategoryScreenState,
    onEvent: (CategoryScreenEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    if (categoryScreenState.categoryList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        CategoryListDisplay(
            navController = navController,
            categoryScreenState = categoryScreenState,
            onEvent = onEvent,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun CategoryListDisplay(
    navController: NavHostController,
    categoryScreenState: CategoryScreenState,
    onEvent: (CategoryScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(categoryScreenState.categoryList.size) {catIndex ->
            CategoryItem(
                navController = navController,
                category = categoryScreenState.categoryList[catIndex],
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
            )
//            println("category = ${categoryScreenState.categoryList}")
            println("catIndex = $catIndex")
            println("categoryScreenState.categoryList.size = ${categoryScreenState.categoryList.size}")
            println("categoryScreenState.isLoading = ${categoryScreenState.isLoading}")

            if (catIndex >= categoryScreenState.categoryList.size - 1 && !categoryScreenState.isLoading) {
                println("should Fire")
                onEvent(CategoryScreenEvents.OnPaginate())
            }
        }

    }
}


@Composable
fun CategoryItem(
    navController: NavHostController,
    category: Category,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column {
            CategoryImage(navController = navController, category = category)
            CategoryInformation(category = category, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            CategoryButton(navController=navController, category = category)
        }
    }
}

@Composable
fun CategoryImage(navController: NavHostController, category: Category, modifier: Modifier = Modifier) {
    val imageModel = ImageRequest.Builder(context = LocalContext.current)
        .data(EvvoliTmApi.BASE_URL + category.imageUrl)
        .crossfade(true)
        .build()

    val imageState = rememberAsyncImagePainter(model = imageModel).state

    Box(
        modifier = modifier.clickable { navController.navigate(Screen.CategoryProducts.route + "/${category.id}") },
        contentAlignment = Alignment.Center
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                imageVector = Icons.Rounded.ImageNotSupported,
                contentDescription = category.name
            )
        } else {
            AsyncImage(
                model = imageModel,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun CategoryInformation(category: Category, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleLarge,
        )
        if (category.description != null) {
            Text(
                text = category.description ?: "",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
fun CategoryButton(
    navController: NavHostController,
    category: Category,
    modifier: Modifier = Modifier
) {
    Button(onClick = {
            Log.d("Nav", "ButtonOnClick => categorySlug = ${category.slug}")
            Log.d("Nav",
                "ButtonOnClick => Button = ${Screen.CategoryProducts.route}/${category.id}"
            )
            Log.d("Nav", "ButtonOnClick => categoryId = ${category.id}")
            navController.navigate(Screen.CategoryProducts.route + "/${category.id}")
        }
    ) {
        Text(text = "See Product")
    }
}



///**
// * The home screen displaying the loading message.
// */
//@Composable
//fun LoadingScreen(navController: NavHostController, modifier: Modifier = Modifier) {
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
//fun ErrorScreen(navController: NavHostController, retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
