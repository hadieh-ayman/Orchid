package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Review
import com.example.project.model.room.repository.ReviewRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReviewViewModel(val context: Application) : AndroidViewModel(context) {
    private val reviewRepo = ReviewRepository(context)
    private val _review = MutableStateFlow(Review("", "", "", 0, "", ""))

    val review: StateFlow<Review> = _review.asStateFlow()

    fun setCurrentReview(review: Review) {
        _review.value = review
    }

    private var reviewDocumentListener: ListenerRegistration? = null


    private var _reviews = MutableStateFlow(emptyList<Review>())
    var reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private var _Previews = MutableStateFlow(emptyList<Review>())
    var ProductReviews: StateFlow<List<Review>> = _Previews.asStateFlow()

    private var _PAreview = MutableStateFlow(emptyList<Review>())
    var ProductAccountReview: StateFlow<List<Review>> = _PAreview.asStateFlow()

   // private val _PAreview =
       // MutableStateFlow(Review(0, "", "", 0, 0, 0))
    //val ProductAccountReview: StateFlow<Review> = _PAreview.asStateFlow()


    init {
        getAllReviews()
    }

    fun getAllReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            _reviews.value = reviewRepo.getAllReviews()
            //registerReviewListener()
        }
    }

    fun getReviewsOfProduct(productId: String) {
        _Previews.value = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            _Previews.value = reviewRepo.getReviewsOfProduct(productId)
            //registerListenerProduct(productId)
        }

    }

    fun getAccountProductReview(productId: String, accountId: String) {
        _PAreview.value = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            _PAreview.value = reviewRepo.getAccountProductReview(productId, accountId)
            //registerListenerProductAccount(productId, accountId)

        }
    }



    fun addReview(review: Review) = viewModelScope.launch(Dispatchers.IO) {
            reviewRepo.addReview(review)
        }


        fun updateReview(review: Review) {
            viewModelScope.launch(Dispatchers.IO) {
            reviewRepo.updateReview(review)
            }
        }

        fun deleteReview(review: Review) {
            viewModelScope.launch(Dispatchers.IO) {
          reviewRepo.deleteReview(review)
            }
            getReviewsOfProduct(review.product)
        }


    private fun registerReviewListener() {
        reviewDocumentListener = reviewRepo.reviewDocumentRef.whereEqualTo(
            "reviewId",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            _reviews.value = snapshot!!.toObjects(Review::class.java)
        }

    }


    private fun registerListenerProductAccount(productId: String, accountId: String) {
        reviewDocumentListener =
            reviewRepo.reviewDocumentRef
                .whereEqualTo("productId", productId)
                .whereEqualTo("accountId", accountId)
                .orderBy("priority", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) return@addSnapshotListener
                    _Previews.value = snapshot!!.toObjects(Review::class.java)
                }
    }


    private fun registerListenerProduct(productId: String) {
        reviewDocumentListener =
            reviewRepo.reviewDocumentRef
                .document(productId)
                .collection("products")
                .orderBy("priority", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) return@addSnapshotListener
                    _Previews.value = snapshot!!.toObjects(Review::class.java)
                }
    }


    }
