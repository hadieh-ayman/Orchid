package com.example.project.model.room.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.project.model.room.entity.Category
import com.example.project.model.room.remote.repo.ApiRepo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class CategoryRepository(context: Context) {
    val db by lazy { Firebase.firestore }

    val categoryDocumentRef by lazy { db.collection("categories") }


    suspend fun getCategory(categoryId: String): List<Category> =
    categoryDocumentRef.whereEqualTo("categoryId", categoryId)
    .get().await()
    .toObjects(Category::class.java)



    suspend fun addCategory(category: Category){
        val documentId = categoryDocumentRef.document().id
        category.categoryId = documentId
        categoryDocumentRef.document(documentId).set(category).await()
    }

    suspend fun getAllCategories(): List<Category> = db
        .collection("categories")
        .get().await().toObjects(Category::class.java)

   suspend fun updateCategory(updatedCategory: Category) = db
       .document(updatedCategory.categoryId)
       .set(updatedCategory)
       .addOnSuccessListener {
           Log.d(ContentValues.TAG, "Successfully updated")
       }.addOnFailureListener {
           it.message?.let { it1 -> Log.d(ContentValues.TAG, it1) }
       }

    suspend fun getCategoryCount() = getAllCategories().size

    suspend fun deleteCategory(category: Category)  =
        categoryDocumentRef.document(category.categoryId).delete().await()


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
       var categories = listOf<Category>()
        if (getCategoryCount() == 0) {
            categories =  ApiRepo.CategoryRepo.getAllCategories();
            categories.forEach {
                runBlocking {
                    this.launch {
                        addCategory(it)
                    }
                }
            }
        }
    }

   // suspend fun getAccountCount() = getAllAccounts().size


//    private val roomDb by lazy {
//        AppDatabase.getInstance(context)
//    }
//    private val categoryDao by lazy {
//        roomDb.categoryDao()
//    }

    //fun categoryCount(): Int = categoryDao.getCategoryCount()
    //suspend fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
//    suspend fun getAllCategories(): MutableList<Category> = categoryDao.getAllCategories() as MutableList<Category>
//    suspend fun getCategory(categoryId: Int): Flow<Category> = categoryDao.getCategory(categoryId)
//    suspend fun addCategory(category: Category) = categoryDao.addCategory(category)
//    suspend fun deleteProduct(category: Category) = categoryDao.deleteCategory(category)
//    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
//    suspend fun getCategoryByName(categoryName: String): Flow<Category> =
//        categoryDao.getCategoryByName(categoryName)


//    init {
//        runBlocking {
//            this.launch(Dispatchers.IO) {
//                initFromJson(context)
//            }
//        }
//    }
//
//    suspend fun initFromJson(context: Context): Unit {
//        if (roomDb == null) {
//            return
//        }
//        if (categoryDao == null) {
//            return
//        }
//        var categories = listOf<Category>()
//        if(categoryDao.getCategoryCount() == 4)
//            addCategory(Category(categoryName = "All Products", categoryImg = "https://w7.pngwing.com/pngs/15/271/png-transparent-computer-icons-online-shopping-shopping-cart-service-shopping-cart-icon-text-service-retail.png"))
//        if (categoryDao.getCategoryCount() == 0) {
//            val json = Json {
//                ignoreUnknownKeys = true
//                coerceInputValues = true
//            }
//            val productsJson = context.resources.openRawResource(R.raw.categories).bufferedReader()
//                .use {
//                    it.readText()
//                }
//            categories = json.decodeFromString(productsJson)
//            categories.forEach {
//                runBlocking {
//                    this.launch {
//                        categoryDao.addCategory(it)
//                    }
//                }
//            }
//        }
//    }
}