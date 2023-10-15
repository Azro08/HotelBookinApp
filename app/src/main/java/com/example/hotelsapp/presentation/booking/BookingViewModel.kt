package com.example.hotelsapp.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel
@Inject constructor(
    private val repository: BookingRepository
) : ViewModel() {

    fun bookHotel(bookingDetails: BookingDetails) = viewModelScope.launch {
        repository.bookHotel(bookingDetails)
    }

}