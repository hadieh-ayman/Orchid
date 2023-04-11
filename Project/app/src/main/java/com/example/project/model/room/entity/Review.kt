package com.example.project.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "review_table",
//    foreignKeys = [
//        ForeignKey(
//            entity = Account::class,
//            parentColumns = arrayOf("accountId"),
//            childColumns = arrayOf("account"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Product::class,
//            parentColumns = arrayOf("productId"),
//            childColumns = arrayOf("product"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        ),
//    ]
//)

data class Review(
    var reviewId:String = "",
    val datePosted: String,
    val reviewText: String,
    val rating: Int,
    val account: String,
    val product: String,
){
    constructor() : this(reviewId = "", datePosted = "", reviewText = "", rating = 0, account = "", product = "")

}