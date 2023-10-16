package com.example.hotelsapp.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.data.repository.BookingRepositoryImpl
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
@Inject constructor(
    private val bookingRepository: BookingRepositoryImpl
) : ViewModel() {

    private val _bookingList =
        MutableStateFlow<ScreenState<List<BookingDetails>?>>(ScreenState.Loading())
    val bookingList: MutableStateFlow<ScreenState<List<BookingDetails>?>> = _bookingList

    init {
        getBookingHistory()
    }

    private fun getBookingHistory() = viewModelScope.launch {
        _bookingList.value = ScreenState.Loading()
        try {
            try {
                bookingRepository.getBookingHistory().let {
                    _bookingList.value = ScreenState.Success(it)
                }
            } catch (e: Exception) {
                _bookingList.value = ScreenState.Error(e.message.toString())
            }

        } catch (e: UnknownHostException) {
            _bookingList.value = ScreenState.Error(e.message.toString())
        }

    }

    fun refresh() {
        getBookingHistory()
    }

    fun removeBookedHotel(hotelName: String) = viewModelScope.launch {
        bookingRepository.removeBookedHotel(hotelName)
    }

}