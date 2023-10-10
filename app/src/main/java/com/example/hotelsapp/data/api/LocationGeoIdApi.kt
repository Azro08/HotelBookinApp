package com.example.hotelsapp.data.api

import com.example.hotelsapp.data.dto.GeoIdData
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationGeoIdApi {

    @GET("searchLocation")
    suspend fun getGeoId(@Query("query") city : String) : GeoIdData

}