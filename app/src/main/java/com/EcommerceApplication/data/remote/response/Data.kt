package com.EcommerceApplication.data.remote.response

data class Data(
    val cart_items: List<CartItem>,
    val sub_total: Double,
    val total: Double
)