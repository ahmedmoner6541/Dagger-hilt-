package com.EcommerceApplication.ui.product

import android.os.Parcelable
import com.EcommerceApplication.data.remote.response.productDetailes.Data
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import java.util.Date
import javax.annotation.Nullable

@Parcelize
data class ProductModel(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val images: List<String>,
    val inCart: Boolean,
    val inFavorites: Boolean,
    val name: String,
    val oldPrice: Double,
    val price: Double,
    val quantity: Int = 0,
    val cartId: Int = 0,
    ) : Parcelable

