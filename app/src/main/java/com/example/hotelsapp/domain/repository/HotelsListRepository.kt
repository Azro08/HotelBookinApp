package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.hotel.HotelResponse

interface HotelsListRepository {
    suspend fun getHotelsList(
        regionId: String = "2427",
        checkIn: String = "2023-10-13",
        checkOut: String = "2023-10-15",
    ): HotelResponse
}