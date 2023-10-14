package com.example.hotelsapp.domain.repository

import com.example.hotelsapp.data.dto.hotel.SingleHotelItem

interface FavoriteHotelsRepository {

    suspend fun saveHotelToFavorites(hotel : SingleHotelItem)

    suspend fun getHotelFromFavorites() : List<SingleHotelItem>

}