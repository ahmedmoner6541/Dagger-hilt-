package com.EcommerceApplication.data.remote.response.addAddress


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddAddressResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val `data`: Data
)