package com.EcommerceApplication.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CartItem(
    val id: Int,
    val quantity: Int,
    val product: Product
): Parcelable
