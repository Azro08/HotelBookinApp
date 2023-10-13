package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class HotelResponse(
    @SerializedName("properties")
    val properties: List<Property>,
)