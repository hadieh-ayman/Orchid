package com.example.project.model.room.entity

import androidx.room.*

//@Entity(
//    tableName = "order_table",
//    foreignKeys = [
//        ForeignKey(
//            entity = Account::class,
//            parentColumns = arrayOf("accountId"),
//            childColumns = arrayOf("account"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        ),
////        ForeignKey(
////            entity = CreditCard::class,
////            parentColumns = arrayOf("cardId"),
////            childColumns = arrayOf("card"),
////            onUpdate = ForeignKey.CASCADE,
////            onDelete = ForeignKey.CASCADE
////        ),
//        ForeignKey(
//            entity = Voucher::class,
//            parentColumns = arrayOf("voucherCode"),
//            childColumns = arrayOf("voucher"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
data class Order(
    var orderId: String= "",
    var status: String,
    val datePlaced: String,
    val estDelivery: String,
    var total: Double,
    var paymentMode: String,
    val account: String,
    var card: String,
    val voucher: String
){
    constructor() : this(orderId = "",
        status = "", datePlaced = "", estDelivery = "", total = 0.0, paymentMode = "",
        account = "", card = "", voucher=""
    )
}
//
//data class OrderWithCartItem(
//    @Embedded
//    val order: Order,
//    @Relation(
//        parentColumn = "orderId",
//        entityColumn = "cartItemId",
//    )
//    val cartItems: List<CartItem>
//)