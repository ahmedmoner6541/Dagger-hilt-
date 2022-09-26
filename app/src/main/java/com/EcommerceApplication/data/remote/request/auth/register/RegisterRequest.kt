package com.EcommerceApplication.data.remote.request.auth.register

data class  RegisterRequest(
    val email: String,
    val name: String,
    val password: String,
    val phone: String
)