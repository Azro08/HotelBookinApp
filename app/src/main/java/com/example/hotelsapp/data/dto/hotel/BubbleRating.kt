package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class BubbleRating(
    @SerializedName("count")
    val count: String?,
    @SerializedName("rating")
    val rating: Double?
)