package com.EcommerceApplication.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.data.remote.response.order.addOrder.addOrderResponse
import com.EcommerceApplication.data.remote.response.order.details.OrderDetailsResponse
import com.EcommerceApplication.data.remote.response.order.getOrders.GetOrdersResponse
import com.EcommerceApplication.repository.ProductRepository
import com.example.kotlinproject.ui.base.BaseViewModel
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : BaseViewModel(productRepository) {
    // ADD   GET   DETAILS   ESTIMATE  CANCEL

    private val _getOrders: MutableLiveData<Resource<GetOrdersResponse>> = MutableLiveData()
    val getOrders: LiveData<Resource<GetOrdersResponse>> get() = _getOrders

    private val _addOrders: MutableLiveData<Resource<addOrderResponse>> = MutableLiveData()
    val addOrders: LiveData<Resource<addOrderResponse>> get() = _addOrders

    private val _orderDetails: MutableLiveData<Resource<OrderDetailsResponse>> = MutableLiveData()
    val orderDetails: LiveData<Resource<OrderDetailsResponse>> get() = _orderDetails
    /*

     private val _getOrders:MutableLiveData<Resource<GetOrdersResponse>> = MutableLiveData()
     val getOrders:LiveData<Resource<GetOrdersResponse>> get() = _getOrders

     private val _getOrders:MutableLiveData<Resource<GetOrdersResponse>> = MutableLiveData()
     val getOrders:LiveData<Resource<GetOrdersResponse>> get() = _getOrders

     private val _getOrders:MutableLiveData<Resource<GetOrdersResponse>> = MutableLiveData()
     val getOrders:LiveData<Resource<GetOrdersResponse>> get() = _getOrders

     */


    fun getOrders() = viewModelScope.launch {
        _getOrders.value = Resource.Loading
        _getOrders.value = productRepository.getOrders()
    }

    fun addOrder(addressId: Int, methodpayment: Int, b: Boolean) = viewModelScope.launch {
        _addOrders.value = Resource.Loading
        _addOrders.value = productRepository.addOrder(addressId,methodpayment,b)
    }
    fun orderDetalils(orderId:String) = viewModelScope.launch {
            _orderDetails.value = Resource.Loading
            _orderDetails.value = productRepository.orderDetails(orderId)
        }


/*
    fun getOrders()= viewModelScope.launch {
        _getOrders.value = Resource.Loading
        _getOrders.value = productRepository.getOrders()
    }
    fun getOrders()= viewModelScope.launch {
        _getOrders.value = Resource.Loading
        _getOrders.value = productRepository.getOrders()
    }
    fun getOrders()= viewModelScope.launch {
        _getOrders.value = Resource.Loading
        _getOrders.value = productRepository.getOrders()
    }
    fun getOrders()= viewModelScope.launch {
        _getOrders.value = Resource.Loading
        _getOrders.value = productRepository.getOrders()
    }*/
}