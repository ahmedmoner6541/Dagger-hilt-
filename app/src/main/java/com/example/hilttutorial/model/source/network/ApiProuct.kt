package com.example.hilttutorial.model.source.network

import com.example.kotlinproject.data.model.responses.ProductResponse.ProductResponse
import retrofit2.http.GET

interface ApiProuct {
    @GET("home")
    suspend fun getAllProduct():ProductResponse//: List<BaseModel>
}