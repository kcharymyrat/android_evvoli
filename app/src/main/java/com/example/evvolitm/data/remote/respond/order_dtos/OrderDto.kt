package com.example.evvolitm.data.remote.respond.order_dtos

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("customer_name") val customerName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("delivery_date") val deliveryDate: String,
    @SerializedName("payment_option") val paymentOption: String,
    val cart: MutableMap<String, Int>  // productId, quantity
)
