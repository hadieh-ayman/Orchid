package com.example.project.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "address_table",
//    foreignKeys = [
//        ForeignKey(
//            entity = Account::class,
//            parentColumns = arrayOf("accountId"),
//            childColumns = arrayOf("account"),
//            onUpdate = ForeignKey.CASCADE,
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)

data class Address(
    var addressId: String = "",
    var addressLine: String,
    var city: String,
    var country: String,
    var zipcode: Int,
    var account: String
){
    constructor() : this(addressId = "", addressLine = "", city = "", country = "", zipcode = 0, account = "")

}