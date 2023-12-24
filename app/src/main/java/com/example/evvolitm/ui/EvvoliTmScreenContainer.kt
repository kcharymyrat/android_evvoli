package com.example.evvolitm.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.evvolitm.navigation.Navigation
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.ui.components.BottomNavigationBar
import com.example.evvolitm.ui.components.EvvoliTopBar
import com.example.evvolitm.ui.theme.EvvoliTmTheme

@Composable
fun EvvoliTmScreenContainer(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    val categoryScreenState = mainViewModel.categoryScreenState.collectAsState().value
    val productScreenState = mainViewModel.productScreenState.collectAsState().value
    val productDetailScreenState = mainViewModel.productDetailScreenState.collectAsState().value
    val cartScreenState = mainViewModel.cartScreenState.collectAsState().value

    Scaffold(
        topBar = {
            EvvoliTopBar(cartScreenState = cartScreenState, navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController, cartScreenState)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Navigation(
                navController = navController,
                mainViewModel = mainViewModel,
                categoryScreenState = categoryScreenState,
                productScreenState = productScreenState,
                productDetailScreenState = productDetailScreenState,
                cartScreenState = cartScreenState
            )
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EvvoliTmPreview() {
//    EvvoliTmTheme(darkTheme = false) {
//        EvvoliTmScreenContainer()
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun EvvoliTmDarkThemePreview() {
//    EvvoliTmTheme(darkTheme = true) {
//        EvvoliTmScreenContainer()
//    }
//}