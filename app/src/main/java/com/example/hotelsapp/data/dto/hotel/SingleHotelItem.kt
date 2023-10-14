package com.example.hotelsapp.data.dto.hotel

data class SingleHotelItem(
    val userId : String = "",
    val id : String = "",
    val name : String = "",
    val neighborhood: String? = "",
    val price : String? = "",
    val imageUrl : String = "",
    val review : Double? = 0.0
)
