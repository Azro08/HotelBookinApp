package com.example.hotelsapp.data.dto.geo_id


import com.google.gson.annotations.SerializedName

data class LocationIdResponse(
    @SerializedName("data")
    val `data`: List<LocationData>,
    @SerializedName("query")
    val query: String
)