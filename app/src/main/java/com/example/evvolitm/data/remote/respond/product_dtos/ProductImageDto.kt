package com.example.evvolitm.data.remote.respond.product_dtos

import com.google.gson.annotations.SerializedName

data class ProductImageDto(
    val id: String? = null,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    val description: String? = null
)
