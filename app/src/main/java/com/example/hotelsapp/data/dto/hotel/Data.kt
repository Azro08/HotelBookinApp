package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val hotelData: List<HotelData>,
    @SerializedName("sortDisclaimer")
    val sortDisclaimer: String,
    @SerializedName("textGroupStandard")
    val textGroupStandard: TextGroupStandard
)