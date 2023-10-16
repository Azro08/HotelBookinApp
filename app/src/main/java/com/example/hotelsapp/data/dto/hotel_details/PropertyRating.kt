package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class PropertyRating(
    @SerializedName("accessibility")
    val accessibility: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("__typename")
    val typename: String
)