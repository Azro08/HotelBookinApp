package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class PropertyImage(
    @SerializedName("image")
    val image: Image,
)