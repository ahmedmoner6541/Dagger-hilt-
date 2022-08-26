package com.EcommerceApplication.ui.product
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.repository.ProductRepository
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.response.addToCart.AddToCartResponse
import com.EcommerceApplication.data.remote.response.product.ProductResponse
import com.EcommerceApplication.data.remote.response.productDetailes.ProductDetailesResponse
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {
    private   val TAG = "ProductViewModel"
    private val _productApi: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val productApi: LiveData<Resource<ProductResponse>>
        get() = _productApi


    private val _productDetails: MutableLiveData<Resource<ProductDetailesResponse>> = MutableLiveData()
    val productDetails: LiveData<Resource<ProductDetailesResponse>>
        get() = _productDetails



   private val _addToCart: MutableLiveData<Resource<AddToCartResponse>> = MutableLiveData()
    val addToCart: LiveData<Resource<AddToCartResponse>>
           get() = _addToCart




    private val _productDB: MutableLiveData<List<Product>> = MutableLiveData()
    val productDB: LiveData<List<Product>>
        get() = _productDB



    fun upsert(product: List<Product>) =  productRepository.upsert(product)

    fun getSavedAllProduct()  = productRepository.getSavedAllProduct()


    fun getAllProductApi()= viewModelScope.launch {
        _productApi.value = Resource.Loading
        _productApi.value = productRepository.getAllProductApi()
    }


 /*   suspend fun addToCart(product_id: Int){
      //val  Request = AddToCartRequest(product_id)
        Log.d(TAG, "addToCart: ")
        homeRepository.addToCart(product_id)
    }
 */
    fun addToCart(product_id:Int)= viewModelScope.launch {
        _addToCart.value = Resource.Loading
        _addToCart.value = productRepository.addToCart(product_id)
    }




    fun getproductDetails(id:String)= viewModelScope.launch {
        _productDetails.value = Resource.Loading
        _productDetails.value = productRepository.getproductDetails(id)
    }




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
