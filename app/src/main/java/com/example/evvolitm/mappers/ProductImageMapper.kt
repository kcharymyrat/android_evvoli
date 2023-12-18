package com.example.evvolitm.mappers

import com.example.evvolitm.data.remote.respond.product_dtos.ProductImageDto
import com.example.evvolitm.domain.model.ProductImage

fun ProductImageDto.toProductImage(): ProductImage {
    return ProductImage(
        id = this.id,
        imageUrl = this.imageUrl,
        thumbnailUrl = this.thumbnailUrl,
        description = this.description
    )
}