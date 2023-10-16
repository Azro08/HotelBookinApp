package com.example.hotelsapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FavoriteHotelsViewModel
@Inject constructor(private val repository: FavoriteHotelsRepository) : ViewModel() {

    private val _hotelsList = MutableStateFlow<ScreenState<List<SingleHotelItem>?>>(ScreenState.Loading())
    val hotelsList: MutableStateFlow<ScreenState<List<SingleHotelItem>?>> = _hotelsList

    init {getFavoriteHotels()}

    private fun getFavoriteHotels() = viewModelScope.launch {
        _hotelsList.value = ScreenState.Loading()
        try {
            try {
                repository.getHotelFromFavorites().let {response ->
                    _hotelsList.value = ScreenState.Success(response)
                }
            } catch (e:Exception){
                _hotelsList.value = ScreenState.Error(e.message.toString())
            }

        } catch (e : UnknownHostException){
            _hotelsList.value = ScreenState.Error("Check your connection!")
        }
    }

}