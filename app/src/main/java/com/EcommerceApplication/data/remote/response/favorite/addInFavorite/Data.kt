package com.EcommerceApplication.data.remote.response.favorite.addInFavorite


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("id")
    val id: Int,
    @SerialName("product")
    val product: Product
)