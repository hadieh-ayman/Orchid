package com.example.project.model.room.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.project.R
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CartItemRepository(context: Context) {
    val db by lazy { Firebase.firestore }

    val cartItemDocumentRef by lazy { db.collection("cartItems") }

    suspend fun getCartItem(cartItemId: String): List<CartItem> =
        cartItemDocumentRef.whereEqualTo("productId", cartItemId)
            .get().await()
            .toObjects(CartItem::class.java)

    suspend fun getAllCartItemsEver() : List<CartItem> = db
        .collection("cartItems").get().await().toObjects(CartItem::class.java)

    suspend fun getAllCartItems(account: String): List<CartItem> = cartItemDocumentRef.whereEqualTo("checkedOut", false)
        .whereEqualTo("account", account)
        .get().await().toObjects(CartItem::class.java)

    suspend fun getBoughtItemsByAccount(account: String) : List<CartItem> = cartItemDocumentRef
        .whereEqualTo("account", account)
        .whereEqualTo("checkedOut", true).get().await().toObjects(CartItem::class.java)

//    suspend fun getCartItemByOrder(order: String): List<CartItem> = cartItemDocumentRef
//        .whereEqualTo("order", order)
//        .whereEqualTo("checkedOut",true).get().await().toObjects(CartItem::class.java)

    suspend fun getCartItemProduct(productId: Int, accountId: Int,color:String, size:String) : List<CartItem> =
        db.collection("cartItems").whereEqualTo("product",productId)
            .whereEqualTo("account",accountId).whereEqualTo("color", color).whereEqualTo("size", size)
            .get().await().toObjects(CartItem::class.java)

    suspend fun addCartItem(cartItem: CartItem) {
        val documentId = cartItemDocumentRef.document().id
        cartItem.cartItemId = documentId
        cartItemDocumentRef.document(documentId).set(cartItem).await()
    }

    suspend fun deleteCartItem(cartItem: CartItem) =
        cartItemDocumentRef.document(cartItem.cartItemId).delete().await()

    suspend fun updateCartItem(updatedCartItem: CartItem) = cartItemDocumentRef
        .document(updatedCartItem.cartItemId)
        .set(updatedCartItem)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully updated")
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(TAG, it1) }
        }

//    fun getCartItemProduct(productId: Int, accountId: Int,color:String, size:String) : Flow<CartItem> = cartItemDao.getCartItemProduct(productId,accountId,false,color,size)


}
