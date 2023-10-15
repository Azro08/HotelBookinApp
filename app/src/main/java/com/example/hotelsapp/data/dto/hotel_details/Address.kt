package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("addressLine")
    val addressLine: String
)