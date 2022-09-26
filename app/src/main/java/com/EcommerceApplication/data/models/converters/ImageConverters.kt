package com.EcommerceApplication.data.models.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageConverters {

    @TypeConverter
    fun fromImageListToJson(stat: List<String>): String {
        return Gson().toJson(stat)
    }

    @TypeConverter
    fun fromJsonToImagesList(jsonImages: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(jsonImages, type)
    }
}

