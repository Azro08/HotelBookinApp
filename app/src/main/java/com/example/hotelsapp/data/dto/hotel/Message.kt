package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("mark")
    val mark: Any?,
    @SerializedName("message")
    val message: String,
    @SerializedName("theme")
    val theme: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("__typename")
    val typename: String
)