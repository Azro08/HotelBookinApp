package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class HotelsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long
)