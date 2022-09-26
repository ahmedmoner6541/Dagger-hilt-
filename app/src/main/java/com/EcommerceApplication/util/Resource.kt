package com.kadirkuruca.newsapp.util

import okhttp3.ResponseBody

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
){

    data class Success<out T>(val value:T):Resource<T>()
    data class Failure(
         val isNetworkError:Boolean,
         val errorCode:Int?,
         val errorResponse:ResponseBody?
    ) : Resource<Nothing>()
     object Loading :Resource<Nothing>()
 }