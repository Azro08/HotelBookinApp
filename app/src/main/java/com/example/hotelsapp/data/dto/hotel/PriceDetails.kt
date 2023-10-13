package com.example.hotelsapp.data.dto.hotel

import com.google.gson.annotations.SerializedName

data class PriceDetails(
    @SerializedName("formatted")
    val priceTag:String
)