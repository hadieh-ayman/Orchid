package com.example.project.viewModels

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Product
import com.example.project.model.room.entity.Review
import com.example.project.model.room.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(val context: Application) : AndroidViewModel(context) {
    private val productRepo = ProductRepository(context)
    //one product
    private var productDocumentListener: ListenerRegistration? = null

    private val _pEdit =
        MutableStateFlow(Product( "", 0.0, "", 0.0, 0, 0.0,0, "",""))
    val productEdit: StateFlow<Product> = _pEdit.asStateFlow()

    var productss = MutableStateFlow<List<Product>>(emptyList())

    var search = "xxx"
    fun setSearchWord(searchWord: String) {
        search = searchWord
    }

    var min = 0.0
    fun setMinValue(minValue: Double){
        min = minValue
    }

    var max = 0.0
    fun setMaxValue(maxValue: Double){
        max = maxValue
    }
    private val _product =
        MutableStateFlow(Product( "", 0.0, "", 0.0, 0, 0.0,0, "",""))
    val product: StateFlow<Product> = _product.asStateFlow()
    private val _ProductView =
        MutableStateFlow(Product( "", 0.0, "", 0.0, 0, 0.0,0, "",""))
    val ProductView: StateFlow<Product> = _ProductView.asStateFlow()

    fun setCurrentProduct(product: Product) {
        _product.value = product
    }
    fun updateCurrentProduct(category: String, url: String, name: String, price: Double, quantity: Int, discount: Double){
        _product.value = _product.value.copy(productName= name, price= price, image = url, category = category, stock = quantity, discount = discount)
    }

    fun updateName(name: String) {
        _product.value = _product.value.copy(productName = name)
    }

    fun updateStock(updatedStock: Int) {
        _product.value = _product.value.copy(stock = updatedStock)
    }

    fun updatePrice(updatedPrice: Double) {
        _product.value = _product.value.copy(price = updatedPrice)
    }

    fun updateImage(updatedImage: String) {
        _product.value = _product.value.copy(image = updatedImage)
    }

    //list of products
    private var _products = MutableStateFlow(emptyList<Product>())
    var products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.value = productRepo.getAllProducts()
            //registerProductListener()
        }
    }

    fun getProduct(productId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _product.value = productRepo.getProduct(productId)[0]
                //registerProductListener()
        }
    }

    fun addProduct(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        productRepo.addProduct(product)
    }

    fun updateProduct(updatedProduct: Product) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, updatedProduct.toString())
        productRepo.updateProduct(updatedProduct)
    }

   fun deleteProduct(product : Product) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "deleteProduct: is called $product")
       productRepo.deleteProduct(product)
    }

    private fun registerProductListener() {
        productDocumentListener = productRepo.productDocumentRef.whereEqualTo(
            "username",
            FirebaseAuth.getInstance().currentUser?.email
        ).addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, error.message.toString())
                return@addSnapshotListener
            }
            productss.value = snapshot!!.toObjects(Product::class.java)
        }

    }
}
