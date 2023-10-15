package com.example.hotelsapp.presentation.hotel_details

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
import com.example.hotelsapp.R
import com.example.hotelsapp.data.dto.hotel_details.HotelDetailsResponse
import com.example.hotelsapp.data.dto.hotel_details.Image
import com.example.hotelsapp.data.dto.hotel_details.NearbyPOIsItems
import com.example.hotelsapp.databinding.FragmentHotelDetailsBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelDetailsFragment : Fragment() {
    private var _binding: FragmentHotelDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HotelDetailsViewModel by viewModels()
    private var viewPagerAdapter: HotelImagesPagerAdapter? = null
    private var score = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val hotelId = arguments?.getString(Constants.HOTEL_ID_KEY)
        score = arguments?.getDouble(Constants.SCORE_KEY)!!
        if (hotelId != null) viewModelOutputs(hotelId)
        else Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
    }

    private fun viewModelOutputs(
        id: String
    ) = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getHotelDetailsById(
                    id
                )
                responseHotelDetails.collect {
                    processResponse(it)
                }
            }
        }
    }

    private fun processResponse(screenState: ScreenState<HotelDetailsResponse?>) {
        when (screenState) {

            is ScreenState.Loading -> {}
            is ScreenState.Success -> {
                binding.loadingDetailsGif.visibility = View.GONE
                binding.layoutDetailsContainer.visibility = View.VISIBLE
                if (screenState.data != null) displayHotelDetails(screenState.data)
                else Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            is ScreenState.Error -> {
                Toast.makeText(requireContext(), screenState.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getImagesList(images: List<Image>): List<String> {
        val imagesList = arrayListOf<String>()
        for (i in 0 until 10) {
            imagesList.add(images[i].image.url)
        }
        return imagesList
    }

    private fun getNearByPlaces(items: List<NearbyPOIsItems>): List<String> {
        val placesList = arrayListOf<String>()

        for (place in items) {
            val nearByPlace = "${place.text} (${place.moreInfo})"
            placesList.add(nearByPlace)
        }
        return placesList
    }

    private fun displayHotelDetails(data: HotelDetailsResponse) = with(binding) {
        val images = getImagesList(data.propertyGallery.images)
        viewPagerAdapter = HotelImagesPagerAdapter(requireContext(), images)
        viewPagerHotelImages.adapter = viewPagerAdapter
        textHotelName.text = data.summary.name
        textAddress.text = data.summary.location.address.addressLine
        textHotelDetails.text = data.summary.location.whatsAround.editorial.content.toString()
        textPhoneNumber.text = data.summary.telesalesPhoneNumber
        textTagline.text = data.summary.tagline
        ratingBar.rating = score.toFloat()
        val nearbyPOIs = getNearByPlaces(data.summary.nearbyPOIs.items)
        textWhatsAround.text = nearbyPOIs.toString()
        binding.buttonBookNow.setOnClickListener {
            findNavController().navigate(R.id.nav_details_to_book, bundleOf(Pair(Constants.HOTEL_NAME_KEY, data.summary.name)))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewPagerAdapter = null
    }

}