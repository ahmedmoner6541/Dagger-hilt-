package com.EcommerceApplication.data.remote.response.searchProduct


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class SearchProductResponse(
    val `data`: Data,
    val message: Any,
    val status: Boolean
)