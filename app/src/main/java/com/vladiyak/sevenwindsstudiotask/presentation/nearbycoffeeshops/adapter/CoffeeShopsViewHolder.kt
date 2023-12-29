package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.R
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.CoffeeShopItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import com.vladiyak.sevenwindsstudiotask.utils.formatDistance


class CoffeeShopsViewHolder(
    val binding: CoffeeShopItemRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LocationItem, onClickListener: OnClickListenerLocationItem) {
        binding.apply {
            itemLayout.setOnClickListener {
                onClickListener.onItemClick(item)
            }
            coffeeShopName.text = item.name
            geoPosition.text = item.distance?.let {
                val distanceString = it.formatDistance(itemView.context)
                itemView.context.getString(R.string.distance_to_me, distanceString)
            } ?: itemView.context.getString(R.string.unknown_distance)
        }
    }


    companion object {
        fun from(parent: ViewGroup): CoffeeShopsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CoffeeShopItemRvBinding.inflate(layoutInflater, parent, false)
            return CoffeeShopsViewHolder(binding)
        }
    }
}