package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class Property(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("neighborhood")
    val neighborhood: Neighborhood?,
    @SerializedName("price")
    val price: Price,
    @SerializedName("propertyImage")
    val propertyImage: PropertyImage,
    @SerializedName("reviews")
    val reviews: Reviews,

    )