package com.example.hotelsapp.data.dto


import com.google.gson.annotations.SerializedName

data class CardPhoto(
    @SerializedName("sizes")
    val sizes: Sizes,
    @SerializedName("__typename")
    val typename: String
)