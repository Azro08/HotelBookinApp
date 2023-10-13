package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("score")
    val score: Double,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("__typename")
    val typename: String
)