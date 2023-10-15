package com.example.hotelsapp.data.api

import com.example.hotelsapp.data.dto.hotel_details.HotelDetailsResponse
import com.example.hotelsapp.helper.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelDetailsApi {
    @GET("hotels/details")
    suspend fun getHotelDetailsById (
        @Query("domain") domain : String = "AE",
        @Query("hotel_id") hotelId : String,
        @Query("locale") locale : String = "en_GB",
        @Query("rapidapi-key") apiKey : String = Constants.API_KEY
    ) : HotelDetailsResponse

}