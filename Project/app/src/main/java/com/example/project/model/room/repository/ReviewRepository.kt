package com.example.project.model.room.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Product
import com.example.project.model.room.entity.Review
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ReviewRepository(context: Context) {

    val db by lazy { Firebase.firestore }

    val reviewDocumentRef by lazy { db.collection("reviews") }


    suspend fun addReview(review: Review)  {
        val documentId = reviewDocumentRef.document().id
        review.reviewId = documentId
        reviewDocumentRef.document(documentId).set(review).await()
    }

    suspend fun getAllReviews(): List<Review> = db
        .collection("reviews")
        .get().await().toObjects(Review::class.java)


    suspend fun reviewCount() = getAllReviews().size


    suspend fun updateReview(review: Review) = reviewDocumentRef
        .document(review.reviewId.toString())
        .set(review)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully updated")
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(TAG, it1) }
        }


    suspend fun deleteReview(review: Review) =
        reviewDocumentRef.document(review.reviewId.toString()).delete().await()


    suspend fun getReviewsOfProduct(productId: String): List<Review> =
        reviewDocumentRef
            .whereEqualTo("productId", productId.toString())
            .get().await()
            .toObjects(Review::class.java)


    suspend fun getAccountProductReview(productId: String, accountId: String): List<Review> =
        reviewDocumentRef
            .whereEqualTo("productId", productId.toString())
            .whereEqualTo("accountId", accountId.toString())
            .get().await()
            .toObjects(Review::class.java)


    //suspend fun addReview(review: Review) = reviewDao.addReview(review)
   //suspend fun reviewCount(): Int = reviewDao.getReviewCount()
   // suspend fun getAllReviews(): Flow<List<Review>> = reviewDao.getAllReviews()
   //suspend fun getReviewsOfProduct(productId: Int): Flow<List<Review>> = reviewDao.getReviewsOfProduct(productId)
    //suspend fun  getAccountProductReview(productId: Int, accountId: Int): Flow<Review> = reviewDao.getAccountProductReview(productId, accountId)
    //suspend fun deleteReview(review: Review) = reviewDao.deleteReview(review)
   //suspend fun updateReview(review: Review) = reviewDao.updateReview(review)


    init {
        runBlocking {
            this.launch(Dispatchers.IO) {
            }
        }
    }



}