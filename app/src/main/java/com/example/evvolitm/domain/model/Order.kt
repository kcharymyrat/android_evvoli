package com.example.evvolitm.domain.model

data class Order(
    val customerName: String,
    val phone: String,
    val shippingAddress: String,
    val deliveryTime: String,
    val paymentOption: String,
    val cart: MutableMap<String, Int>
)
