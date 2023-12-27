package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.CoffeeShopItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
import com.vladiyak.sevenwindsstudiotask.utils.toKilometers
import dagger.hilt.android.lifecycle.HiltViewModel


class CoffeeShopsViewHolder(
    val binding: CoffeeShopItemRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: LocationItem, onClickListener: OnClickListenerLocationItem) {
        binding.apply {
            itemLayout.setOnClickListener {
                onClickListener.onItemClick(item)
            }
            coffeeShopName.text = item.name
            geoPosition.text = convert(item)
        }
    }


    companion object {
        fun from(parent: ViewGroup): CoffeeShopsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CoffeeShopItemRvBinding.inflate(layoutInflater, parent, false)
            return CoffeeShopsViewHolder(binding)
        }
    }

    private fun convert(item: LocationItem): String {
        return if (item.distance >= 1000) {
            String.format("%.0f" + " " + "км от вас", item.distance / 1000)
        } else {
            String.format("%.0f" + " " + "м от вас", item.distance)
        }
    }
}