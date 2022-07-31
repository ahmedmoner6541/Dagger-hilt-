package com.example.kotlinproject.data.model.responses.ProductResponse

data class Data(
    val ad: String,
    val banners: List<Banner>,
    val products: List<Product>
)