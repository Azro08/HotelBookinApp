package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class NearbyPOIsItems(
    @SerializedName("moreInfo")
    val moreInfo: String,
    @SerializedName("text")
    val text: String,
)