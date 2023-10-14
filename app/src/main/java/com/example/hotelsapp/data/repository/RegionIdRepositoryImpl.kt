package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.api.LocationGeoIdApi
import com.example.hotelsapp.data.dto.geo_id.LocationIdResponse
import com.example.hotelsapp.domain.repository.RegionIdRepository
import javax.inject.Inject

class RegionIdRepositoryImpl @Inject constructor(
    private val api : LocationGeoIdApi
)  : RegionIdRepository{
    override suspend fun getGeoId(city: String): LocationIdResponse =
        api.getGeoId(city)

}