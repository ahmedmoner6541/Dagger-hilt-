package com.EcommerceApplication.data.remote.response.productDetailes

data class Data(
    val id: Int,
    val price: Int,
    val old_price: Int,
    val discount: Int,
    val image: String,
    val name: String,
    val description: String,
    val in_favorites: Boolean,
    val in_cart: Boolean,
    val images: List<String>
)