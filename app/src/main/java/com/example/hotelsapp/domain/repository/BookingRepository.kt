package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails

interface BookingRepository {

    suspend fun bookHotel(bookingDetails: BookingDetails)

    suspend fun getBookingHistory() : List<BookingDetails>

    suspend fun removeBookedHotel(hotelName: String)

}