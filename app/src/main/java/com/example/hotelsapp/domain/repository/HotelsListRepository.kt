package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.hotel.HotelResponse

interface HotelsListRepository {
    suspend fun getHotelsList(
        regionId: String,
        checkIn: String,
        checkOut: String,
    ): HotelResponse
}