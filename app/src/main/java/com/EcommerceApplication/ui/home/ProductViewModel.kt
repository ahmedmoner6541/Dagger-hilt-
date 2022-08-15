package com.EcommerceApplication.ui.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.repository.ProductRepository
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.response.product.ProductResponse
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductViewModel @Inject constructor(
    private val homeRepository: ProductRepository
): ViewModel() {
    private val _productApi: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val productApi: LiveData<Resource<ProductResponse>>
        get() = _productApi


    private val _productDB: MutableLiveData<List<Product>> = MutableLiveData()
    val productDB: LiveData<List<Product>>
        get() = _productDB




    fun upsert(product: List<Product>) {
        homeRepository.upsert(product)

    }


    fun getSavedAllProduct()  =
        homeRepository.getSavedAllProduct()





    fun getAllProductApi()= viewModelScope.launch {
        _productApi.value = Resource.Loading
        _productApi.value = homeRepository.getAllProductApi()
    }
  /*  fun getAllProduct()= viewModelScope.launch {
        _productDB.value = Resource.Loading
        _productDB.value = homeRepository.getAllProductDB()
    }
    */

}








/* private var productApiMutableLiveData = MutableLiveData<List<ProductResponse>>()
 val userApilivedata: LiveData<List<ProductResponse>> get() = productApiMutableLiveData//اول userMutableLiveData توصل حطها في userlivedata

 fun getAllProducts()=viewModelScope.launch {
     var result = homeRepository.getAllProduct()

     productApiMutableLiveData.postValue(result)
 }*/


/*
    private val mutableLiveDatalist:MutableLiveData<List<Model>> = MutableLiveData()
    val user:LiveData<List<Model>> get() = mutableLiveDatalist

    fun getUser()= viewModelScope.launch {
        mutableLiveDatalist.postValue(homeRepository.myList())
    }
*/
