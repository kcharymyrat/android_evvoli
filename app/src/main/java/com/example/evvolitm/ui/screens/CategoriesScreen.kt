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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.evvolitm.R
import com.example.evvolitm.model.Category
import com.example.evvolitm.ui.theme.EvvoliTmTheme


@Composable
fun CategoriesScreen(
    categoriesUiState: CategoriesUiState, retryAction: () -> Unit, modifier: Modifier = Modifier
) {
    when (categoriesUiState) {
        is CategoriesUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CategoriesUiState.Success -> CategoryListDisplay(
            categories = categoriesUiState.categories, modifier = modifier.fillMaxWidth()
        )

        is CategoriesUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
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
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun CategoryListDisplay(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(categories) {
            CategoryItem(
                category = it,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_small)
                    )
            )
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column {
            ProductImage(category = category)
            CategoryInformation(category = category, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            CategoryButton()
        }
    }
}

@Composable
fun ProductImage(category: Category, modifier: Modifier = Modifier) {
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
fun CategoryButton(modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "See Product")
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    EvvoliTmTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    EvvoliTmTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryListScreenPreview() {
    EvvoliTmTheme {
        val mockData = List(10) {
            Category(
                "$it",
                "${it.toString()}) name",
                "${it.toString()}) nameEn",
                "${it.toString()}) nameRu",
                "${it.toString()})_slug",
                "${it.toString()}) desc",
                "${it.toString()}) descEn",
                "${it.toString()}) descRu",
                "${it.toString()}) imageUrl",
                "${it.toString()}) thumbUrl",
            ) }
        CategoryListDisplay(mockData)
    }
}



