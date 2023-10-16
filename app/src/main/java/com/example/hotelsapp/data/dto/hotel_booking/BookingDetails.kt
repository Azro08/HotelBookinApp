package com.example.hotelsapp.data.dto.hotel_booking

data class BookingDetails(
    val hotelName : String = "",
    val userId : String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNum: String = "",
    val checkInDate: String = "",
    val checkOutDate: String = "",
    val adultNumber: Int = 0,
    val childrenNum: Int = 0,
    val paymentType: String = "",
    val cardDetails : CardDetails? = null
    )