package com.example.evvolitm.mappers

import com.example.evvolitm.data.remote.respond.product_dtos.ProductSpecDto
import com.example.evvolitm.domain.model.ProductSpec

fun ProductSpecDto.toProductSpec(): ProductSpec {
    return ProductSpec(
        id = this.id,
        title = this.title,
        titleEn = this.titleEn,
        titleRu = this.titleRu,
        content = this.content,
        contentEn = this.contentEn,
        contentRu = this.contentRu
    )
}