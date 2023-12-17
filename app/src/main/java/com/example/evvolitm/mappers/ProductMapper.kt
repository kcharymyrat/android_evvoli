package com.example.evvolitm.mappers

import com.example.evvolitm.data.remote.respond.product_dtos.ProductDto
import com.example.evvolitm.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        categoryId = categoryId,
        type = type,
        typeEn = typeEn,
        typeRu = typeRu,
        model = model,
        title = title,
        titleEn = titleEn,
        titleRu = titleRu,
        slug = slug,
        price = price,
        salePercent = salePercent,
        salePrice = salePrice,
        onSale = onSale,
        inStock = inStock,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )
}

fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id = id,
        categoryId = categoryId,
        type = type,
        typeEn = typeEn,
        typeRu = typeRu,
        model = model,
        title = title,
        titleEn = titleEn,
        titleRu = titleRu,
        slug = slug,
        price = price,
        salePercent = salePercent,
        salePrice = salePrice,
        onSale = onSale,
        inStock = inStock,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )
}
