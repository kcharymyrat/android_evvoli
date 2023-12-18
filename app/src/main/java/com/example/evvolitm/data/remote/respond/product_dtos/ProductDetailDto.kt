package com.example.evvolitm.data.remote.respond.product_dtos

import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.google.gson.annotations.SerializedName

data class ProductDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("type") val type: String,
    @SerializedName("type_en") val typeEn: String,
    @SerializedName("type_ru") val typeRu: String,
    @SerializedName("model") val model: String,
    @SerializedName("title") val title: String,
    @SerializedName("title_en") val titleEn: String,
    @SerializedName("title_ru") val titleRu: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("price") val price: String,
    @SerializedName("sale_percent") val salePercent: Int,
    @SerializedName("sale_price") val salePrice: String,
    @SerializedName("on_sale") val onSale: Boolean,
    @SerializedName("description") val description: String?,
    @SerializedName("description_en") val descriptionEn: String?,
    @SerializedName("description_ru") val descriptionRu: String?,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("video_url") val video: String?,
    @SerializedName("images") val images: List<ProductImageDto>,
    @SerializedName("specs") val specs: List<ProductSpecDto>
)
