package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.GeoIdData

interface GeoIdRepository {
    suspend fun getGeoId(city : String) : GeoIdData
}