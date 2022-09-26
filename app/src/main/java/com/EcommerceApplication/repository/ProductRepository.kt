package com.EcommerceApplication.repository

import android.util.Log
import com.EcommerceApplication.base.BaseRepositoty
import com.EcommerceApplication.data.local.db.ProductDao
import com.EcommerceApplication.data.local.shared.TokenManager
import com.EcommerceApplication.data.models.Product
import com.EcommerceApplication.data.remote.network.ProductApi
import com.EcommerceApplication.data.remote.request.addAddress.AddAddressRequest
import com.EcommerceApplication.data.remote.request.orders.AddOrderRequest
import com.EcommerceApplication.data.remote.request.updateAddress.UpdateAddressRequest
import com.EcommerceApplication.data.remote.response.order.addOrder.addOrderResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val api: ProductApi,
    tokenManager: TokenManager, // for details product in details fragment

) : BaseRepositoty() {

    var token = tokenManager.getToken().toString()

    suspend fun getAllProductApi() = safeApiCall {
        api.getAllProduct(token)
    }

    suspend fun getFavorite() = safeApiCall {
        api.geFavorite(token)
    }


    suspend fun getproductDetails(id: String) = safeApiCall {
        api.getproductDetails(id, token)
    }

    suspend fun addOrDeleteProductToCartById(id: String) = safeApiCall {
        api.addOrDeleteProductToCartById(id, token)
    }

    suspend fun updateProductQuantityInCart(id: Int, quantity: Int) = safeApiCall {
        api.updateProductQuantityInCart(id, quantity, token)
    }

    suspend fun getCart() = safeApiCall {
        api.getCarts(token)
    }

    suspend fun getAddresses() = safeApiCall {
        api.getAddresses(token)
    }

    suspend fun addNewAddress(newAddress: AddAddressRequest) = safeApiCall {
        api.addNewAddress(token, newAddress)
    }

    suspend fun deleteAddress(id: Int) = safeApiCall {
        api.deleteAddress(token, id)
    }

    suspend fun updateAddress(id: Int, newAddress: UpdateAddressRequest) = safeApiCall {
        api.updateAddress(token, id, newAddress)
    }


    suspend fun getOrders() = safeApiCall {
        api.getOrders(token)
    }

    suspend fun addOrder(addressId: Int, methodpayment: Int, b: Boolean) = safeApiCall {
        val addOrderRequest = AddOrderRequest(addressId.toString(),methodpayment.toString(),b,0)


        Log.d("TAG", "addOrder:addressId ${addOrderRequest.address_id}")
        Log.d("TAG", "addOrder: ${addOrderRequest.promo_code_id}")
        Log.d("TAG", "addOrder:paymentMethod =  ${addOrderRequest.payment_method}")
        Log.d("TAG", "addOrder: ${addOrderRequest.use_points}")

        api.addOrder(token, addOrderRequest)
    }

    suspend fun orderDetails(orderId: String) = safeApiCall {
        api.orderDetails(token, orderId)
    }


    fun upsert(product: List<Product>) = productDao.upsert(product)

    fun getSavedAllProduct() = productDao.getSavedAllProduct()

    suspend fun setProductToFavoriteById(productId: String) = safeApiCall {
        api.setProductToFavoriteById(productId, token)
    }


}






