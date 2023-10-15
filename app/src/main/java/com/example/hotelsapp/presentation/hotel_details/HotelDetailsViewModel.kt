package com.example.hotelsapp.presentation.hotel_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel_details.HotelDetailsResponse
import com.example.hotelsapp.domain.repository.HotelDetailsRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor(
    private val repository: HotelDetailsRepository
) : ViewModel() {

    val responseHotelDetails: MutableStateFlow<ScreenState<HotelDetailsResponse?>> =
        MutableStateFlow(ScreenState.Loading())

    fun getHotelDetailsById(hotelId:String) = viewModelScope.launch {

        repository.getHotelDetailsById(hotelId).let {response ->
            responseHotelDetails.value = ScreenState.Loading()
            try {

                try {
                    responseHotelDetails.value = ScreenState.Success(response)
                } catch (e : Exception){
                    responseHotelDetails.value = ScreenState.Error(e.message.toString())
                }

            } catch (e : UnknownHostException){
                responseHotelDetails.value = ScreenState.Error(e.message.toString())
            }

        }

    }

}