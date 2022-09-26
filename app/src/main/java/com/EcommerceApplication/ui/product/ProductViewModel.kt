package com.EcommerceApplication.ui.product
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.repository.ProductRepository
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.response.CartRespo
import com.EcommerceApplication.data.remote.response.addToCart.AddToCartResponse
import com.EcommerceApplication.data.remote.response.cartUpdate.CartUpdateQuantityResponse
import com.EcommerceApplication.data.remote.response.favorite.addInFavorite.FavoriteAddReposnse
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.FavoriteProductResponse
import com.EcommerceApplication.data.remote.response.product.ProductResponse
import com.EcommerceApplication.data.remote.response.searchProduct.SearchProductResponse
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {
    private   val TAG = "ProductViewModel"



    // get home date
    private val _productApi: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val productApi: LiveData<Resource<ProductResponse>>
        get() = _productApi

    fun getAllProductApi()= viewModelScope.launch {
        _productApi.value = Resource.Loading
        _productApi.value = productRepository.getAllProductApi()
    }

    //getSearchProductName
    private val _searchProductnameApi: MutableLiveData<Resource<SearchProductResponse>> = MutableLiveData()
    val searchProductnameApi: LiveData<Resource<SearchProductResponse>>
        get() = _searchProductnameApi

    fun getSearchProductName(productName: String)= viewModelScope.launch {
        _searchProductnameApi.value = Resource.Loading
        _searchProductnameApi.value = productRepository.getSearchProduct(productName)
    }

    // get favorite date
    private val _favoriteApi:  MutableLiveData<Resource<FavoriteProductResponse>> = MutableLiveData()
    val favoriteApi: LiveData<Resource<FavoriteProductResponse>>
        get() = _favoriteApi
    fun geFavorite()= viewModelScope.launch {
        _favoriteApi.value = Resource.Loading
        _favoriteApi.value = productRepository.getFavorite()
    }


    private val _addFavorite :MutableLiveData<Resource<FavoriteAddReposnse>> = MutableLiveData()
    val addFav:LiveData<Resource<FavoriteAddReposnse>>
        get()  = _addFavorite

    fun setProductToFavoriteById(productId: String)  = viewModelScope.launch{
        _addFavorite.value = Resource.Loading
        _addFavorite.value = productRepository.setProductToFavoriteById(productId)
    }
    private val _updateProductQuantityInCart: MutableLiveData<Resource<CartUpdateQuantityResponse>> = MutableLiveData()
    val updateProductQuantityInCart: LiveData<Resource<CartUpdateQuantityResponse>> get() = _updateProductQuantityInCart

    fun updateProductQuantityInCart(id:Int,totalquantity:Int) = viewModelScope.launch {
        _updateProductQuantityInCart.value = Resource.Loading
        _updateProductQuantityInCart.value = productRepository.updateProductQuantityInCart(id,totalquantity)
    }

    private val _productDB: MutableLiveData<List<Product>> = MutableLiveData()
    val productDB: LiveData<List<Product>>
        get() = _productDB





    private val _addProductToCart:MutableLiveData<Resource<AddToCartResponse>> = MutableLiveData()
    val addOrDeleteProductToCartById : LiveData<Resource<AddToCartResponse>> get() = _addProductToCart
    fun addOrDeleteProductToCartById(id: String) = viewModelScope.launch {
        _addProductToCart.value = Resource.Loading
        _addProductToCart.value = productRepository.addOrDeleteProductToCartById(id)
    }



    fun upsert(product: List<Product>) =  productRepository.upsert(product)

    fun getSavedAllProduct()  = productRepository.getSavedAllProduct()


    private val _cart:MutableLiveData<Resource<CartRespo>> = MutableLiveData()
    val cart :LiveData<Resource<CartRespo>> get() = _cart

    fun cart()=viewModelScope.launch {
        _cart.value = Resource.Loading
        _cart.value =  productRepository.getCart()
    }

/*
    // get home date
    private val _cartProductApi: MutableLiveData<Resource<CartResponse>> = MutableLiveData()
    val cartProductApi: LiveData<Resource<CartResponse>> get() = _cartProductApi

    fun getcartProductApi()= viewModelScope.launch {
        _cartProductApi.value = Resource.Loading
        _cartProductApi.value = productRepository.getCarts()
    }
*/








}



/*  fun getAllProduct()= viewModelScope.launch {
      _productDB.value = Resource.Loading
      _productDB.value = homeRepository.getAllProductDB()
  }


 private var productApiMutableLiveData = MutableLiveData<List<ProductResponse>>()
 val userApilivedata: LiveData<List<ProductResponse>> get() = productApiMutableLiveData//اول userMutableLiveData توصل حطها في userlivedata

 fun getAllProducts()=viewModelScope.launch {
     var result = homeRepository.getAllProduct()

     productApiMutableLiveData.postValue(result)
 }



    private val mutableLiveDatalist:MutableLiveData<List<Model>> = MutableLiveData()
    val user:LiveData<List<Model>> get() = mutableLiveDatalist

    fun getUser()= viewModelScope.launch {
        mutableLiveDatalist.postValue(homeRepository.myList())
    }
*/
