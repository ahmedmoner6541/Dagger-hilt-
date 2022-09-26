package com.EcommerceApplication.data.remote.request.orders


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

 data class AddOrderRequest(
     val address_id: String,
     val payment_method: String,
     val use_points: Boolean,
     val promo_code_id: Int
)