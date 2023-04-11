package com.example.project.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = Category::class,
//            parentColumns = arrayOf("categoryId"),
//            childColumns = arrayOf("category"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
//    tableName = "product_table"
//)
data class Product(
    var productName: String,
    var price: Double,
    var image: String,
    val rating: Double,
    val totalReviews: Int,
    var discount: Double,
    var stock: Int,
    var category: String,
    var productId: String = "",

    ){
        constructor() : this(productName = "",
        price = 0.0, image = "", rating = 0.0, totalReviews = 0, discount = 0.0,
        stock = 0, category = "", productId = ""
    )
}



