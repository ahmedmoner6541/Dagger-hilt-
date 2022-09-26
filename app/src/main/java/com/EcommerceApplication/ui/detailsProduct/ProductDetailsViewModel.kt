package com.EcommerceApplication.ui.detailsProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.data.remote.response.addToCart.AddToCartResponse
import com.EcommerceApplication.data.remote.response.cartUpdate.CartUpdateQuantityResponse
import com.EcommerceApplication.data.remote.response.productDetailes.ProductDetailesResponse
import com.EcommerceApplication.repository.ProductRepository
import com.example.kotlinproject.ui.base.BaseViewModel
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository
): BaseViewModel(productRepository) {
    private val TAG = "ProductViewModel"


    private val _productDetails: MutableLiveData<Resource<ProductDetailesResponse>> = MutableLiveData()
    val productDetails: LiveData<Resource<ProductDetailesResponse>> get() = _productDetails

    private val _addProductToCart:MutableLiveData<Resource<AddToCartResponse>> = MutableLiveData()
    val addOrDeleteProductToCartById : LiveData<Resource<AddToCartResponse>> get() = _addProductToCart

    private val _updateProductQuantityInCart: MutableLiveData<Resource<CartUpdateQuantityResponse>> = MutableLiveData()
    val updateProductQuantityInCart: LiveData<Resource<CartUpdateQuantityResponse>> get() = _updateProductQuantityInCart


    fun setIdForProductDetails(id:String)= viewModelScope.launch {
        _productDetails.value = Resource.Loading
        _productDetails.value = productRepository.getproductDetails(id)
    }
    fun addOrDeleteProductToCartById(id: String) = viewModelScope.launch {
        _addProductToCart.value = Resource.Loading
        _addProductToCart.value = productRepository.addOrDeleteProductToCartById(id)
    }

    fun updateProductQuantityInCart(id:Int,totalquantity:Int) = viewModelScope.launch {
        _updateProductQuantityInCart.value = Resource.Loading
        _updateProductQuantityInCart.value = productRepository.updateProductQuantityInCart(id,totalquantity)
    }


/*
     //add
    private val _addToCart: MutableLiveData<Resource<AddToCartResponse>> = MutableLiveData()
    val addToCart: LiveData<Resource<AddToCartResponse>>
        get() = _addToCart
    fun addToCart(product_id:Int)= viewModelScope.launch {
        _addToCart.value = Resource.Loading
        _addToCart.value = cartRepository.addToCart(product_id)
    }

    //update
    private val _updateCarts: MutableLiveData<Resource<CartUpdateQuantityResponse>> = MutableLiveData()
    val updateProductQuantityInCart: LiveData<Resource<CartUpdateQuantityResponse>>
        get() = _updateCarts
    fun updateProductQuantityInCart(id:Int,totalquantity:Int) = viewModelScope.launch {
        _updateCarts.value = Resource.Loading
        _updateCarts.value = cartRepository.updateProductQuantityInCart(id,totalquantity)
    }


    private val _productDetails: MutableLiveData<Resource<ProductDetailesResponse>> = MutableLiveData()
    val productDetails: LiveData<Resource<ProductDetailesResponse>>
        get() = _productDetails

    fun getproductDetails(id:String)= viewModelScope.launch {
        _productDetails.value = Resource.Loading
        _productDetails.value = cartRepository.getproductDetails(id)
    }
*/
















}
