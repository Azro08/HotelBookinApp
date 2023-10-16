package com.example.hotelsapp.presentation.favorite

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
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.databinding.FragmentFavoriteBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var rvAdapter: FavoriteHotelsRvAdapter? = null
    private val viewModel: FavoriteHotelsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelOutputs()
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshFragment()
        }
    }

    private fun viewModelOutputs() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                hotelsList.collect {
                    processResponse(it)
                }
            }
        }
    }

    private fun processResponse(screenState: ScreenState<List<SingleHotelItem>?>) {
        when (screenState) {

            is ScreenState.Loading -> {}
            is ScreenState.Success -> {
                binding.loadingGif.visibility = View.GONE
                binding.rvFavoriteHotels.visibility = View.VISIBLE
                if (screenState.data != null) displayHotels(screenState.data)
                else Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            is ScreenState.Error -> {
                handleError(screenState.message)
            }
        }
    }

    private fun handleError(message: String?) {
        binding.loadingGif.visibility = View.GONE
        binding.rvFavoriteHotels.visibility = View.GONE
        binding.textViewError.visibility = View.VISIBLE
        binding.textViewError.text = message!!
    }

    private fun refreshFragment() {
        binding.loadingGif.visibility = View.VISIBLE
        binding.rvFavoriteHotels.visibility = View.GONE
        binding.textViewError.visibility = View.GONE
        viewModelOutputs()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun displayHotels(hotelItems: List<SingleHotelItem>) {
        rvAdapter = FavoriteHotelsRvAdapter(hotelItems) {
            findNavController().navigate(
                R.id.nav_fav_to_details,
                bundleOf(Pair(Constants.HOTEL_ID_KEY, it.id))
            )
        }
        binding.rvFavoriteHotels.setHasFixedSize(true)
        binding.rvFavoriteHotels.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteHotels.adapter = rvAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rvAdapter = null
    }

}