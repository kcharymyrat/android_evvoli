package com.example.evvolitm.data.remote.respond.product_dtos

import com.google.gson.annotations.SerializedName

data class ProductsResponseDto(
    @SerializedName("count") var count: Int? = 0,
    @SerializedName("next") var next: String? = null,
    @SerializedName("previous") var previous: String? = null,
    @SerializedName("results") var results: List<ProductDto>
)
