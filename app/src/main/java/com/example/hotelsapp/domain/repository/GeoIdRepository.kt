package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.geo_id.LocationIdResponse

interface GeoIdRepository {
    suspend fun getGeoId(city : String) : LocationIdResponse
}