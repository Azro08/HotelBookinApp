package com.example.hotelsapp.data.dto.geo_id


import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("gaiaId")
    val gaiaId: String?,
)