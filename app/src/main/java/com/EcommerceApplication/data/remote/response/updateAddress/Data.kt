package com.EcommerceApplication.data.remote.response.updateAddress


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Data")
data class Data(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("city")
    val city: String,
    @SerialName("region")
    val region: String,
    @SerialName("details")
    val details: String,
    @SerialName("notes")
    val notes: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)