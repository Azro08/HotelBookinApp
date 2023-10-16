package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class SummaryDetailsResponseSummary(
    @SerializedName("id")
    val id: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("nearbyPOIs")
    val nearbyPOIs: NearbyPOIs,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("telesalesPhoneNumber")
    val telesalesPhoneNumber: String,
)