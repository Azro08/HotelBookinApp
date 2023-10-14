package com.example.hotelsapp.presentation.hotels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.domain.repository.RegionIdRepository
import com.example.hotelsapp.domain.repository.HotelsListRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HotelsListViewModel @Inject constructor(
    private val repository: HotelsListRepository,
    private val regionIdRepository: RegionIdRepository,
    private val favRepository : FavoriteHotelsRepository
) : ViewModel() {

    val responseHotels: MutableStateFlow<ScreenState<HotelResponse?>> =
        MutableStateFlow(ScreenState.Loading())

    fun getHotelsList(
        cityName: String,
        checkIn: String,
        checkOut: String
    ) = viewModelScope.launch {
        val regionId = regionIdRepository.getGeoId(cityName).data[0].gaiaId

        if  (regionId != null){
            responseHotels.value = ScreenState.Loading()
            repository.getHotelsList(regionId = regionId, checkIn = checkIn, checkOut = checkOut)
                .let { response ->
                    try {
                        try {
                            responseHotels.value = ScreenState.Success(response)
                        } catch (e: Exception) {
                            responseHotels.value = ScreenState.Error(e.message.toString())
                            Log.d("httpError", e.cause?.message.toString())
                        }

                    } catch (e: UnknownHostException) {
                        responseHotels.value = ScreenState.Error(e.message.toString())
                    }
                }
        } else responseHotels.value = ScreenState.Error("Location not found")

    }


    fun addHotelToFavorite(hotel : SingleHotelItem) = viewModelScope.launch {
        favRepository.saveHotelToFavorites(hotel)
    }

}