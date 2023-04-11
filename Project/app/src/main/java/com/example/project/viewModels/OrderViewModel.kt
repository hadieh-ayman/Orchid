package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Order
import com.example.project.model.room.repository.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class OrderViewModel(val context: Application) : AndroidViewModel(context) {

    private val orderRepo = OrderRepository(context)

    private val date = Date()
    private val _order = MutableStateFlow(Order("", "", "", "", 0.0, "", "", "", ""))

    val order: StateFlow<Order> = _order.asStateFlow()

    var orderss = MutableStateFlow<List<Order>>(emptyList())

    private var orderDocumentListener: ListenerRegistration? = null

    fun setCurrentOrder(order: Order) {
        _order.value = order
    }

    var status = ""
    fun setCurrentStatus(currentStatus: String){
        status = currentStatus
    }

    fun updateTotal(newTotal: Double) {
        _order.value = _order.value.copy(total = newTotal)
    }

    fun updateCurrentOrder(
        newStatus: String,
        newTotal: Double,
        newPaymentMode: String,
        newCard: String
    ) {
        _order.value = _order.value.copy(
            status = newStatus,
            total = newTotal,
            paymentMode = newPaymentMode,
            card= newCard
        )
    }

    var _orders = MutableStateFlow(emptyList<Order>())
    var orders: StateFlow<List<Order>> = _orders.asStateFlow()

    var _allOrders = MutableStateFlow(emptyList<Order>())
    var allOrders: StateFlow<List<Order>> = _allOrders.asStateFlow()


    init {
        getAllOrders()
    }

    fun getAllOrders() {
        viewModelScope.launch {
            _allOrders.value = orderRepo.getAllOrders()
        }
    }
    fun getAllAccountOrders(account: String) {
        viewModelScope.launch {
            _orders.value = orderRepo.getAllAccountOrders(account)
//            }
        }
    }

    fun getOrder(orderId: String){
        viewModelScope.launch(Dispatchers.IO) {
            orderss.value = orderRepo.getOrder(orderId)
            //registerOrderListener()
        }
    }

    fun addOrder(order: Order) = viewModelScope.launch(Dispatchers.IO) {
        val orderId = orderRepo.addOrder(order)
        _order.value = order
        _order.value.orderId = orderId
    }

    fun updateOrder(updatedOrder: Order) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(ContentValues.TAG, updatedOrder.toString())
        orderRepo.updateOrder(updatedOrder)
        orderRepo.getAllOrders()
    }

    fun deleteOrder(order: Order) = viewModelScope.launch {
        Log.d(ContentValues.TAG, "deleteOrder: is called $order")
        orderRepo.deleteOrder(order)
        getAllAccountOrders(order.account)
    }

    fun getCurrentOrder(): Order {
        return _order.value
    }

    fun deleteAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
//            orderRepo.deleteAllOrders()
        }
    }

    private fun registerOrderListener() {
        orderDocumentListener = orderRepo.orderDocumentRef.whereEqualTo(
            "username",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            orderss.value = snapshot!!.toObjects(Order::class.java)
        }

    }
}