package com.EcommerceApplication.data.remote.response.auth.login

data class Data(
    val credit: Int,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val phone: String,
    val points: Int,
    val token: String
)