package com.example.evvolitm.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evvolitm.ui.components.EvvoliTopBar
import com.example.evvolitm.ui.screens.CategoriesScreen
import com.example.evvolitm.ui.screens.MainViewModel
import com.example.evvolitm.ui.theme.EvvoliTmTheme

@Composable
fun EvvoliTmApp() {
    Scaffold(
        topBar = {
            EvvoliTopBar()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val mainViewModel: MainViewModel =
                viewModel(factory = MainViewModel.Factory)
            CategoriesScreen(
                categoriesUiState = mainViewModel.categoriesUiState,
                retryAction = mainViewModel::getCategories
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvvoliTmPreview() {
    EvvoliTmTheme(darkTheme = false) {
        EvvoliTmApp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvvoliTmDarkThemePreview() {
    EvvoliTmTheme(darkTheme = true) {
        EvvoliTmApp()
    }
}