package com.example.hotelsapp.data.dto.hotel_details


import com.google.gson.annotations.SerializedName

data class PropertyGallery(
    @SerializedName("images")
    val images: List<Image>
)