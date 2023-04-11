package com.example.project.model.room.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.project.R
import com.example.project.model.room.entity.Address
import com.example.project.model.room.entity.CreditCard
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CardRepository(context: Context) {

    val db by lazy { Firebase.firestore }

    val cardDocumentRef by lazy { db.collection("cards") }

    suspend fun getCard(cardId: String): List<CreditCard> =
        cardDocumentRef.whereEqualTo("cardId", cardId)
            .get().await().toObjects(CreditCard::class.java)

    suspend fun addCard(card: CreditCard) {
        val documentId = cardDocumentRef.document().id
        card.cardId = documentId
        cardDocumentRef.document(documentId).set(card).await()
    }

    suspend fun getAllCards(accountId: String): List<CreditCard> = cardDocumentRef
        .whereEqualTo("account", accountId)
        .get().await().toObjects(CreditCard::class.java)

    suspend fun updateCard(updatedCard: CreditCard) {
        cardDocumentRef
            .document(updatedCard.cardId)
            .set(updatedCard)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Successfully updated")
            }.addOnFailureListener {
                it.message?.let { it1 -> Log.d(ContentValues.TAG, it1) }
            }
    }

    suspend fun deleteCard(card: CreditCard) {
        cardDocumentRef
            .document(card.cardId)
            .delete()
            .await()
    }
}


//    init {
//        runBlocking {
//            this.launch(Dispatchers.IO) {
//                initFromJson(context)
//            }
//        }
//    }
//
//    suspend fun initFromJson(context: Context): Unit {
//        if (roomDb == null) {
//            return
//        }
//        if (cardDao == null) {
//            return
//        }
//        var cards = listOf<CreditCard>()
//        if (cardDao.getCardCount() == 0) {
//            val json = Json {
//                ignoreUnknownKeys = true
//                coerceInputValues = true
//            }
//            val cardsJson = context.resources.openRawResource(R.raw.cards).bufferedReader()
//                .use {
//                    it.readText()
//                }
//            cards = json.decodeFromString(cardsJson)
//            cards.forEach {
//                runBlocking {
//                    this.launch {
//                        val account = accountRepository.getAccountById(it.account)
//                        if (account != null) {
//                            cardDao.addCard(it)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}