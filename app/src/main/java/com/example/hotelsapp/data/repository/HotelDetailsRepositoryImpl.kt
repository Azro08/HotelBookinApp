package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.api.HotelDetailsApi
import com.example.hotelsapp.domain.repository.HotelDetailsRepository
import javax.inject.Inject

class HotelDetailsRepositoryImpl
@Inject constructor(private val api : HotelDetailsApi) : HotelDetailsRepository {
    override suspend fun getHotelDetailsById(
        hotelId: String
    ) = api.getHotelDetailsById(
        hotelId = hotelId
    )


}