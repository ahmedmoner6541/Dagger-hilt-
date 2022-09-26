package com.EcommerceApplication.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.EcommerceApplication.data.models.converters.ImageConverters
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