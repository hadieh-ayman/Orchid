package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.repository.CartItemRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartItemViewModel(val context: Application) : AndroidViewModel(context) {
    private val cartItemRepo = CartItemRepository(context)
    var _cartItem = MutableStateFlow(CartItem("", 0, "", "", false, 0.0, "", "", ""))

    val cartItem: StateFlow<CartItem> = _cartItem.asStateFlow()
    var cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    var allCartItems = MutableStateFlow<List<CartItem>>(emptyList())

    private var cartItemDocumentListener: ListenerRegistration? = null

    fun setCartItem(cartItem: CartItem) {
        _cartItem.value = cartItem
    }

    fun updateCartItem(
        newQuantity: Int,
        newColor: String,
        newSize: String
    ) {
        _cartItem.value = _cartItem.value.copy(
            quantity = newQuantity,
            color = newColor,
            size = newSize
        )
    }

    init{
        getAllCartItemsEver()
    }

    fun getAllCartItemsEver(){
        viewModelScope.launch {
            allCartItems.value = cartItemRepo.getAllCartItemsEver()
        }
    }

    fun getAllBoughtItems(accountId: String) {
        viewModelScope.launch {
            cartItems.value = cartItemRepo.getBoughtItemsByAccount(accountId)
            //registerCartItemListener()
        }
    }

//    fun getCartItemsByOrder(orderId: String){
//        viewModelScope.launch {
//            cartItemOrder.value = cartItemRepo.getCartItemByOrder(orderId)
//        }
//    }

    fun getAllCartItems(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cartItems.value = cartItemRepo.getAllCartItems(accountId)
            //registerCartItemListener()
        }
    }

    fun getCartItem(cartItemId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _cartItem.value = cartItemRepo.getCartItem(cartItemId)[0]
            registerCartItemListener()
        }
    }

    fun addCartItem(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartItemRepo.addCartItem(cartItem)
        }
    }

    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            Log.d(ContentValues.TAG, "deleteCartItem: is called $cartItem")
            cartItemRepo.deleteCartItem(cartItem)
        }
        getAllCartItems(cartItem.account)
    }

    fun updateCartItem(updatedCartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(ContentValues.TAG, updatedCartItem.toString())
        cartItemRepo.updateCartItem(updatedCartItem)
    }

    fun getCartItemProduct(productId: Int, cartItemId: Int,color:String,size:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _cartItem.value = cartItemRepo.getCartItemProduct(productId, cartItemId,color,size)[0]
        }
    }


    private fun registerCartItemListener() {
        cartItemDocumentListener = cartItemRepo.cartItemDocumentRef
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.d(ContentValues.TAG, error.message.toString())
                    return@addSnapshotListener
                }
                cartItems.value = snapshot!!.toObjects(CartItem::class.java)
            }

    }

}