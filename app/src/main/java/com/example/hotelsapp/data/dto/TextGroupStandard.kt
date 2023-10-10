package com.example.hotelsapp.data.dto


import com.google.gson.annotations.SerializedName

data class TextGroupStandard(
    @SerializedName("message")
    val message: String,
    @SerializedName("title")
    val title: String
)