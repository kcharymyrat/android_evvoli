package com.example.evvolitm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class ProductResponse(
    @SerialName(value = "count") var count: Int? = 0,
    @SerialName(value = "next") var next: String? = null,
    @SerialName(value = "previous") var previous: String? = null,
    @SerialName(value = "results") var results: List<Product>
)

@Serializable
data class Image (
    val id: String? = null,
    @SerialName(value = "img_url")
    val imageUrl: String,
    @SerialName(value = "thumbnail_url")
    val thumbnailUrl: String,
    val description: String? = null,
)

@Serializable
data class Spec (
    val id: String? = null,
    val title: String,
    @SerialName(value = "title_en")
    val titleEn: String,
    @SerialName(value = "title_ru")
    val titleRu: String,
    val content: String,
    @SerialName(value = "content_en")
    val contentEn: String,
    @SerialName(value = "content_ru")
    val contentRu: String,
)

@Serializable
data class Product(
    val id: String,
    val category: Category,
    val type: String,
    @SerialName(value = "type_en")
    val typeEn: String,
    @SerialName(value = "type_ru")
    val typeRu: String,
    val model: String,
    val title: String,
    @SerialName(value = "title_en")
    val titleEn: String,
    @SerialName(value = "title_ru")
    val titleRu: String,
    val slug: String,
    val price: Double,
    @SerialName(value = "sale_percent")
    val salePercent: Int = 0,
    @SerialName(value = "sale_price")
    val salePrice: Double,
    @SerialName(value = "on_sale")
    val onSale: Boolean,
    val description: String,
    @SerialName(value = "description_en")
    val descriptionEn: String,
    @SerialName(value = "description_ru")
    val descriptionRu: String,
    @SerialName(value = "img_url")
    val imageUrl: String,
    @SerialName(value = "thumbnail_url")
    val thumbnailUrl: String,
    val images: List<Image> = listOf(),
    val specs: List<Spec> = listOf()
)
