package com.EcommerceApplication.repository

import com.EcommerceApplication.base.BaseRepositoty
import com.EcommerceApplication.data.remote.network.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
) : BaseRepositoty() {

    suspend fun register(
        email: String,
        name: String,
        password: String,
        phone: String
    ) = safeApiCall {
        api.register(name, phone, email, password)
    }

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {

        api.login(email, password)
             
    }

}