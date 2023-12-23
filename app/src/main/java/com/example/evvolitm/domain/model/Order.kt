package com.example.evvolitm.domain.model

data class Order(
    val paymentOption: String,
    val customerName: String,
    val phone: String,
    val deliveryDate: String,
    val email: String?,
    val cart: MutableMap<String, Int>
)
