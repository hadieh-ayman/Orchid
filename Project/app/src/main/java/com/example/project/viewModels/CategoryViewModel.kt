package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Product
import com.example.project.model.room.repository.CategoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(val context: Application) : AndroidViewModel(context) {
    private val categoryRepo = CategoryRepository(context)
    private val _category = MutableStateFlow(Category("", "", ""))

    val category: StateFlow<Category> = _category.asStateFlow()

    private var categoryDocumentListener: ListenerRegistration? = null
    var categoryy = MutableStateFlow<List<Category>>(emptyList())
    //
    private var _categories = MutableStateFlow(emptyList<Category>())
    var categories: StateFlow<List<Category>> = _categories.asStateFlow()

    fun setCategory(category: Category) {
        _category.value = category
    }
    init {
        getAllCategories()
    }

    fun getAllCategories() {
        viewModelScope.launch {
            _categories.value = categoryRepo.getAllCategories()
                //registerCategoryListener()
        }
    }

    fun getCategory(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _category.value = categoryRepo.getCategory(categoryId)[0]
            //registerCategoryListener()
        }
    }

    fun addCategory(category: Category) =
        viewModelScope.launch(Dispatchers.IO) {
        categoryRepo.addCategory(category)
    }

    fun updateCategory(updatedCategory: Category) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(ContentValues.TAG, updatedCategory.toString())
        categoryRepo.updateCategory(updatedCategory)
    }


    fun deleteCategory(category: Category) = viewModelScope.launch {
        Log.d(TAG, "deleteAddress: is called $category")
        categoryRepo.deleteCategory(category)
    }

    //    fun deleteAllCategories() {
//        viewModelScope.launch(Dispatchers.IO) {
//            categoryRepo.deleteAllCategories()
//        }
//    }
    private fun registerCategoryListener() {
        categoryDocumentListener = categoryRepo.categoryDocumentRef.whereEqualTo(
            "categoryId",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            categoryy.value = snapshot!!.toObjects(Category::class.java)
        }

    }

}