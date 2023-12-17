package com.example.evvolitm.presentation

import com.example.evvolitm.domain.model.Product

data class ProductScreenState(
    var isLoading: Boolean = false,
    var page: Int = 1,
    var productList: List<Product> = emptyList(),

//    var isRefreshing: Boolean = false,
//    var previousConnectivityState: MutableState<String> = mutableStateOf(""),
//    var backOnlineStarted: Boolean = false,
)
