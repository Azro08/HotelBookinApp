package com.example.hotelsapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.domain.repository.FavoriteHotelsRepository
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FavoriteHotelsViewModel
@Inject constructor(private val repository: FavoriteHotelsRepository) : ViewModel() {

    val responseHotels: MutableStateFlow<ScreenState<List<SingleHotelItem>?>> =
        MutableStateFlow(ScreenState.Loading())

    init {getFavoriteHotels()}

    private fun getFavoriteHotels() = viewModelScope.launch {

        repository.getHotelFromFavorites().let {response ->
            responseHotels.value = ScreenState.Loading()

            try {
                try {
                    responseHotels.value = ScreenState.Success(response)
                } catch (e:Exception){
                    responseHotels.value = ScreenState.Error(e.message.toString())
                }

            } catch (e : UnknownHostException){
                responseHotels.value = ScreenState.Error("Check your connection!")
            }

        }

    }

}