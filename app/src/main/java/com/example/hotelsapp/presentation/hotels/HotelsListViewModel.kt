package com.example.hotelsapp.presentation.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.domain.repository.HotelsListRepository
import com.example.hotelsapp.domain.repository.RegionIdRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HotelsListViewModel @Inject constructor(
    private val hotelsListRepository: HotelsListRepository,
    private val regionIdRepository: RegionIdRepository,
    private val favoriteHotelsRepository: FavoriteHotelsRepository
) : ViewModel() {

    private val _hotelsList = MutableStateFlow<ScreenState<HotelResponse?>>(ScreenState.Loading())
    val hotelsList: MutableStateFlow<ScreenState<HotelResponse?>> = _hotelsList

    fun getHotelsList(
        cityName: String,
        checkIn: String,
        checkOut: String
    ) = viewModelScope.launch {
        try {
            val regionId = regionIdRepository.getGeoId(cityName).data[0].gaiaId
            _hotelsList.value = ScreenState.Loading()

            if (regionId != null) {
                try {
                    _hotelsList.value = ScreenState.Success(
                        hotelsListRepository.getHotelsList(
                            regionId,
                            checkIn,
                            checkOut
                        )
                    )
                } catch (e: HttpException) {
                    _hotelsList.value = ScreenState.Error(e.message.toString())
                }
            } else {
                _hotelsList.value = ScreenState.Error("Location not found")
            }
        } catch (e: UnknownHostException) {
            _hotelsList.value = ScreenState.Error("Check your connection")
        }
    }

    fun addToFavorites(hotel: SingleHotelItem) = viewModelScope.launch {
        favoriteHotelsRepository.saveHotelToFavorites(hotel)
    }
}
