package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.CreditCard
import com.example.project.model.room.repository.CardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardViewModel(val context: Application) : AndroidViewModel(context) {
    private val cardRepo = CardRepository(context)
    private var cardDocumentListener: ListenerRegistration? = null

    private val _card = MutableStateFlow(CreditCard("", "", "", "", "", ""))
    val card: StateFlow<CreditCard> = _card.asStateFlow()

    fun setCard(card: CreditCard) {
        _card.value = card
    }

    private var _cards = MutableStateFlow(emptyList<CreditCard>())
    var cards: StateFlow<List<CreditCard>> = _cards.asStateFlow()


    fun getAllCards(account: String) {
        viewModelScope.launch(Dispatchers.IO) {
        _cards.value = cardRepo.getAllCards(account)
        //registerCardListener()
        }
    }

    fun getCard(cardId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _cards.value = cardRepo.getCard(cardId.toString())
            //registerCardListener()
        }
    }

    fun addCard(card: CreditCard) =
        viewModelScope.launch(Dispatchers.IO) {
            cardRepo.addCard(card)
        }

    fun updateCard(card: CreditCard) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(ContentValues.TAG, card.toString())
            cardRepo.updateCard(card)
        }
    }

    fun deleteCard(card: CreditCard) {
        viewModelScope.launch {
            Log.d(ContentValues.TAG, "deletCard: is called $card")
            cardRepo.deleteCard(card)
        }
    }


    private fun registerCardListener() {
        cardDocumentListener = cardRepo.cardDocumentRef.whereEqualTo(
            "accountId",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            _cards.value = snapshot!!.toObjects(CreditCard::class.java)
        }

    }
}