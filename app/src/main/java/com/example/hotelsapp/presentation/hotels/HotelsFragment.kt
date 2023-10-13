package com.example.hotelsapp.presentation.hotels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsapp.R
import com.example.hotelsapp.data.dto.hotel.HotelResponse
import com.example.hotelsapp.data.dto.hotel.Property
import com.example.hotelsapp.databinding.FragmentHotelsBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelsFragment : Fragment() {
    private var _binding: FragmentHotelsBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: HotelsRvAdapter? = null
    private val viewModel: HotelsListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentLocation = arguments?.getString(Constants.LOCATION_KEY)
        currentLocation?.let {
            binding.textViewCurLocation.text = currentLocation
        }
        viewModelOutputs()
    }

    private fun viewModelOutputs() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getHotelsList()
                responseHotels.collect {
                    processResponse(it)
                }
            }
        }
    }

    private fun processResponse(screenState: ScreenState<HotelResponse?>) {
        when (screenState) {

            is ScreenState.Loading -> {}
            is ScreenState.Success -> {
                binding.loadingGif.visibility = View.GONE
                binding.textViewCurLocation.visibility = View.VISIBLE
                binding.recyclerViewHotels.visibility = View.VISIBLE
                if (screenState.data != null) displayHotels(screenState.data.properties)
                else Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            is ScreenState.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayHotels(hotelData: List<Property>) {
        rvAdapter = HotelsRvAdapter(hotelData) {
            findNavController().navigate(
                R.id.nav_hotel_to_details,
                bundleOf(Pair(Constants.HOTEL_ID_KEY, it.id))
            )
        }
        binding.recyclerViewHotels.setHasFixedSize(true)
        binding.recyclerViewHotels.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHotels.adapter = rvAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rvAdapter = null
    }

}