package com.example.hotelsapp.presentation.hotels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.domain.repository.HotelsListRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HotelsListViewModel @Inject constructor(
    private val repository: HotelsListRepository
) : ViewModel() {

    val responseHotels: MutableStateFlow<ScreenState<HotelResponse?>> =
        MutableStateFlow(ScreenState.Loading())

    fun getHotelsList() = viewModelScope.launch {
        responseHotels.value = ScreenState.Loading()
        repository.getHotelsList().let { response ->
            try {
                try {
                    responseHotels.value = ScreenState.Success(response)
                } catch (e: HttpException) {
                    responseHotels.value = ScreenState.Error(e.message.toString())
                    Log.d("httpError", e.cause?.message.toString())
                }

            } catch (e: UnknownHostException) {
                responseHotels.value = ScreenState.Error(e.message.toString())
            }
        }
    }

}