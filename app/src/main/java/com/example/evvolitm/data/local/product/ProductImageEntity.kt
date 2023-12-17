package com.example.evvolitm.data.local.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "product_images",
    foreignKeys = [
        ForeignKey(
            entity = ProductInDetailsEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductImageEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val description: String? = null
)

