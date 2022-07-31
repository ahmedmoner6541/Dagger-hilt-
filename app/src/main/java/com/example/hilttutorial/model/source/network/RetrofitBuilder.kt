package com.example.hilttutorial.model.source.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        const val BASE_URL: String = "https://student.valuxapps.com/api/"

    }

  /*  fun <Api> buileApi(
        api:Class<Api>
    ):Api{
       return Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(api)
    }*/
}