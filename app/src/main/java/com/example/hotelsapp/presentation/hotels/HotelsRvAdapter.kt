package com.example.hotelsapp.presentation.hotels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelsapp.R
import com.example.hotelsapp.data.dto.hotel.Property
import com.example.hotelsapp.databinding.HotelViewHolderBinding


class HotelsRvAdapter(
    private val hotelList: List<Property>,
    private val listener: (hotel: Property) -> Unit
) : RecyclerView.Adapter<HotelsRvAdapter.HotelViewHolder>() {

    class HotelViewHolder(
        listener: (hotel: Property) -> Unit,
        private var binding: HotelViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val context = itemView.context!!
        private var hotel: Property? = null
        fun bind(curHotel: Property) {
            Glide.with(context).load(curHotel.propertyImage.image.url)
                .error(R.drawable.places)
                .into(binding.imageViewHotel)
            binding.textViewHotelName.text = curHotel.name
            val rating = curHotel.reviews.total
            val textViewRatingText = "Rating: $rating/10"
            if (rating != null) binding.textViewRating.text = textViewRatingText
            val price = curHotel.price.displayMessages[0].lineItems[0].price?.priceTag
            val unknownPriceMsg = "-"
            if (price == null) binding.textViewPrice.text = unknownPriceMsg
            else binding.textViewPrice.text = price
            if (curHotel.neighborhood.name != null) binding.textViewNeighborhood.text =
                curHotel.neighborhood.name
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