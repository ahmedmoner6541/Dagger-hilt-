package com.EcommerceApplication.data.remote.response.order.addOrder


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class addOrderResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val `data`: Data
)