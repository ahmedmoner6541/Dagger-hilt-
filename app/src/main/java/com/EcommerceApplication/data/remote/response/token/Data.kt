package com.EcommerceApplication.data.remote.response.token

data class Data(
    val token: String,
    val user_id: Int,
    val updated_at: String,
    val created_at: String,
    val id: Int
)