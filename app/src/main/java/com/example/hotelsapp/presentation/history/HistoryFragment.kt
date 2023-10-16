package com.example.hotelsapp.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsapp.data.dto.hotel_booking.BookingDetails
import com.example.hotelsapp.databinding.FragmentHistoryBinding
import com.example.hotelsapp.helper.ScreenState
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private var rvAdapter: RvBookingHistoryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshFragment()
        }
        viewModelOutputs()
    }

    private fun viewModelOutputs() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookingList.collect {
                    processResponse(it)
                }
            }
        }
    }

    private fun refreshFragment() {
        binding.loadingGif.visibility = View.VISIBLE
        binding.rvBookingHistory.visibility = View.GONE
        binding.textViewError.visibility = View.GONE
        viewModelOutputs()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun processResponse(screenState: ScreenState<List<BookingDetails>?>) {
        when (screenState) {

            is ScreenState.Loading -> {}
            is ScreenState.Success -> {
                binding.loadingGif.visibility = View.GONE
                binding.rvBookingHistory.visibility = View.VISIBLE
                if (screenState.data != null) displayHistory(screenState.data)
                else Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            is ScreenState.Error -> {
                handleError(screenState.message)
            }
        }
    }

    private fun handleError(message: String?) {
        binding.loadingGif.visibility = View.GONE
        binding.rvBookingHistory.visibility = View.GONE
        binding.textViewError.visibility = View.VISIBLE
        binding.textViewError.text = message!!
    }

    private fun displayHistory(data: List<BookingDetails>) {
        rvAdapter = RvBookingHistoryAdapter(data) {
            viewModel.removeBookedHotel(it.hotelName)
            refreshFragment()
        }
        binding.rvBookingHistory.setHasFixedSize(true)
        binding.rvBookingHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBookingHistory.adapter = rvAdapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rvAdapter = null
    }

}