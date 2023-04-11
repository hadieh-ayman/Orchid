package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.CreditCard
import com.example.project.model.room.entity.Product
import com.example.project.model.room.entity.Voucher
import com.example.project.model.room.repository.VoucherRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VoucherViewModel(val context: Application) : AndroidViewModel(context){
    private val voucherRepo = VoucherRepository(context)
    private var voucherDocumentListener: ListenerRegistration? = null

    private val _voucher =
        MutableStateFlow(Voucher("","", 0.0))
    val voucher: StateFlow<Voucher> = _voucher.asStateFlow()

    fun setCurrentVoucher(voucher: Voucher) {
        _voucher.value = voucher
    }

    private var _vouchers = MutableStateFlow<List<Voucher>>(emptyList())
    var vouchers: StateFlow<List<Voucher>> = _vouchers.asStateFlow()

    init {
        getAllVouchers()
    }

    fun getAllVouchers() {
        viewModelScope.launch(Dispatchers.IO) {
            _vouchers.value = voucherRepo.getAllVouchers()
        }
    }

    fun getVoucher(voucherCode : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _voucher.value = voucherRepo.getVoucher(voucherCode)[0]
        }
    }

    fun addVoucher(voucher: Voucher) = viewModelScope.launch(Dispatchers.IO) {
        voucherRepo.addVoucher(voucher)
    }

    private fun registerCardListener() {
        voucherDocumentListener = voucherRepo.voucherDocumentRef.whereEqualTo(
            "accountId",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            _vouchers.value = snapshot!!.toObjects(Voucher::class.java)
        }

    }
}