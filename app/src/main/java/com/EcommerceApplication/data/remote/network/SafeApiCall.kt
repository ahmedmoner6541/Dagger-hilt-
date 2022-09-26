package com.EcommerceApplication.data.remote.network

import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

interface SafeApiCall {
  /*  suspend fun<T: Any> safeAPICall(call: suspend () -> Response<T>) : T{
        val response = try {
            call.invoke()
        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
            val message = if( e is ConnectException) "Connection Error" else "Something went wrong. Please try again."
            throw IOException(ResponseError(message, 500).toString())
        }


// When connection is OK

        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            error?.let{
                val message = JSONObject(it).optString("message", "Something went wrong")
                val responseError = ResponseError(message, response.code())
                throw IOException(responseError.message.toString())

            }
            throw IOException(ResponseError("Something went wrong. Please try again.", 500).toString())
        }
    }
    data class ResponseError(val message:String, val errorCode:Int)
*/

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

}
