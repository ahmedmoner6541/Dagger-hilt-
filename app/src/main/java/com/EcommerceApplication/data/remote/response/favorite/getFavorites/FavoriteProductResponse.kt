package com.EcommerceApplication.data.remote.response.favorite.getFavorites


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProductResponse(
    @SerialName("status")
    val status: Boolean,
    @SerialName("message")
    val message: Any?,
    @SerialName("data")
    val `data`: Data
)