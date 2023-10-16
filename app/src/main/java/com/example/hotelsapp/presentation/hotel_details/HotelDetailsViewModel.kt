package com.example.hotelsapp.presentation.hotel_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel_details.HotelDetailsResponse
import com.example.hotelsapp.domain.repository.HotelDetailsRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor(
    private val repository: HotelDetailsRepository
) : ViewModel() {

    private val _hotelsDetails =
        MutableStateFlow<ScreenState<HotelDetailsResponse?>>(ScreenState.Loading())
    val hotelsDetails: MutableStateFlow<ScreenState<HotelDetailsResponse?>> = _hotelsDetails

    fun getHotelDetailsById(hotelId: String) = viewModelScope.launch {
        _hotelsDetails.value = ScreenState.Loading()
        try {
            try {
                repository.getHotelDetailsById(hotelId).let { response ->
                    _hotelsDetails.value = ScreenState.Success(response)
                }
            } catch (e: HttpException) {
                _hotelsDetails.value = ScreenState.Error(e.message.toString())
            }

        } catch (e: UnknownHostException) {
            _hotelsDetails.value = ScreenState.Error(e.message.toString())
        }

    }


}