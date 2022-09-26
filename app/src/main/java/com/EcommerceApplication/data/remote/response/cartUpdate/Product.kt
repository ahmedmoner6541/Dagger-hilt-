package com.EcommerceApplication.data.remote.response.cartUpdate

data class Product(
    val id: Int,
    val price: Int,
    val old_price: Int,
    val discount: Int,
    val image: String
)