package com.EcommerceApplication.data.remote.response

import android.os.Parcelable
 import com.EcommerceApplication.ui.product.ProductModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val price: Double,
    val old_price: Double,
    val discount: Int,
    val image: String,
    val name: String,
    val description: String,
    val images: List<String>,
    val in_favorites: Boolean,
    val in_cart: Boolean
): Parcelable
fun List<Product>.toProductAdapterModel(): List<ProductModel> {

    return map {
        ProductModel(
            description = it.description,
            discount = it.discount,
            id = it.id,
            image = it.image,
            images = it.images,
            inCart = it.in_cart,
            inFavorites = it.in_favorites,
            name = it.name,
            oldPrice = it.old_price,
            price = it.price,
            quantity =  0,
            cartId = it.id
        )

    }
}