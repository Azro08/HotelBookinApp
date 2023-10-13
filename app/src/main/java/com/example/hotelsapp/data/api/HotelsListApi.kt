package com.example.hotelsapp.data.api

import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.helper.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelsListApi {

    @GET("hotels/search")
    suspend fun getHotelsList(
        @Query("region_id") regionId: String,
        @Query("locale") locale : String = "en_US",
        @Query("checkin_date") checkIn: String,
        @Query("sort_order") sortOrder : String = "REVIEW",
        @Query("adults_number") adultNum : Int = 1,
        @Query("domain") domain : String = "US",
        @Query("checkout_date") checkOut: String,
        @Query("rapidapi-key") apiKey : String = Constants.API_KEY
    ) : HotelResponse
}