package com.EcommerceApplication.data.remote.response.auth.regisetr

data class Data(
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val token: String
)