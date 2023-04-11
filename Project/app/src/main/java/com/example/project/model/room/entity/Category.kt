package com.example.project.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//@Entity(
//    tableName = "category_table"
//)
data class Category(
    var categoryId: String  = "",
    val categoryName: String,
    val categoryImg: String
)
{
    constructor() : this(categoryId = "",
        categoryName = "", categoryImg = ""
    )
}