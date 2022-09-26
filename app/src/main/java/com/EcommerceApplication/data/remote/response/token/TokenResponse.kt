package com.EcommerceApplication.data.remote.response.token

data class TokenResponse(
    val status: Boolean,
    val message: String,
    val `data`: Data
)