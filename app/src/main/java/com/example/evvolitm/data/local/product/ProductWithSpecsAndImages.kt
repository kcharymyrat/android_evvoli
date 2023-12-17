package com.example.evvolitm.data.local.product

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithSpecsAndImages(
    @Embedded val product: ProductInDetailsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val specs: List<ProductSpecEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val images: List<ProductImageEntity>
)

