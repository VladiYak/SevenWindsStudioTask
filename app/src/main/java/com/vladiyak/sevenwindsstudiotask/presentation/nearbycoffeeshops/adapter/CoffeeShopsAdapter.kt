package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.utils.OnClickListenerLocationItem

class CoffeeShopsAdapter(
    private val onClickListener: OnClickListenerLocationItem
) : ListAdapter<LocationItem, CoffeeShopsViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopsViewHolder {
        return CoffeeShopsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoffeeShopsViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, onClickListener)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocationItem, newItem: LocationItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
}