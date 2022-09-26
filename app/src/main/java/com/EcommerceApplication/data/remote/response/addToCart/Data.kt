package com.EcommerceApplication.data.remote.response.addToCart


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("id")
    val id: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("product")
    val product: Product
)