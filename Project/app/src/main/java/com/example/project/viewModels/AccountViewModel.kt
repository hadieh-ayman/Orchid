package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Review
import com.example.project.model.room.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(val context: Application) : AndroidViewModel(context) {

    private val accountRepo = AccountRepository(context)

    val _ReviewAccount = MutableStateFlow(Account("", "", "", "", "", ""))
    val reviewAccount: StateFlow<Account> = _ReviewAccount.asStateFlow()

    //one account
    private val _account = MutableStateFlow(Account("", "", "", "", "", ""))
    val account: StateFlow<Account> = _account.asStateFlow()

    //list of accounts
    private var _accounts =MutableStateFlow<List<Account>>(emptyList())
    var accounts: StateFlow<List<Account>> = _accounts.asStateFlow()

    private var accountDocumentListener: ListenerRegistration? = null

    fun updateCurrentAccount(name: String, mail: String, mobile: String) {
        _account.value = _account.value.copy(username = name).copy(email = mail).copy(phone = mobile)
    }

    init {
        getAllAccounts()
    }

    fun getAllAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            _accounts.value = accountRepo.getAllAccounts()
            //registerAccountListener()
        }
    }

    fun getAccount(accountId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _account.value = accountRepo.getAccount(accountId)[0]
                //registerAccountListener()
        }
    }
    fun getAccountByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _account.value = accountRepo.getAccountbyEmail(email)[0]
            //registerAccountListener()
        }
    }

    fun addAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepo.addAccount(account)
        }
    }

    fun updateAccount(updatedAccount: Account) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, updatedAccount.toString())
        accountRepo.updateAccount(updatedAccount)
    }

    fun deleteAccount(account: Account) = viewModelScope.launch {
        Log.d(TAG, "deleteAccount: is called $account")
        accountRepo.deleteAccount(account)
    }

    fun setCurrentAccount(account: Account) {
        _account.value = account
    }

    fun getCurrentAccount(): Account {
        return _account.value
    }

    private fun registerAccountListener() {
        accountDocumentListener = accountRepo.accountDocumentRef.whereEqualTo(
            "username",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(TAG, error.message.toString())
                return@addSnapshotListener
            }
            _accounts.value = snapshot!!.toObjects(Account::class.java)
        }

    }

    override fun onCleared() {
        super.onCleared()
        accountDocumentListener?.remove()

    }

    fun clearDate() {
        _accounts.value = emptyList()
    }
}