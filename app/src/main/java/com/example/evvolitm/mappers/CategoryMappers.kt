package com.example.evvolitm.mappers

import com.example.evvolitm.data.local.category.CategoryEntity
import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.example.evvolitm.domain.model.Category

fun CategoryDto.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        nameEn = nameEn,
        nameRu = nameRu,
        slug = slug,
        description = description,
        descriptionEn = descriptionEn,
        descriptionRu = descriptionRu,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
    )
}

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        nameEn = nameEn,
        nameRu = nameRu,
        slug = slug,
        description = description,
        descriptionEn = descriptionEn,
        descriptionRu = descriptionRu,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
    )
}


fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        nameEn = nameEn,
        nameRu = nameRu,
        slug = slug,
        description = description,
        descriptionEn = descriptionEn,
        descriptionRu = descriptionRu,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
    )
}


fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        nameEn = nameEn,
        nameRu = nameRu,
        slug = slug,
        description = description,
        descriptionEn = descriptionEn,
        descriptionRu = descriptionRu,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
    )
}
