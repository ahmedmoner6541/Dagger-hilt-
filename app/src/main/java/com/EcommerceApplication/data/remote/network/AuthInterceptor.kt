package com.EcommerceApplication.data.remote.network

import android.util.Log
import com.EcommerceApplication.base.BaseRepositoty
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.data.remote.response.token.TokenResponse
import com.kadirkuruca.newsapp.util.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenApi: TokenRefreshApi
) : Interceptor, Authenticator , BaseRepositoty() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        request.addHeader("Authorization", " $token")


        return chain.proceed(request.build())
    }



     suspend fun getUpdatedToken() : Resource<TokenResponse> {
 //           return safeApiCall {tokenApi.refreshAccessToken(tokenManager.getToken()).data.token}
         return  safeApiCall { tokenApi.refreshAccessToken(tokenManager.getToken()) }
    }


    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedToken()) {
                is Resource.Success -> {

                    tokenManager.saveToken(tokenResponse.value.data.token)
                    response.request.newBuilder()
                        .header("Authorization", "${tokenResponse.value.data.token}")
                        .header("lang", "ar")
                        .header("Content-Type", "application/json")
                        .build()
                }
                else -> null
            }
        }
    }

}





