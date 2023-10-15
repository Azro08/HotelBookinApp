package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("image")
    val image: ImageDetails,
)