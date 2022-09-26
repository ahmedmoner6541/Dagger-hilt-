package com.EcommerceApplication.data.remote.response.productDetailes

data class Data(
    val id: Int,
    val price: Int,
    val old_price: Int,
    val discount: Int,
    val image: String,
    val name: String,
    val description: String,
    val in_favorites: Boolean,
    val in_cart: Boolean,
    val images: List<String>
)


/*
  fun mapFromDomainModel(product: Data): ProductModel {
    return ProductModel(
        id = product.id,
        price = product.price.toDouble(),
        oldPrice =product.old_price.toDouble(),
        discount = product.discount,
        image = product.image,
        name = product.name,
        description = product.description,
        inFavorites = product.in_favorites,
        inCart = product.in_cart,
        images = product.images
    )
}
*/
