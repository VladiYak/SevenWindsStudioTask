package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.databinding.CoffeeShopItemRvBinding
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem
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