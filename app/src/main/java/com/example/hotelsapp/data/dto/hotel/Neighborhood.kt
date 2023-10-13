package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Neighborhood(
    @SerializedName("name")
    val name: String?,
    @SerializedName("__typename")
    val typename: String
)