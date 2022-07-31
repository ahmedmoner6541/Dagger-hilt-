package com.example.hilttutorial.ui
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hilttutorial.repository.HomeRepository
import com.example.kotlinproject.data.model.responses.ProductResponse.ProductResponse
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _product: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val product: LiveData<Resource<ProductResponse>>
        get() = _product
    fun getAllProducts()= viewModelScope.launch {
        _product.value = Resource.Loading
        _product.value = homeRepository.getAllProduct()
    }
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
