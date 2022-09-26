package com.EcommerceApplication.data.remote.response.searchProduct


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

 data class Data(
    @SerialName("current_page")
    val currentPage: Int?,
    var `data`: List<DataX>,
    @SerialName("first_page_url")
    val firstPageUrl: String?,
    val from: Int?,
    @SerialName("last_page")
    val lastPage: Int?,
    @SerialName("last_page_url")
    val lastPageUrl: String?,
    @SerialName("next_page_url")
    val nextPageUrl: Any?,
    val path: String?,
    @SerialName("per_page")
    val perPage: Int?,
    @SerialName("prev_page_url")
    val prevPageUrl: Any?,
    val to: Int?,
    val total: Int?
)