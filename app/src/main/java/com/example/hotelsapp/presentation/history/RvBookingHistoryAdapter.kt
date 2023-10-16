package com.example.hotelsapp.presentation.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.databinding.HistoryItemViewHolderBinding
import java.time.LocalDate

class RvBookingHistoryAdapter(
    private val bookingList: List<BookingDetails>,
    private val listener: (bookingDetails: BookingDetails) -> Unit
) : RecyclerView.Adapter<RvBookingHistoryAdapter.BookedHotelViewHolder>() {

    class BookedHotelViewHolder(
        listener: (bookedHotel: BookingDetails) -> Unit,
        private var binding: HistoryItemViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var bookedHotel: BookingDetails? = null

        fun bind(curBookedHotel : BookingDetails) {
            binding.textViewBookedHotelName.text = curBookedHotel.hotelName
            binding.textViewBookedCheckIn.text = curBookedHotel.checkInDate
            binding.textViewBookedCheckOut.text = curBookedHotel.checkOutDate
            if (datePassed(curBookedHotel.checkInDate)) binding.buttonCancelBooking.visibility = View.GONE
            bookedHotel = curBookedHotel
        }

        private fun datePassed(dateString: String): Boolean {
            val date = LocalDate.parse(dateString)
            return LocalDate.now() > date
        }

        init {
            binding.buttonCancelBooking.setOnClickListener { listener(bookedHotel!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedHotelViewHolder {
        return BookedHotelViewHolder(
            listener,
            HistoryItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: BookedHotelViewHolder, position: Int) {
        holder.bind(bookingList[position])
    }

}