package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class DisplayMessage(
    @SerializedName("lineItems")
    val lineItems: List<LineItem>,
)