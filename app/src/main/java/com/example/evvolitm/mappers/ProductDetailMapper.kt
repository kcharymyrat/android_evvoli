package com.example.evvolitm.mappers

import com.example.evvolitm.data.remote.respond.product_dtos.ProductDetailDto
import com.example.evvolitm.domain.model.ProductDetail

fun ProductDetailDto.toProductDetail(): ProductDetail {
    return ProductDetail(
        id = this.id,
        categoryId = this.categoryId,
        type = this.type,
        typeEn = this.typeEn,
        typeRu = this.typeRu,
        model = this.model,
        title = this.title,
        titleEn = this.titleEn,
        titleRu = this.titleRu,
        slug = this.slug,
        price = this.price,
        salePercent = this.salePercent,
        salePrice = this.salePrice,
        onSale = this.onSale,
        description = this.description,
        descriptionEn = this.descriptionEn,
        descriptionRu = this.descriptionRu,
        imageUrl = this.imageUrl,
        thumbnailUrl = this.thumbnailUrl,
        videoUrl = this.video,
        images = this.images.map { it.toProductImage() },
        specs = this.specs.map { it.toProductSpec() }
    )
}