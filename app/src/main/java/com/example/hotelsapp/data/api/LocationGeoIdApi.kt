package com.example.hotelsapp.data.api

import com.example.hotelsapp.data.dto.geo_id.LocationIdResponse
import com.example.hotelsapp.helper.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationGeoIdApi {
    @GET("regions")
    suspend fun getGeoId(
        @Query("query") city: String,
        @Query("domain") domain: String = "AE",
        @Query("locale") locale: String = "en_GB",
        @Query("rapidapi-key") apiKey: String = Constants.API_KEY
    ): LocationIdResponse

}