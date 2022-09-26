package com.EcommerceApplication.data.remote.network

import com.EcommerceApplication.data.remote.response.auth.login.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST


interface UserApi{
    @GET("profile")//date
    suspend fun getUser(): LoginResponse

    @POST("logout")
    suspend fun logout(): ResponseBody
}