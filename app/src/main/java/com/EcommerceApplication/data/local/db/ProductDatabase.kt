package com.EcommerceApplication.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.EcommerceApplication.data.models.converters.ImageConverters
import com.EcommerceApplication.data.models.Product


@Database(entities = [Product::class], version = 1)
@TypeConverters(ImageConverters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao


}