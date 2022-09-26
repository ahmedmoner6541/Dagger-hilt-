package com.EcommerceApplication.ui.Adrerss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.EcommerceApplication.data.remote.response.getAdress.AddressResponse
import com.EcommerceApplication.repository.ProductRepository
import com.kadirkuruca.newsapp.util.Resource
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.data.remote.request.addAddress.AddAddressRequest
import com.EcommerceApplication.data.remote.request.updateAddress.UpdateAddressRequest
import com.EcommerceApplication.data.remote.response.addAddress.AddAddressResponse
import com.EcommerceApplication.data.remote.response.deleteAddress.DeleteAddressResponse
import com.EcommerceApplication.data.remote.response.updateAddress.UpdateAddressResponse
import com.example.kotlinproject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AddressViewModel @Inject constructor(
    private val productRepository: ProductRepository
    ):BaseViewModel(productRepository){

    // get home date
    private val _getAddresses: MutableLiveData<Resource<AddressResponse>> = MutableLiveData()
    val getAddresses: LiveData<Resource<AddressResponse>>
        get() = _getAddresses

    fun getAllAddresses()= viewModelScope.launch {
        _getAddresses.value = Resource.Loading
        _getAddresses.value = productRepository.getAddresses()
    }

    //add new  address
    private val _addNewAddresses: MutableLiveData<Resource<AddAddressResponse>> = MutableLiveData()
    val addNewAddresses: LiveData<Resource<AddAddressResponse>>
        get() = _addNewAddresses

    fun addAddress(newAddress: AddAddressRequest) =viewModelScope.launch {
        _addNewAddresses.value = Resource.Loading
        _addNewAddresses.value = productRepository.addNewAddress(newAddress)
    }

    //_deleteAddress
    private val _deleteAddress:MutableLiveData<Resource<DeleteAddressResponse>> = MutableLiveData()
    val deleteAddress:LiveData<Resource<DeleteAddressResponse>>
    get() = _deleteAddress

    fun deleteAddress(id:Int) = viewModelScope.launch {
        _deleteAddress.value = Resource.Loading
        _deleteAddress.value = productRepository.deleteAddress(id)
    }

    private val _updateAddress:MutableLiveData<Resource<UpdateAddressResponse>> = MutableLiveData()
    val updateAddress:LiveData<Resource<UpdateAddressResponse>>
        get() = _updateAddress

    fun updateAddress(id: Int, newAddress: UpdateAddressRequest) = viewModelScope.launch {
        _updateAddress.value = Resource.Loading
        _updateAddress.value = productRepository.updateAddress(id,newAddress)
    }



}
