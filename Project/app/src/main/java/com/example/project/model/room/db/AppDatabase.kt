/*
package com.example.project.model.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project.model.room.remote.api.*
import com.example.project.model.room.entity.*

abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): ApiMethods
//    abstract fun addressDao(): AddressDao
//    abstract fun creditCardDao(): CreditCardDao
//    abstract fun cartItemDoa(): CartItemDao
//    abstract fun cartDao(): CartDao
//    abstract fun categoryDao(): CategoryApi
//    abstract fun orderDao(): OrderDao
//    abstract fun productDao(): ProductApi
//    abstract fun reviewDao(): ReviewDao
//    abstract fun voucherDao(): VoucherDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "app_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
*/
