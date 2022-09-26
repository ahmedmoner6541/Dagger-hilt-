package com.EcommerceApplication.data.remote.response.order.getOrders


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    @SerialName("id")
    val id: Int,
    @SerialName("total")
    val total: Double,
    @SerialName("date")
    val date: String,
    @SerialName("status")
    val status: String
)