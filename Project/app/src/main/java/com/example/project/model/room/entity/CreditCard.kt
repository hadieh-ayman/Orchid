package com.example.project.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//@Entity(
//    tableName = "card_table",
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
data class CreditCard(
    var cardId: String = "",
    val cardNo: String,
    val expDate: String,
    val ccv: String,
    val cardHolder: String,
    val account: String
){
    constructor() : this(cardId = "",
        cardNo = "", expDate = "", ccv = "", cardHolder = "",
        account=""
    )
}