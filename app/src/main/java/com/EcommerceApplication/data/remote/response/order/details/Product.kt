package com.EcommerceApplication.data.remote.response.order.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String
)