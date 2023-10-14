package com.example.hotelsapp.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelsapp.R
import com.example.hotelsapp.data.dto.hotel.SingleHotelItem
import com.example.hotelsapp.databinding.HotelViewHolderBinding


class FavoriteHotelsRvAdapter(
    private val hotelList: List<SingleHotelItem>,
    private val listener: (hotel: SingleHotelItem) -> Unit
) : RecyclerView.Adapter<FavoriteHotelsRvAdapter.HotelViewHolder>() {

    class HotelViewHolder(
        listener: (hotel: SingleHotelItem) -> Unit,
        private var binding: HotelViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context!!
        private var hotel: SingleHotelItem? = null
        fun bind(curHotel: SingleHotelItem) {
            Glide.with(context).load(curHotel.imageUrl)
                .error(R.drawable.places)
                .into(binding.imageViewHotel)
            binding.textViewHotelName.text = curHotel.name
            val rating = curHotel.review
            val textViewRatingText = "Rating: $rating/10"
            if (rating != null) binding.textViewRating.text = textViewRatingText
            val price = curHotel.price
            val unknownPriceMsg = "-"
            if (price == null) binding.textViewPrice.text = unknownPriceMsg
            else binding.textViewPrice.text = price
            if (curHotel.neighborhood != null) binding.textViewNeighborhood.text =
                curHotel.neighborhood
        }

        init {
            binding.imageViewHotel.setOnClickListener { listener(hotel!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            listener,
            HotelViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(hotelList[position])
    }

}