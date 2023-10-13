package com.example.hotelsapp.data.dto.hotel


import com.google.gson.annotations.SerializedName

data class HotelData(
    @SerializedName("accentedLabel")
    val accentedLabel: Boolean,
    @SerializedName("bubbleRating")
    val bubbleRating: BubbleRating,
    @SerializedName("cardPhotos")
    val cardPhotos: List<CardPhoto>,
    @SerializedName("id")
    val id: String,
    @SerializedName("isSponsored")
    val isSponsored: Boolean,
    @SerializedName("priceDetails")
    val priceDetails: Any?,
    @SerializedName("priceForDisplay")
    val priceForDisplay: Any?,
    @SerializedName("priceSummary")
    val priceSummary: Any?,
    @SerializedName("primaryInfo")
    val primaryInfo: Any?,
    @SerializedName("provider")
    val provider: Any?,
    @SerializedName("secondaryInfo")
    val secondaryInfo: Any?,
    @SerializedName("strikethroughPrice")
    val strikethroughPrice: Any?,
    @SerializedName("title")
    val title: String
)