package com.example.hotelsapp.data.dto


import com.google.gson.annotations.SerializedName

data class GeoIdResponse(
    @SerializedName("data")
    val `data`: List<GeoIdData>,
)