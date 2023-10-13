package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("displayMessages")
    val displayMessages: List<DisplayMessage>
)