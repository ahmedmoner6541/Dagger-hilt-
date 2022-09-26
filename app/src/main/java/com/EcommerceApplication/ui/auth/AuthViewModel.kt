package com.EcommerceApplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.EcommerceApplication.data.remote.response.auth.login.LoginResponse
import com.EcommerceApplication.data.remote.response.auth.regisetr.RegisterResponse
import com.EcommerceApplication.repository.AuthRepositoryImpl
import com.example.kotlinproject.ui.base.BaseViewModel
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _registerResopne: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResopne: LiveData<Resource<RegisterResponse>>
        get() = _registerResopne


    fun register(
        email: String,
        name: String,
        password: String,
        phone: String
    ) = viewModelScope.launch {

         _registerResopne.value = Resource.Loading //  **
        _registerResopne.value = repository.register(email, name, password, phone)
    }


    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

}

