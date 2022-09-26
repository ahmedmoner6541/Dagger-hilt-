package com.EcommerceApplication.data.remote.network

import com.EcommerceApplication.data.remote.request.addAddress.AddAddressRequest
import com.EcommerceApplication.data.remote.request.orders.AddOrderRequest
import com.EcommerceApplication.data.remote.request.updateAddress.UpdateAddressRequest
import com.EcommerceApplication.data.remote.response.CartRespo
import com.EcommerceApplication.data.remote.response.addAddress.AddAddressResponse
import com.EcommerceApplication.data.remote.response.addToCart.AddToCartResponse
import com.EcommerceApplication.data.remote.response.cartUpdate.CartUpdateQuantityResponse
import com.EcommerceApplication.data.remote.response.deleteAddress.DeleteAddressResponse
import com.EcommerceApplication.data.remote.response.favorite.addInFavorite.FavoriteAddReposnse
import com.EcommerceApplication.data.remote.response.favorite.getFavorites.FavoriteProductResponse
import com.EcommerceApplication.data.remote.response.getAdress.AddressResponse
import com.EcommerceApplication.data.remote.response.order.addOrder.addOrderResponse
import com.EcommerceApplication.data.remote.response.order.details.OrderDetailsResponse
import com.EcommerceApplication.data.remote.response.order.getOrders.GetOrdersResponse
import com.EcommerceApplication.data.remote.response.product.ProductResponse
import com.EcommerceApplication.data.remote.response.productDetailes.ProductDetailesResponse
import com.EcommerceApplication.data.remote.response.updateAddress.UpdateAddressResponse
import retrofit2.http.*

interface ProductApi {

    @GET("home")
    suspend fun getAllProduct(
        @Header("Authorization") token: String,
    ): ProductResponse


    @GET("favorites")
    suspend fun geFavorite(
        @Header("Authorization") token: String,
    ): FavoriteProductResponse

    @FormUrlEncoded
    @POST("favorites")
    suspend fun setProductToFavoriteById(
        @Field("product_id") productId: String,
        @Header("Authorization") token: String,
    ): FavoriteAddReposnse

    @GET("products/{id}")
    suspend fun getproductDetails(
        @Path("id") ids: String,
        @Header("Authorization") token: String,
    ): ProductDetailesResponse


    @POST("carts")
    suspend fun addOrDeleteProductToCartById(
        @Query("product_id") product_id: String,
        @Header("Authorization") token: String,
    ): AddToCartResponse


    @PUT("carts/{id}")
    suspend fun updateProductQuantityInCart(
        @Path("id") id: Int,
        @Query("quantity") totalquantity: Int,
        @Header("Authorization") token: String,
    ): CartUpdateQuantityResponse//: List<BaseModel>

    @GET("carts")
    suspend fun getCarts(
        @Header("Authorization") token: String,
    ): CartRespo

    @GET("addresses")
    suspend fun getAddresses(
        @Header("Authorization") token: String,
    ): AddressResponse


    @POST("addresses")
    suspend fun addNewAddress(
        @Header("Authorization") token: String,
        @Body registerRequest: AddAddressRequest
    ): AddAddressResponse


    @DELETE("addresses/{id}")
    suspend fun deleteAddress(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): DeleteAddressResponse

    @PUT("addresses/{id}")
    suspend fun updateAddress(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body updateAddressRequest: UpdateAddressRequest
    ): UpdateAddressResponse

    @GET("orders")
    suspend fun getOrders(
        @Header("Authorization") token: String,
        ): GetOrdersResponse

    @POST("orders")
    suspend fun addOrder(
        @Header("Authorization") token: String,
        @Body addOrderRequest: AddOrderRequest,
         ): addOrderResponse

    @GET("orders/{id}")
    suspend fun orderDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): OrderDetailsResponse


}
