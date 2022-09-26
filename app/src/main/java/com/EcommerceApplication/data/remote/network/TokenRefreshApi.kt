package com.EcommerceApplication.data.remote.network

 import com.EcommerceApplication.data.remote.response.token.TokenResponse
 import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshApi   {
    @FormUrlEncoded
    @POST("fcm-token")
    suspend fun refreshAccessToken(
        @Field("token") refreshToken: String?
    ): TokenResponse
}