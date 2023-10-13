package com.example.hotelsapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelsapp.databinding.PopularLocationViewHolderBinding
import com.example.hotelsapp.domain.model.City

class PopularCitiesRvAdapter (
    private val citiesList : List<City>,
    private val listener : (city : City) -> Unit
)  : RecyclerView.Adapter<PopularCitiesRvAdapter.CityViewHolder>(){

    class CityViewHolder(
        listener: (city: City) -> Unit,
        private var binding : PopularLocationViewHolderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private var city : City ?= null
        fun bind (curCity: City){
            binding.textViewPopCityName.text = curCity.name
            binding.imageViewPopCity.setBackgroundResource(curCity.image)
            city = curCity
        }

        init {
            binding.imageViewPopCity.setOnClickListener{listener(city!!)}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            listener,
            PopularLocationViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(citiesList[position])
    }

}