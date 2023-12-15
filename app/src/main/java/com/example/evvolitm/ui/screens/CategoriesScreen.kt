package com.example.evvolitm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.model.Category
import com.example.evvolitm.model.CategoryResponse
import com.example.evvolitm.navigation.Screen
import kotlin.reflect.KFunction1


@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoriesUiState: CategoriesUiState,
    mainViewModel: MainViewModel,
    retryAction: KFunction1<Int, Unit>,
    modifier: Modifier = Modifier
) {
    when (categoriesUiState) {
        is CategoriesUiState.Loading -> LoadingScreen(
            navController = navController,
            modifier = modifier.fillMaxSize()
        )
        is CategoriesUiState.Success -> CategoryListDisplay(
            navController = navController,
            categoryResponse = categoriesUiState.categoryPage,
            mainViewModel = mainViewModel,
            modifier = modifier.fillMaxWidth(),
        )

        is CategoriesUiState.Error -> ErrorScreen(
            navController = navController,
            retryAction = { retryAction(mainViewModel.currentPage.value ?: 1) },
            modifier = modifier.fillMaxSize()
        )
    }
}


/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(navController: NavHostController, retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
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
            CategoryImage(category = category)
            CategoryInformation(category = category, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            CategoryButton(navController=navController, category = category)
        }
    }
}

@Composable
fun CategoryImage(category: Category, modifier: Modifier = Modifier) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data("http://192.168.1.14:8000/" + category.imageUrl)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
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
            println("Button = ${Screen.CategoryProducts.route} + /${category.slug}}")
            navController.navigate(Screen.CategoryProducts.route + "/${category.slug}")
        }
    ) {
        Text(text = "See Product")
    }
}

@Composable
fun CategoryListDisplay(
    navController: NavHostController,
    categoryResponse: CategoryResponse,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(categoryResponse.results) {
            CategoryItem(
                navController = navController,
                category = it,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
            )
        }

        if (categoryResponse.next != null) {
            println("categoryResponse.next  = ${categoryResponse.next }")
            item {
                Button(onClick = { mainViewModel.getCategories(2) }) {
                    Text("Load More")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoadingScreenPreview() {
//    EvvoliTmTheme {
//        LoadingScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ErrorScreenPreview() {
//    EvvoliTmTheme {
//        ErrorScreen({})
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CategoryListScreenPreview() {
//    EvvoliTmTheme {
//        val mockData = List(10) {
//            Category(
//                "$it",
//                "${it.toString()}) name",
//                "${it.toString()}) nameEn",
//                "${it.toString()}) nameRu",
//                "${it.toString()})_slug",
//                "${it.toString()}) desc",
//                "${it.toString()}) descEn",
//                "${it.toString()}) descRu",
//                "${it.toString()}) imageUrl",
//                "${it.toString()}) thumbUrl",
//            ) }
//        CategoryListDisplay(mockData)
//    }
//}



