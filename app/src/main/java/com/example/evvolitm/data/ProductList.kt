package com.example.evvolitm.data

import com.example.evvolitm.R
import com.example.evvolitm.model.Product
import kotlin.random.Random

object ProductList {
    val products: List<Product> = listOf(
        Product(R.drawable.tv_1, R.string.title_1, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_2, R.string.title_3, R.string.description, Random.nextDouble(200.00, 1000.00), Random.nextDouble(200.00, 1000.00) * 0.8),
        Product(R.drawable.tv_3, R.string.title_3, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_4, R.string.title_4, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_5, R.string.title_5, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_6, R.string.title_6, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_7, R.string.title_7, R.string.description, Random.nextDouble(200.00, 1000.00), Random.nextDouble(200.00, 1000.00) * 0.87),
        Product(R.drawable.tv_8, R.string.title_8, R.string.description, Random.nextDouble(200.00, 1000.00), Random.nextDouble(200.00, 1000.00) * 0.71),
        Product(R.drawable.tv_9, R.string.title_9, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_10, R.string.title_10, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_11, R.string.title_11, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_12, R.string.title_12, R.string.description, Random.nextDouble(200.00, 1000.00)),
        Product(R.drawable.tv_13, R.string.title_13, R.string.description, Random.nextDouble(200.00, 1000.00)),
    )
}