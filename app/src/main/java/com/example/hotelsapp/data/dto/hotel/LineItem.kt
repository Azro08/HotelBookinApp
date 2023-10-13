package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class LineItem(
    @SerializedName("price")
    val price: PriceDetails?,
)