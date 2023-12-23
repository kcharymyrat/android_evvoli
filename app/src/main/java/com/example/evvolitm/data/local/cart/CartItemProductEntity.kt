package com.example.evvolitm.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item_products")
data class CartItemProductEntity(
    @PrimaryKey val id: String,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val model: String,
    val slug: String,
    val imageUrl: String?,
    val price: Double,
    val salePrice: Double
)
