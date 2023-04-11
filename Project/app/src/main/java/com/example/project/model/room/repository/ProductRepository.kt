package com.example.project.model.room.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.project.model.room.entity.Product
import com.example.project.model.room.remote.repo.ApiRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ProductRepository(context: Context) {
    val db by lazy { Firebase.firestore }

    val productDocumentRef by lazy { db.collection("products") }

    suspend fun getProduct(productId: String): List<Product> =
        productDocumentRef.whereEqualTo("productId", productId)
            .get().await().toObjects(Product::class.java)

    suspend fun getAllProducts(): List<Product> = db
        .collection("products")
        .get().await().toObjects(Product::class.java)

    suspend fun addProduct(product: Product) {
        val documentId = productDocumentRef.document().id
        product.productId = documentId
        productDocumentRef.document(documentId).set(product).await()
    }

    suspend fun getProductCount() = getAllProducts().size


    suspend fun updateProduct(updatedProduct: Product) = productDocumentRef
        .document(updatedProduct.productId)
        .set(updatedProduct)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully updated")
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(TAG, it1) }
        }

    suspend fun deleteProduct(product: Product) =
        productDocumentRef.document(product.productId).delete().await()

    init {
        val settings = firestoreSettings { isPersistenceEnabled = true }
        db.firestoreSettings = settings
        runBlocking {
            this.launch(Dispatchers.IO) {
                initialize(context)
            }
        }
    }

    suspend fun initialize(context: Context): Unit {
        var products = listOf<Product>()
        if (getProductCount() == 0) {
            products = ApiRepo.ProductRepo.getAllProducts();
            products.forEach {
                runBlocking {
                    this.launch {
                        addProduct(it)
                    }
                }
            }
        }
    }
}


/*
   suspend fun deleteAllProducts() = productDao.deleteAllProducts()
*/