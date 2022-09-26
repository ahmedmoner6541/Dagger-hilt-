package com.EcommerceApplication.data.remote.response.favorite.getFavorites


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: Int,
    @SerialName("price")
    val price: Double,
    @SerialName("old_price")
    val oldPrice: Double,
    @SerialName("discount")
    val discount: Int,
    @SerialName("image")
    val image: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String
)