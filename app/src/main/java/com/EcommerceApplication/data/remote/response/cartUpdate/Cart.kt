package com.EcommerceApplication.data.remote.response.cartUpdate

data class Cart(
    val id: Int,
    val quantity: Int,
    val product: Product
)