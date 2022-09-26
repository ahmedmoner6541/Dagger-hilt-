package com.EcommerceApplication.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.EcommerceApplication.data.models.converters.ImageConverters
import com.EcommerceApplication.ui.product.ProductModel
import kotlinx.parcelize.Parcelize
import java.sql.Types.BLOB

@Entity
 @Parcelize
data class Product(
    val description: String,
    val discount: Int,
    val id: Int,
    @ColumnInfo(typeAffinity = BLOB)
    val image: String,
    @PrimaryKey
    @TypeConverters(ImageConverters::class)
    val images: List<String>,
    val in_cart: Boolean,
    val in_favorites: Boolean,
    val name: String,
    val old_price: Double,
    val price: Double,
)
 : Parcelable

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
            price = it.price
        )
    }
}

fun List<Product>.toProductAdapterBanner(): List<ProductModel> {
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
            price = it.price
        )
    }
}