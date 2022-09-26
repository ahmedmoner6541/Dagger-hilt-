package com.EcommerceApplication.data.remote.response.favorite.addInFavorite


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("old_price")
    val oldPrice: Int,
    @SerialName("discount")
    val discount: Int,
    @SerialName("image")
    val image: String
)