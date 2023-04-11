package com.example.project.model.room.remote.repo

import com.example.project.model.room.remote.api.ApiMethods

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepo {

        object AccountRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/6a9e88b4a5649b3a5a4392f524d144ab/raw/2a3f54588a8ad11b688e058b8b4117d7d4493fb0/"
            val accountApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllAccounts() = accountApi.getAllAccounts()
        }

        object CategoryRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/190e613b5e673d40abd820d343f7750f/raw/af366341671ef3376781b3bb410b6fe61e6d333a/"
            val categoryApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllCategories() = categoryApi.getAllCategories()
        }


        object ProductRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/99d7a748b4d72f619ae0af0355be0ab3/raw/e65afb13072bfaf487c9219a96f79d03e276eb5f/"
            val productApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllProducts() = productApi.getAllProducts()
        }

        object OrderRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/e3319e38b74a978bcb3a74e88bfd932c/raw/fd3c5fc95733eb331ac34d78f8119a20dffeccd3/"
            val orderApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllOrders() = orderApi.getAllOrders()
        }

        object CardRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/be6d93a2d5cf4944187afd902cb7734a/raw/71c43f5ff92b885ebefeb6767d6cab0e004e7045/"
            val cardApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllCards() = cardApi.getAllCards()
        }

        object VouchersRepo {
            val BASE_URL =
                "https://gist.githubusercontent.com/taqwa-te1901992/d94ed1887a52cbf27749a367b638c23c/raw/902ae31565b5f4ac90a560e0951f1bed34189b1a/"
            val vouchersApi by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiMethods::class.java)
            }

            suspend fun getAllVouchers() = vouchersApi.getAllVouchers()
        }

    }