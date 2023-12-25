package com.example.evvolitm.presentation

sealed class OrderStatus {
    data object Idle : OrderStatus()
    class Success(val message: String) : OrderStatus()
    class Error(val errorMessage: String) : OrderStatus()
}