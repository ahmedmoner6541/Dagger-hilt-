package com.EcommerceApplication.data.remote.response.favorite.getFavorites


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataX(
    @SerialName("id")
    val id: Int,
    @SerialName("product")
    val product: Product
)