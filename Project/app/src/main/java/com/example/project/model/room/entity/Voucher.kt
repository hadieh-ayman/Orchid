package com.example.project.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//@Entity(
//    tableName = "voucher_table"
//)
data class Voucher(
    var voucherId: String = "",
    val voucherCode: String,
    val percentage: Double
){
        constructor() : this(voucherId="",voucherCode = "",
        percentage = 0.0
        )
}