package com.example.evvolitm.presentation

import com.example.evvolitm.domain.model.Category

sealed class CategoryScreenEvents {
    data class OnPaginate(val type: String = "category"): CategoryScreenEvents()
    data object Refresh : CategoryScreenEvents()

//    object BackOnline : CategoryScreenEvents()
//    data class OnPaginate(val type: String) : CategoryScreenEvents()
//    object Navigate: CategoryScreenEvents
}