package com.EcommerceApplication.data.remote.response

import android.os.Parcelable
import com.EcommerceApplication.ui.product.ProductModel
import kotlinx.parcelize.Parcelize
@Parcelize
data class CartItem(
    val id: Int,
    val quantity: Int,
    val product: Product
): Parcelable

fun List<CartItem>.toProductAdapterModel(): List<ProductModel> {

    return map {
        ProductModel(
            description = it.product.description,
            discount = it.product.discount,
            id = it.product.id,
            image = it.product.image,
            images = it.product.images,
            inCart = it.product.in_cart,
            inFavorites = it.product.in_favorites,
            name = it.product.name,
            oldPrice = it.product.old_price,
            price = it.product.price,
            quantity = it.quantity,
            cartId = it.id
          )

    }
}