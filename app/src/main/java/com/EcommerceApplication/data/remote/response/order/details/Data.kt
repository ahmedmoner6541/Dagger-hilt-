package com.EcommerceApplication.data.remote.response.order.details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("id")
    val id: Int,
    @SerialName("cost")
    val cost: Int,
    @SerialName("discount")
    val discount: Int,
    @SerialName("points")
    val points: Int,
    @SerialName("vat")
    val vat: Double,
    @SerialName("total")
    val total: Int,
    @SerialName("points_commission")
    val pointsCommission: Int,
    @SerialName("promo_code")
    val promoCode: String,
    @SerialName("payment_method")
    val paymentMethod: String,
    @SerialName("date")
    val date: String,
    @SerialName("status")
    val status: String,
    @SerialName("address")
    val address: Address,
    @SerialName("products")
    val products: List<Product>
)