package com.example.hotelsapp.helper

import com.example.hotelsapp.R
import com.example.hotelsapp.domain.model.City

object PopularLocations {
    fun generatePopularLocations() : List<City>{
        val locations = arrayListOf<City>()
        locations.add(City("Barcelona", R.drawable.barcelona))
        locations.add(City("London", R.drawable.london))
        locations.add(City("Paris", R.drawable.paris))
        locations.add(City("New York", R.drawable.new_york))
        locations.add(City("Istanbul", R.drawable.istanbul))
        return locations
    }
}