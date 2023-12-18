package com.example.evvolitm.data.remote.respond.product_dtos

import com.google.gson.annotations.SerializedName

data class ProductImageDto(
    @SerializedName("id") val id: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("description") val description: String?
)

