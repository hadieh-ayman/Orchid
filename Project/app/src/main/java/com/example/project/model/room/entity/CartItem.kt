package com.example.project.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
//
//@Entity(
//    tableName = "cartItem_table",
//    foreignKeys = [
//        ForeignKey(
//            entity = Product::class,
//            parentColumns = arrayOf("productId"),
//            childColumns = arrayOf("product"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        ),
////        ForeignKey(
////            entity = Cart::class,
////            parentColumns = arrayOf("cartId"),
////            childColumns = arrayOf("cart"),
////            onUpdate = ForeignKey.CASCADE,
////            onDelete = ForeignKey.CASCADE
////        ),
////        ForeignKey(
////            entity = Order::class,
////            parentColumns = arrayOf("orderId"),
////            childColumns = arrayOf("order"),
////            onUpdate = ForeignKey.CASCADE,
////            onDelete = ForeignKey.CASCADE
////        )
//    ]
//)

data class CartItem(
    var cartItemId: String = "",
    var quantity: Int,
    val color: String,
    val size: String,
    var checkedOut: Boolean=false,
    val price: Double,
    val product: String,
    val account: String,
    var order: String,
){
    constructor() : this(quantity = 0, color = "", size = "", checkedOut = false, price = 0.0, product = "", account = "", order = "", cartItemId = "")

}