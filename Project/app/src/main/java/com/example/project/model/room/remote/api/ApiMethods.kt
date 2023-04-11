package com.example.project.model.room.remote.api

import com.example.project.model.room.entity.*
import retrofit2.http.GET

interface ApiMethods {
    @GET("accounts")
    suspend fun getAllAccounts() : List<Account>
    @GET("categories")
    suspend fun getAllCategories() : List<Category>
    @GET("products")
    suspend fun getAllProducts() : List<Product>
    @GET("cards")
    suspend fun getAllCards() : List<CreditCard>
    @GET("orders")
    suspend fun getAllOrders() : List<Order>
    @GET("vouchers")
    suspend fun getAllVouchers() : List<Voucher>

}
