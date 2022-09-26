package com.EcommerceApplication.data.remote.request.updateAddress


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class UpdateAddressRequest(
    @SerialName("name")
    val name: String,
    @SerialName("city")
    val city: String,
    @SerialName("region")
    val region: String,
    @SerialName("details")
    val details: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("notes")
    val notes: String
)