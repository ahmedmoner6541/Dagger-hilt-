package com.EcommerceApplication.data.remote.network

import com.EcommerceApplication.data.remote.response.auth.login.LoginResponse
import com.EcommerceApplication.data.remote.response.auth.regisetr.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

     @FormUrlEncoded
     @POST("register")
     suspend fun register(
          @Field("name") name: String,
          @Field("phone") phone: String,
          @Field("email") email: String,
          @Field("password") password: String
     ): RegisterResponse

     @FormUrlEncoded
     @POST("login")
     suspend fun login(
          @Field("email") email: String,
          @Field("password") password: String
     ): LoginResponse

}










