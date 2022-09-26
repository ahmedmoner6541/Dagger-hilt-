package com.EcommerceApplication.data.remote.response.order.addOrder


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("cost")
    val cost: Int,
    @SerialName("vat")
    val vat: Double,
    @SerialName("discount")
    val discount: Int,
    @SerialName("points")
    val points: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("id")
    val id: Int
)