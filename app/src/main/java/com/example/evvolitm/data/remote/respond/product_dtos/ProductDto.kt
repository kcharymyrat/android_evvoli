package com.example.evvolitm.data.remote.respond.product_dtos

import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("type") val type: String? = null,
    @SerializedName("type_en") val typeEn: String? = null,
    @SerializedName("type_ru") val typeRu: String? = null,
    @SerializedName("model") val model: String,
    @SerializedName("title") val title: String,
    @SerializedName("title_en") val titleEn: String,
    @SerializedName("title_ru") val titleRu: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("price") val price: String,
    @SerializedName("sale_percent") val salePercent: Int,
    @SerializedName("sale_price") val salePrice: String,
    @SerializedName("on_sale") val onSale: Boolean,
    @SerializedName("in_sale") val inStock: Boolean,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
)
