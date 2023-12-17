package com.example.evvolitm.data.remote.respond.product_dtos

import com.google.gson.annotations.SerializedName

data class ProductSpecDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("title_en") val titleEn: String,
    @SerializedName("title_ru") val titleRu: String,
    @SerializedName("content") val content: String,
    @SerializedName("content_en") val contentEn: String,
    @SerializedName("content_ru") val contentRu: String
)
