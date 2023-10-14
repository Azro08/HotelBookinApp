package com.example.hotelsapp.presentation.hotels

import android.os.Bundle
import android.util.Log
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
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.databinding.FragmentHotelsBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.ScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HotelsFragment : Fragment() {
    private var _binding: FragmentHotelsBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: HotelsRvAdapter? = null
    private val viewModel: HotelsListViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
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
        val checkInDate = arguments?.getString(Constants.CHECK_IN_DATE_KEY)
        val checkOutDate = arguments?.getString(Constants.CHECK_OUT_DATE_KEY)
        viewModelOutputs(currentLocation!!, checkInDate!!, checkOutDate!!)
    }

    private fun viewModelOutputs(
        currentLocation: String,
        checkInDate: String,
        checkOutDate: String
    ) = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getHotelsList(
                    currentLocation,
                    checkInDate,
                    checkOutDate
                )
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
                Toast.makeText(requireContext(), screenState.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayHotels(hotelData: List<Property>) {
        rvAdapter = HotelsRvAdapter(hotelData, { navToDetails(it.id) }, { addHotelToFav(it) })
        binding.recyclerViewHotels.setHasFixedSize(true)
        binding.recyclerViewHotels.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHotels.adapter = rvAdapter
    }

    private fun addHotelToFav(property: Property) {
        val hotelId = property.id
        val userId = firebaseAuth.uid.toString()
        val imageUrl = property.propertyImage.image.url
        val reviews = property.reviews.score
        val name = property.name
        val price = property.price.displayMessages[0].lineItems[0].price!!.priceTag
        val neighborhood = property.neighborhood.name
        val hotel = SingleHotelItem(
            id = hotelId,
            userId = userId,
            imageUrl = imageUrl,
            review = reviews,
            name = name,
            price = price,
            neighborhood = neighborhood
        )
        Log.d("hotelItem", hotel.toString())
        viewModel.addHotelToFavorite(hotel)
    }

    private fun navToDetails(id: String) {
        findNavController().navigate(
            R.id.nav_hotel_to_details,
            bundleOf(Pair(Constants.HOTEL_ID_KEY, id))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rvAdapter = null
    }

}