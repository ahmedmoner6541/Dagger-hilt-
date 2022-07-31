package com.example.hilttutorial.repository

 import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepositoty {

    suspend fun <T>safeApiCall(
        apiCall:suspend ()->T
    ) :Resource<T>{
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiCall.invoke())
            }catch (throwable:Throwable){
                when(throwable){
                    is HttpException ->{
                        Resource.Failure(false, throwable.code(),throwable.response()?.errorBody())
                    }
                    else->{
                        Resource.Failure(true,null,null)
                    }
                }
            } as Resource<T>


        }
    }
 /*   suspend fun logout(api:UserApi) = safeApiCall {
        api.logout()
    }*/
}