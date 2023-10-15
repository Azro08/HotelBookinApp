package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class WhatsAround(
    @SerializedName("editorial")
    val editorial: Editorial,
    @SerializedName("__typename")
    val typename: String
)