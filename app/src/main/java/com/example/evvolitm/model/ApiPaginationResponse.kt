package com.example.evvolitm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiPagination<T>(
    @SerialName(value = "count") var count: Int? = 0,
    @SerialName(value = "next") var next: String? = null,
    @SerialName(value = "previous") var previous: String? = null,
    @SerialName(value = "results") var results: List<T>? = listOf()
)


//val apiCategoryPagination = ApiPagination(
//    count = 10,
//    next = "http://example.com/api/category/?page=2",
//    previous = null,
//    results = listOf(
//        Category(/* product properties here */),
//        Category(/* product properties here */),
//        // etc.
//    )
//)
