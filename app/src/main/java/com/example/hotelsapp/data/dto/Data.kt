package com.example.hotelsapp.data.dto


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("sortDisclaimer")
    val sortDisclaimer: String,
    @SerializedName("textGroupStandard")
    val textGroupStandard: TextGroupStandard
)