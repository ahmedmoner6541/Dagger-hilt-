package com.EcommerceApplication.data.remote.response.getAdress


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: Any?,
    @SerialName("data")
    val `data`: Data
)