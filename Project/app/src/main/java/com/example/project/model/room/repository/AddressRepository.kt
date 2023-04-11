package com.example.project.model.room.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Address
import com.example.project.model.room.entity.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddressRepository(context: Context) {

    val db by lazy { Firebase.firestore }

    val addressDocumentRef by lazy { db.collection("address") }

    suspend fun getAddress(addressId: String): List<Address> =
        addressDocumentRef.whereEqualTo("addressId", addressId)
            .get().await().toObjects(Address::class.java)

    suspend fun addAddress(address: Address) {
        val documentId = addressDocumentRef.document().id
        address.addressId = documentId
        addressDocumentRef.document(documentId).set(address).await()
    }

    suspend fun getAllAddresses(accountId: String): List<Address> = db
        .collection("address").whereEqualTo("account", accountId)
        .get().await().toObjects(Address::class.java)

    suspend fun updateAddress(updatedAddress: Address){
        addressDocumentRef
            .document(updatedAddress.addressId)
            .set(updatedAddress)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully updated")
            }.addOnFailureListener {
                it.message?.let { it1 -> Log.d(TAG, it1) }
            }
    }

    suspend fun deleteAddress(address: Address) {
        addressDocumentRef.document(address.addressId).delete().await()
    }


}