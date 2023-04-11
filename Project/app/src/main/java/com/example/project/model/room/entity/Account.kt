package com.example.project.model.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable


data class Account(
    var accountId: String = "",
    val accountType: String,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
){
    constructor() : this(accountId = "", accountType = "", username = "", email = "", phone = "", password = "")
}
