package com.example.hotelsapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsapp.R
import com.example.hotelsapp.databinding.FragmentHomeBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.PopularLocations

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var recyclerViewAdapter : PopularCitiesRvAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerViewAdapter()
        binding.buttonFindLocation.setOnClickListener{
            val cityName = binding.editTextLocation.text.toString()
            navToHotels(cityName)
        }
    }

    private fun setRecyclerViewAdapter() {
        val cities = PopularLocations.generatePopularLocations()
        recyclerViewAdapter = PopularCitiesRvAdapter(
            cities
        ){
            navToHotels(it.name)
        }
        binding.recyclerPopularLocations.setHasFixedSize(true)
        binding.recyclerPopularLocations.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPopularLocations.adapter = recyclerViewAdapter

    }

    private fun navToHotels(cityName: String) {
        findNavController().navigate(
            R.id.nav_home_hotels,
            bundleOf(Pair(Constants.LOCATION_KEY, cityName))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        recyclerViewAdapter = null
    }

}