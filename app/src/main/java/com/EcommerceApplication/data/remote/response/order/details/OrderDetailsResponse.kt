package com.EcommerceApplication.data.remote.response.order.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: Any?,
    @SerialName("data")
    val `data`: Data
)