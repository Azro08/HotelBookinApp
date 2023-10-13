package com.example.hotelsapp.data.repository

import com.example.hotelsapp.data.api.LocationGeoIdApi
import com.example.hotelsapp.data.dto.geo_id.GeoIdData
import com.example.hotelsapp.domain.repository.GeoIdRepository
import javax.inject.Inject

class GeoIdRepositoryImpl @Inject constructor(
    private val api : LocationGeoIdApi
)  : GeoIdRepository{
    override suspend fun getGeoId(city: String): GeoIdData =
        api.getGeoId(city)

}