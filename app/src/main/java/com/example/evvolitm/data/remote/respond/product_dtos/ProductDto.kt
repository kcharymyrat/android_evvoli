package com.example.evvolitm.data.remote.respond.product_dtos

import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.google.gson.annotations.SerializedName

data class ProductDto(
    val id: String,
    val category: CategoryDto,
    val type: String,
    @SerializedName("type_en") val typeEn: String,
    @SerializedName("type_ru") val typeRu: String,
    val model: String,
    val title: String,
    @SerializedName("title_en") val titleEn: String,
    @SerializedName("title_ru") val titleRu: String,
    val slug: String,
    val price: String,
    @SerializedName("sale_percent") val salePercent: Int = 0,
    @SerializedName("sale_price") val salePrice: String,
    @SerializedName("on_sale") val onSale: Boolean,
    val description: String?,
    @SerializedName("description_en") val descriptionEn: String?,
    @SerializedName("description_ru") val descriptionRu: String?,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    val video: String? = null,
    val images: List<ProductImageDto> = listOf(),
    val specs: List<ProductSpecDto> = listOf()
)