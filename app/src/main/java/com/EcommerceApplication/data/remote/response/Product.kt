package com.EcommerceApplication.data.remote.response

import android.os.Parcelable
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