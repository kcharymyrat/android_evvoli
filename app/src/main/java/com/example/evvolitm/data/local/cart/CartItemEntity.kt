package com.example.evvolitm.data.local.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.evvolitm.domain.model.Cart

@Entity(
    tableName = "cart_items",
    foreignKeys = [
        ForeignKey(
            entity = CartEntity::class,
            parentColumns = ["cartId"],
            childColumns = ["cartOwnerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CartItemProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "productId") val productId: String,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "cartOwnerId") val cartOwnerId: Long
)
