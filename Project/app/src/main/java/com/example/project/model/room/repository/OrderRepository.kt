package com.example.project.model.room.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Order
import com.example.project.model.room.remote.repo.ApiRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import android.content.ContentValues.TAG
import com.example.project.model.room.entity.CreditCard
import com.example.project.model.room.entity.Product

class OrderRepository(context: Context) {
    val db by lazy { Firebase.firestore }

    val orderDocumentRef by lazy { db.collection("orders") }

    suspend fun getOrder(orderId: String): List<Order> =
        orderDocumentRef.whereEqualTo("orderId", orderId)
            .get().await()
            .toObjects(Order::class.java)

    suspend fun addOrder(order: Order) : String {
        val documentId = orderDocumentRef.document().id
        order.orderId = documentId
        orderDocumentRef.document(documentId).set(order).await()
        return order.orderId
    }

    suspend fun getAllOrders(): List<Order> = db
        .collection("orders")
        .get().await().toObjects(Order::class.java)

    suspend fun getAllAccountOrders(accountId: String): List<Order> = orderDocumentRef
        .whereEqualTo("account", accountId)
        .get().await().toObjects(Order::class.java)

    suspend fun updateOrder(updatedOrder: Order) = orderDocumentRef
        .document(updatedOrder.orderId)
        .set(updatedOrder)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully updated")
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(TAG, it1) }
        }

    suspend fun deleteOrder(order: Order) =
        orderDocumentRef.document(order.orderId.toString()).delete().await()

    suspend fun getOrderCount() = getAllOrders().size

    init {
        runBlocking {
            this.launch(Dispatchers.IO) {
                initialize(context)
            }
        }
    }

    suspend fun initialize(context: Context): Unit {
        var orders = listOf<Order>()
        if (getOrderCount() == 0) {
            orders = ApiRepo.OrderRepo.getAllOrders();
            orders.forEach {
                runBlocking {
                    this.launch {
                        addOrder(it)
                    }
                }
            }
        }
    }
}