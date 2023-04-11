package com.example.project.model.room.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Address
import com.example.project.model.room.entity.Product
import com.example.project.model.room.remote.repo.ApiRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class AccountRepository(context: Context) {
    val TAG = "AccountRepo"

    val db by lazy { Firebase.firestore }
    val accountDocumentRef by lazy { db.collection("accounts") }

    suspend fun getAccount(username: String): List<Account> =
       accountDocumentRef.whereEqualTo("username",username )
            .get().await()
            .toObjects(Account::class.java)

    suspend fun getAccountbyEmail(email: String): List<Account> =
        accountDocumentRef.whereEqualTo("email", email)
            .get().await()
            .toObjects(Account::class.java)

    suspend fun addAccount(account: Account) {
         val documentId = accountDocumentRef.document().id
         account.accountId = documentId
         accountDocumentRef.document(documentId.toString()).set(account).await()
     }

    suspend fun getAllAccounts(): List<Account> = db
        .collection("accounts")
        .get().await().toObjects(Account::class.java)

    suspend fun updateAccount(updatedAccount: Account) = accountDocumentRef
        .document(updatedAccount.accountId)
        .set(updatedAccount)
        .addOnSuccessListener {
            Log.d(TAG, "Successfully updated")
        }.addOnFailureListener {
            it.message?.let { it1 -> Log.d(TAG, it1) }
        }

    suspend fun deleteAccount(account: Account) =
        accountDocumentRef.document(account.accountId).delete().await()

    suspend fun getAccountCount() = getAllAccounts().size


    init {
        val settings = firestoreSettings { isPersistenceEnabled = true }
        db.firestoreSettings = settings
         runBlocking {
            this.launch(Dispatchers.IO) {
                 initialize(context)
            }
        }
    }

     suspend fun initialize(context: Context): Unit {
        var accounts = listOf<Account>()
        if (getAccountCount() == 0) {
            runBlocking {
                accounts = ApiRepo.AccountRepo.getAllAccounts()
            }
            accounts.forEach {
                runBlocking {
                    this.launch {
                        addAccount(it)
                    }
                }
            }
        }
    }

//    init {
//        runBlocking {
//            this.launch(Dispatchers.IO) {
//               // initFromJson(context)
//            }
//        }
//    }

   /* suspend fun initFromJson(context: Context): Unit {
        if (roomDb == null) {
            return
        }
        if (accountDao == null) {
            return
        }
        var accounts = listOf<Account>()
        if (accountDao.getAccountCount() == 0) {
            val json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
            val accountsJson = context.resources.openRawResource(R.raw.accounts).bufferedReader()
                .use {
                    it.readText()
                }
            accounts = json.decodeFromString(accountsJson)
            accounts.forEach {
                runBlocking {
                    this.launch {
                        val address = addressRepository.getAddress(it.address)
                        if (address != null) {
                            accountDao.addAccount(it)
                        }
                    }
                }
            }
            }
        }*/
}
