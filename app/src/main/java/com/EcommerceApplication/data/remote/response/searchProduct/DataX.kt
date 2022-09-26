package com.EcommerceApplication.data.remote.response.searchProduct


import android.os.Parcelable
import com.EcommerceApplication.ui.product.ProductModel
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class DataX(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val images: List<String>,
    @SerialName("in_cart")
    val inCart: Boolean,
    @SerialName("in_favorites")
    val inFavorites: Boolean,
    val name: String,
    @SerialName("old_price")
    val oldPrice: Double,
    val price: Double
) : Parcelable

fun List<DataX>.toProductAdapterModel(): List<ProductModel> {
    return map {
        ProductModel(
            description = it.description,
            discount = it.discount,
            id = it.id,
            image = it.image,
            images = it.images,
            inCart = it.inCart,
            inFavorites = it.inFavorites,
            name = it.name,
            oldPrice = it.oldPrice,
            price = it.price,
         )

    }
}