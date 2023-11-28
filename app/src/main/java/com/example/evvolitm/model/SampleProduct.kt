package com.example.evvolitm.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SampleProduct(
    @DrawableRes val imageResId: Int,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    val price: Double,
    val salePrice: Double = price,
)