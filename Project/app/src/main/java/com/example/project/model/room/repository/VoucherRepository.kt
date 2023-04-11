package com.example.project.model.room.repository

import android.content.Context
import com.example.project.model.room.entity.Review
import com.example.project.model.room.entity.Voucher
import com.example.project.model.room.remote.repo.ApiRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class VoucherRepository(context: Context) {
    val db by lazy { Firebase.firestore }

    val voucherDocumentRef by lazy { db.collection("vouchers") }

    suspend fun getVoucher(vid: String): List<Voucher> = voucherDocumentRef
        .whereEqualTo("voucherCode", vid)
        .get().await().toObjects(Voucher::class.java)

    suspend fun getAllVouchers(): List<Voucher> = voucherDocumentRef
        .get().await().toObjects(Voucher::class.java)

    suspend fun getVoucherCount() = getAllVouchers().size

    suspend fun addVoucher(voucher: Voucher){
        val documentId = voucherDocumentRef.document().id
        voucher.voucherId = documentId
        voucherDocumentRef.document(documentId).set(voucher).await()
    }

    init {
        runBlocking {
            this.launch(Dispatchers.IO) {
                initialize(context)
            }
        }
    }

    suspend fun initialize(context: Context): Unit {
        var vouchers = listOf<Voucher>()
        if (getVoucherCount() == 0) {
            runBlocking {
                vouchers =  ApiRepo.VouchersRepo.getAllVouchers()
            }
            vouchers.forEach {
                runBlocking {
                    this.launch {
                        addVoucher(it)
                    }
                }
            }
        }
    }
}