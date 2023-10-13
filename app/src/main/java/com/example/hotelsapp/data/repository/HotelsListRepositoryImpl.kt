package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.api.HotelsListApi
import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.domain.repository.HotelsListRepository
import javax.inject.Inject

class HotelsListRepositoryImpl @Inject constructor(
    private val api : HotelsListApi
) : HotelsListRepository {
    override suspend fun getHotelsList(
        regionId: String,
        checkIn: String,
        checkOut: String
    ): HotelResponse =
        api.getHotelsList(
            regionId = regionId,
            checkIn = checkIn,
            checkOut = checkOut
        )

}