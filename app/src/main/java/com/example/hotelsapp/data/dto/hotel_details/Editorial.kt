package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class Editorial(
    @SerializedName("content")
    val content: List<String>,
)