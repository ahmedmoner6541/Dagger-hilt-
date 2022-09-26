package com.EcommerceApplication.data.remote.response.order.details


import com.EcommerceApplication.ui.product.ProductModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: Int,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String
)
fun List<Product>.toProductAdapterModel(): List<ProductModel> {
    return map {
        ProductModel(
            description = 0.toString(),
            discount = 0,
            id = it.id,
            image = it.image,
            images = emptyList(),
            inCart = false,
            inFavorites = false,
            name = it.name,
            oldPrice = 0.0,
            price = it.price.toDouble(),
            quantity = it.quantity
        )

    }
}