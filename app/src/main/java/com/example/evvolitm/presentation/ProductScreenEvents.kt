package com.example.evvolitm.presentation

sealed class ProductScreenEvents {
    data class OnPaginate(val type: String = ""): ProductScreenEvents()
    data class Refresh(val type: String) : ProductScreenEvents()

//    object BackOnline : CategoryScreenEvents()
//    data class OnPaginate(val type: String) : CategoryScreenEvents()
//    object Navigate: CategoryScreenEvents
}

