package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: Address,
    @SerializedName("whatsAround")
    val whatsAround: WhatsAround
)