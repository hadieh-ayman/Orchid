package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Address
import com.example.project.model.room.repository.AddressRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressViewModel(val context: Application) : AndroidViewModel(context){
    private val addressRepo = AddressRepository(context)

    private var addressDocumentListener: ListenerRegistration? = null

    private val _address = MutableStateFlow(Address("", "","", "", 1, ""))
    val address: StateFlow<Address> = _address.asStateFlow()
    private var _addresses = MutableStateFlow<List<Address>>(emptyList())
    var addresses: StateFlow<List<Address>> = _addresses.asStateFlow()

    fun setAddress(address: Address) {
        _address.value = address
    }

    fun getAddress(addressId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _addresses.value = addressRepo.getAddress(addressId)
            //registerAddressListener()
        }
    }

    fun getAllAddresses(accountId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _addresses.value = addressRepo.getAllAddresses(accountId)
            //registerAddressListener()
        }
    }

    fun addAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepo.addAddress(address)
        }
    }

    fun updateAddress(updatedAddress: Address){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, updatedAddress.toString())
            addressRepo.updateAddress(updatedAddress)
        }
    }

    fun deleteAddress(address: Address) = viewModelScope.launch {
        Log.d(TAG, "deleteAddress: is called $address")
        addressRepo.deleteAddress(address)
    }

    private fun registerAddressListener() {
        addressDocumentListener = addressRepo.addressDocumentRef.whereEqualTo(
            "addressId",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            _addresses.value = snapshot!!.toObjects(Address::class.java)
        }

    }
}