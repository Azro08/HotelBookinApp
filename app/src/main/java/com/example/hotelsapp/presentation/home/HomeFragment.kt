package com.example.hotelsapp.presentation.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelsapp.R
import com.example.hotelsapp.databinding.FragmentHomeBinding
import com.example.hotelsapp.helper.Constants
import com.example.hotelsapp.helper.PopularLocations
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var recyclerViewAdapter: PopularCitiesRvAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerViewAdapter()
        binding.buttonFindLocation.setOnClickListener {
            if (binding.editTextLocation.text.isEmpty()) Toast.makeText(
                requireContext(),
                "Enter your destination",
                Toast.LENGTH_LONG
            ).show()
            else {
                val cityName = binding.editTextLocation.text.toString()
                navToHotels(cityName)
            }
        }

        binding.imageButtonDateFrom.setOnClickListener {
            showDatePickerDialog(binding.textViewCheckInDate)
        }

        binding.imageButtonDateTo.setOnClickListener {
            showDatePickerDialog(binding.textViewCheckOutDate)
        }

    }

    private fun showDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val formattedDate = formatDate(selectedDate.time)
                textView.text = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(date)
    }

    private fun setRecyclerViewAdapter() {
        val cities = PopularLocations.generatePopularLocations()
        recyclerViewAdapter = PopularCitiesRvAdapter(
            cities
        ) {
            navToHotels(it.name)
        }
        binding.recyclerPopularLocations.setHasFixedSize(true)
        binding.recyclerPopularLocations.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPopularLocations.adapter = recyclerViewAdapter

    }

    private fun navToHotels(cityName: String) {

        if (binding.textViewCheckInDate.text.isEmpty() || binding.textViewCheckOutDate.text.isEmpty())
            Toast.makeText(
                requireContext(),
                "Enter check-in check-out dates please",
                Toast.LENGTH_LONG
            ).show()
        else {
            val checkInDate = binding.textViewCheckInDate.text.toString()
            val checkoutDate = binding.textViewCheckOutDate.text.toString()
            findNavController().navigate(
                R.id.nav_home_hotels,
                bundleOf(
                    Pair(Constants.LOCATION_KEY, cityName),
                    Pair(Constants.CHECK_IN_DATE_KEY, checkInDate),
                    Pair(Constants.CHECK_OUT_DATE_KEY, checkoutDate)
                )
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        recyclerViewAdapter = null
    }

}