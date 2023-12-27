package com.example.evvolitm.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ProductFilterState(
    val types: List<String>, // Populate with product types
    var selectedType: String? = null,
    var minPrice: Float? = null,
    var maxPrice: Float? = null,
    var onSale: Boolean = false
)

@Composable
fun ProductFilterSidebar(
    filterState: ProductFilterState,
    onFilterChange: (ProductFilterState) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Filter by Type", style = MaterialTheme.typography.labelSmall)
        filterState.types.forEach { type ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = type == filterState.selectedType,
                    onCheckedChange = {
                        filterState.selectedType = if (it) type else null
                        onFilterChange(filterState)
                    }
                )
                Text(type)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Price Range", style = MaterialTheme.typography.labelMedium)
        // Add RangeSlider for price range

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("On Sale")
            Switch(
                checked = filterState.onSale,
                onCheckedChange = {
                    filterState.onSale = it
                    onFilterChange(filterState)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Apply filters */ }) {
            Text("Apply Filters")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductFilter() {
    val productFilterState = ProductFilterState(
        types = mutableListOf("UHD", "FHD", "PHD"),
        selectedType = "UHD",
        minPrice = 0.00f,
        maxPrice = 100.00f,
        onSale = true,
    )
    ProductFilterSidebar(productFilterState, ::print)
}