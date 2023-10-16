package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class HotelDetailsResponse(
    @SerializedName("propertyGallery")
    val propertyGallery: PropertyGallery,
    @SerializedName("summary")
    val summary: SummaryDetailsResponseSummary,
)