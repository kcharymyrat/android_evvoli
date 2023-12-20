package com.example.evvolitm.data.local.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "cartId") val id: Long = 0L
)
