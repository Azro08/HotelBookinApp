package com.example.hotelsapp.presentation.hotel_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.hotelsapp.R

class HotelImagesPagerAdapter(private val context: Context, private val imageUrls: List<String>) :
    PagerAdapter() {

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_hotel_image, container, false)

        val imageView: ImageView = itemView.findViewById(R.id.imageViewHotel)

        // Load image using Glide
        Glide.with(context)
            .load(imageUrls[position])
            .centerCrop()
            .placeholder(R.drawable.places) // placeholder image while loading
            .into(imageView)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}
