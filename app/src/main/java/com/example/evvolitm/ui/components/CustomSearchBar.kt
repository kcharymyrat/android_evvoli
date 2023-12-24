package com.example.evvolitm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.evvolitm.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end= 8.dp),
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            active = false
//            navController.navigate(Screen.CategoryProductsScreen.route + "/${category.id}")
            navController.navigate(Screen.SearchProductsScreen.route + "/${query}")
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder =  {
            Text("Search")
        },
        leadingIcon =  {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier
                        .clickable {
                          if (query.isNotEmpty()) {
                              query = ""
                          } else {
                              active = false
                          }
                        },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    ) {

    }
}
