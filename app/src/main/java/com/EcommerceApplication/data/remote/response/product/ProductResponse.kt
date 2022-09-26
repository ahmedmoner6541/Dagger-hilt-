package com.EcommerceApplication.data.remote.response.product

import com.EcommerceApplication.data.models.Data

data class ProductResponse(
    val `data`: Data,
    val message: Any,
    val status: Boolean
)