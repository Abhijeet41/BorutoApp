package com.abhi41.borutoapp.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("prevPage")
    val prevPage: Int? = null,
    @SerializedName("nextPage")
    val nextPage: Int? = null,
    @SerializedName("heroes")
    val heroes: List<Hero> = emptyList(),
    @SerializedName("lastUpdated")
    val lastUpdated: Long? = null
)
