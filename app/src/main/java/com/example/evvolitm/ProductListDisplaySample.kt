package com.example.evvolitm

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evvolitm.ui.theme.EvvoliTmTheme
import com.example.evvolitm.data.SampleProductList
import com.example.evvolitm.model.SampleProduct




@Composable
fun ProductListDisplay(
    sampleProducts: List<SampleProduct>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(sampleProducts) {
            ProductItem(
                sampleProduct = it,
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
fun ProductItem(
    sampleProduct: SampleProduct,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column {
            ProductImage(sampleProduct = sampleProduct)
            ProductInformation(sampleProduct = sampleProduct, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            ProductButton()
        }
    }
}

@Composable
fun ProductImage(sampleProduct: SampleProduct, modifier: Modifier = Modifier) {
    Box {
        Image(
            painter = painterResource(id = sampleProduct.imageResId),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProductInformation(sampleProduct: SampleProduct, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = sampleProduct.titleResId),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(id = sampleProduct.descriptionResId),
            style = MaterialTheme.typography.labelSmall,
        )
        if (sampleProduct.salePrice < sampleProduct.price) {
            Row {
                Text(
                    text = sampleProduct.price.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = sampleProduct.salePrice.toString(),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        } else {
            Text(
                text = sampleProduct.price.toString(),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Composable
fun ProductButton(modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "See Product")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductListDisplaySamplePreview() {
    EvvoliTmTheme {
        ProductListDisplay(SampleProductList.sampleProducts)
    }
}


