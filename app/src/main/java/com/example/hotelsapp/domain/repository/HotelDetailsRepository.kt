package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.hotel_details.HotelDetailsResponse

interface HotelDetailsRepository {
    suspend fun getHotelDetailsById(
        hotelId: String
    ): HotelDetailsResponse
}