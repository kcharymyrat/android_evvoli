package com.example.evvolitm.data.remote.respond.category_dtos

import com.google.gson.annotations.SerializedName


data class CategoriesResponseDto(
    @SerializedName("count") var count: Int = 0,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: List<CategoryDto> = listOf()
)